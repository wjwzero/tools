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
 * 2018/11/20    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * 公共配置请到
 *
 * @author yaojianping
 * @version 1.0
 * @date 2018/11/20 17:38
 */
@SpringBootApplication(scanBasePackages = ReportApplication.ROOT_PACKAGE_NAME)
public class ReportApplication implements CommandLineRunner {
    public static final String ROOT_PACKAGE_NAME = "com.report";
    private static final Logger logger = LoggerFactory.getLogger(ReportApplication.class);


    public static void main(String[] args) {
        new SpringApplication(ReportApplication.class).run(args);
        // 初始化数据在 com.jimi.together.web.config.initialize包中
        logger.info("====================Jimi Together Web启动成功!====================");
    }

    @Override
    public void run(String... args) {
        logger.info("解析参数：{}", Arrays.toString(args));
    }
}
