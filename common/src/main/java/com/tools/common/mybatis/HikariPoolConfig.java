/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
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
 * 2019/1/16    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.mybatis;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Hikari连接池配置
 * @date 2019/1/16 18:28
 * @author yaojianping
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariPoolConfig {

    private HikariConfig mysql;

    private HikariConfig mycat;

    public HikariConfig getMysql() {
        return mysql;
    }

    public void setMysql(HikariConfig mysql) {
        this.mysql = mysql;
    }

    public HikariConfig getMycat() {
        return mycat;
    }

    public void setMycat(HikariConfig mycat) {
        this.mycat = mycat;
    }
}