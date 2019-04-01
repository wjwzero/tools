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

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author wanghe
 * @version 1.0
 * @date 2018年11月23日 下午6:20:34
 */

@Configuration
public class DataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Resource
    private HikariPoolConfig hikariConfig;

    @Bean(name = "mysqlDataSource")
    @Primary
    public DataSource primaryDataSource() {
        logger.info("mysql连接池创建中.......");
        return new HikariDataSource(hikariConfig.getMysql());
    }

    /*@Bean(name = "mycatDataSource")
    public DataSource mycatDataSource() {
        logger.info("mycat连接池创建中.......");
        return new HikariDataSource(hikariConfig.getMycat());
    }*/
}
