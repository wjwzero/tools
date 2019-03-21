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

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author wanghe
 * @version 1.0
 * @date 2018年11月23日 下午6:24:52
 */
@Configuration
@MapperScan(basePackages = {"com.jimi.together.**.mycat"}, sqlSessionFactoryRef = "mycatSqlSessionFactory")
public class MycatConfiguration {

    @javax.annotation.Resource
    @Qualifier("mycatDataSource")
    private DataSource mycatDataSource;

    @Bean(name = "mycatSqlSessionFactory")
    public SqlSessionFactory mycatSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mycatDataSource);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.tools.**.mycat");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mycat/*.xml"));
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        Objects.requireNonNull(sqlSessionFactory).getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }
}
