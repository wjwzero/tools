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
 * 2019/1/9    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.netty;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tools.netty.service.NettyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 告警MQ消费服务启动入口
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/9 17:54
 */
@Component
@Order(2)
@EnableJms
public class WebSocketRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketRunner.class);

    @Resource
    private NettyService nettyService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(()-> nettyService.run());
        logger.warn("====================AlarmWebSocket启动成功!====================");
    }
}
