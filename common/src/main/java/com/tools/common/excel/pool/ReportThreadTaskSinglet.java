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
 * 2018年7月26日    HuangWeiMou         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.excel.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @author HuangWeiMou
 * @version 1.0
 * @date 2018年7月26日 上午11:16:11
 */
public class ReportThreadTaskSinglet {

    private static Logger logger = LoggerFactory.getLogger(ReportThreadTaskSinglet.class);

    private volatile static ReportThreadTaskSinglet reportThreadTaskSinglet;

    private static Map<String, Future<?>> hashMap = new ConcurrentHashMap<String, Future<?>>();
    /**
     * 用来记录每个线程的过期时间，防止线程执行时间过长
     */
    private static Map<String, Long> timeOutMap = new ConcurrentHashMap<>();
    /**
     * 线程最大执行时间，设置为15min，超过15min，销毁该线程
     */
    private static final long THREAD_OVER_TIME = 900000L;

    private ReportThreadTaskSinglet() {

    }

    public static ReportThreadTaskSinglet instance() {
        if (reportThreadTaskSinglet == null) {
            synchronized (ReportThreadTaskSinglet.class) {
                if (reportThreadTaskSinglet == null) {
                    reportThreadTaskSinglet = new ReportThreadTaskSinglet();
                }
            }
        }
        return reportThreadTaskSinglet;
    }

    /**
     * @param key
     * @param future
     * @Title: put 添加
     * @author HuangWeiMou
     * @date 2018年7月26日 上午11:30:59
     */
    public void put(String key, Future<?> future) {
        hashMap.put(key, future);
        timeOutMap.put(key, System.currentTimeMillis() + THREAD_OVER_TIME);
    }

    /**
     * @param key
     * @return
     * @Title: get 获取
     * @author HuangWeiMou
     * @date 2018年7月26日 上午11:31:13
     */
    public Future<?> get(String key) {
        return hashMap.get(key);
    }

    /**
     * @param key
     * @Title: remove 删除
     * @author HuangWeiMou
     * @date 2018年7月26日 上午11:31:23
     */
    public void remove(String key) {
        hashMap.remove(key);
        timeOutMap.remove(key);
    }

    /**
     * 获取个数
     * 
     * @return
     * @author zhengjunwei
     * @date 2019年1月10日 下午1:41:31
     */
    public int getSize() {
        return hashMap.size();
    }

    /**
     * @param key
     * @Title: interrupt 中断Future任务，成功后自动删除。失败情况下，重试3次
     * @author HuangWeiMou
     * @date 2018年7月26日 上午11:24:16
     */
    public void interrupt(String key) {
        Future<?> future = get(key);
        if (null != future) {
            if (!future.isCancelled()) {
                logger.info("任务{}在进行中，进行中断操作", key);
                int count = 1;
                while (!future.cancel(true)) {
                    if (3 == count) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    count++;
                }
                if (future.isCancelled()) {
                    logger.info("任务{}，进行中断操作成功", key);
                    remove(key);
                } else {
                    logger.error("任务{}，进行中断操作失败", key);
                    remove(key);
                }
            } else {
                remove(key);
            }
        }
    }

    /**
     * 移除执行时间超时的线程
     * 
     * @author zhengjunwei
     * @date 2019年1月18日 下午4:32:36
     */
    public void removeOverTimeFuture() {
        List<String> list = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();

        timeOutMap.forEach((key, value) -> {
            if (currentTimeMillis > value) {
                list.add(key);
            }
        });

        list.forEach(key -> {
            interrupt(key);
        });
    }
}
