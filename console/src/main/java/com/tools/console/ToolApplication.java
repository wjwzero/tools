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
 * 2019/1/29    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/29 15:02
 */
@SpringBootApplication(scanBasePackages = ToolApplication.ROOT_PACKAGE_NAME)
public class ToolApplication {
    public static final String ROOT_PACKAGE_NAME = "com.tools";
    private static final Logger logger = LoggerFactory.getLogger(ToolApplication.class);

    public static void main(String[] args) {
        new SpringApplication(ToolApplication.class).run(args);
        logger.warn("====================Console Web启动成功!====================");
    }
}
