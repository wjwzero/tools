/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2018.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording,
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 *
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2018年11月23日    wanghe         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.mybatis;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tools.common.basic.model.IBaseModel;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.PathMatcher;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wanghe
 * @version 1.0
 * @date 2018年11月23日 下午6:24:52
 */
@Configuration
@MapperScan(basePackages = {"com.tools.**.mapper"}, sqlSessionFactoryRef = "masterSqlSessionFactory")
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfiguration {

    @javax.annotation.Resource
    @Qualifier(value = "mysqlDataSource")
    private DataSource mysqlDataSource;

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(MybatisProperties properties) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mysqlDataSource);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setTypeAliasesPackage(buildTypeAliasesPackage(properties.getTypeAliasesPackage()));
        sqlSessionFactoryBean.setTypeAliasesSuperType(IBaseModel.class);
        sqlSessionFactoryBean.setMapperLocations(properties.resolveMapperLocations());
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        Objects.requireNonNull(sqlSessionFactory).getConfiguration().setMapUnderscoreToCamelCase(properties.getConfiguration().isMapUnderscoreToCamelCase());
        return sqlSessionFactoryBean.getObject();
    }

    private String buildTypeAliasesPackage(String typeAliasesPackage) throws IOException {
        Set<String> allPackage = Sets.newHashSet();

        PathMatchingResourcePatternResolver resourceResolver =
                new PathMatchingResourcePatternResolver(MybatisConfiguration.class.getClassLoader());
        MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory(resourceResolver);

        String[] aliasesPackages = typeAliasesPackage.split(",");


        List<String> excludes = Lists.newArrayList();
        Resource[] resources;
        String className;
        for (String aliasesPackage : aliasesPackages) {
            if (aliasesPackage.startsWith("!")) {
                excludes.add(aliasesPackage.substring(1));
                continue;
            }
            aliasesPackage = aliasesPackage.replace(".", "/");
            resources =
                    resourceResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + aliasesPackage + "/**/*.class");
            for (Resource resource : resources) {
                className = metadataReaderFactory.getMetadataReader(resource).getClassMetadata().getClassName();
                allPackage.add(className.substring(0, className.lastIndexOf(".")));
            }
        }
        PathMatcher pathMatcher = resourceResolver.getPathMatcher();
        return allPackage.stream().filter(packageName -> {
            for (String exclude : excludes) {
                if (pathMatcher.match(exclude, packageName)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.joining(","));
    }

    /**
     * 配置事务管理器
     */
    @Bean(name = "masterTransactionManager")
    @Primary
    public PlatformTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(mysqlDataSource);
    }
}
