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


package com.tools.jms.producer.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tools.jms.producer.ActiveMqProducerService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/29 16:53
 */
@Service
public class ActiveMqProducerServiceImpl implements ActiveMqProducerService {


    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 推送线程池
     * @return
     * @author  wangjianwei
     * @date 2019/1/29 11:50
     */
    private static ThreadFactory bdfProducerMonitorThreadFactory = new ThreadFactoryBuilder().setNameFormat("producer-monitor-pool-%d").build();
    private static ExecutorService pool = new ThreadPoolExecutor(5, 200,10L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(1024), bdfProducerMonitorThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private static AtomicLong count = new AtomicLong(0);

    @Autowired
    JmsTemplate jmsTemplate;

    private Logger logger = LoggerFactory.getLogger(ActiveMqProducerServiceImpl.class);

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    @Override
    public void run(int threadNum, Integer loopNum, String msg, String destinationName, long threadSleepTime) {
        //20-1000;50-3000
        for (int i = 0; i < threadNum; i++) {
            pool.execute(() -> {
                int j = 0;
                while (j < loopNum) {
                    j++;
//                    {"imei":"868120209984290","msg":{"alertType":"offline","deviceImei":"868120209984290","gpsTime":"1547268298000","lat":"22.577233","lng":"113.916409"},"postTime":"2019-01-2812:55:02","type":"DEVICE"}
                    /*String json = "{\"imei\":\"868120209984290\",\"msg\":{\"alertType\":\"offline\"," +
                            "\"deviceImei\":\"868120209984290\",\"gpsTime\":1547268298000,\"lat\":22.577233," +
                            "\"lng\":113.916409},\"postTime\":\"2019-01-28 12:55:02\",\"type\":\"DEVICE\"}";*/
                    count.incrementAndGet();
                    jmsTemplate.convertAndSend(destinationName, msg.getBytes());
                    try {
                        Thread.sleep(threadSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        final int[] t = {0};
        executorService.scheduleAtFixedRate(() -> {
            logger.info("睡眠50ms，每秒生产：" + count.getAndSet(0));
            if(count.get() == 0){
                t[0]++;
                if(t[0] == 3){
                    logger.info("睡眠"+threadSleepTime+"ms，长时间无生产停止定时");
                    executorService.shutdown();
                }
            }
        }, 1, 3, TimeUnit.SECONDS);
    }

    @Override
    public void stop(){
        pool.shutdownNow();
    }
}
