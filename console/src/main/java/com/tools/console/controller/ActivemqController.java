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


package com.tools.console.controller;

import com.tools.common.basic.AbstractController;
import com.tools.base.basic.model.dto.ResultDTO;
import com.tools.common.utils.SpringUtils;
import com.tools.console.controller.api.ActivemqApi;
import com.tools.jms.consumer.ActiveMqConsumerService;
import com.tools.jms.consumer.DynaicConsumer;
import com.tools.jms.consumer.DynamicLoadPropertySource;
import com.tools.jms.producer.ActiveMqProducerService;
import com.tools.base.util.date.DateUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/29 16:10
 */
@RestController
public class ActivemqController extends AbstractController implements ActivemqApi {

    public static final String DESTINATION_NAME = "destinationName";

    @Resource
    private ActiveMqProducerService activeMqProducerService;

    @Resource
    private ActiveMqConsumerService activeMqConsumerService;



    @Override
    public ResultDTO stopPush() {
        activeMqProducerService.stop();
        return success();
    }

    @Override
    public ResultDTO pushMsg(String msg, Integer
            maxThreadNum, String destinationName, Long theardSleepTime, Integer loopNum) {
        activeMqProducerService.run(maxThreadNum, loopNum, msg, destinationName, theardSleepTime);
        return success();
    }

    @Override
    public ResultDTO pushFixedMsg(@NotNull(message = "最大线程数不为空") Integer maxThreadNum, String destinationName, Long
            theardSleepTime, Integer loopNum) {
        String msg = "{\"imei\":\"201902191356001\",\"msg\":{\"alertType\":\"offline\"," +
                "\"deviceImei\":\"201902191356001\",\"gpsTime\":\"1547268298000\",\"lat\":\"22.577233\"," +
                "\"lng\":\"113.916409\"},\"postTime\":\""+ DateUtils.format(LocalDateTime.now())+"\",\"type\":\"DEVICE\"}";
        activeMqProducerService.run(maxThreadNum, loopNum, msg, destinationName, theardSleepTime);
        return success();
    }

    @Override
    public ResultDTO consumeMsg(String destinationName) {
        DynamicLoadPropertySource.setConsumerDestinationName(destinationName);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) SpringUtils
                .getApplicationContext().getAutowireCapableBeanFactory();
        if(defaultListableBeanFactory.containsBeanDefinition(DESTINATION_NAME)){
            defaultListableBeanFactory.removeBeanDefinition(DESTINATION_NAME);
        }
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition
                (DynaicConsumer.class);
        defaultListableBeanFactory.registerBeanDefinition(DESTINATION_NAME, definitionBuilder.getBeanDefinition());
        DynaicConsumer consumer = SpringUtils
                .getApplicationContext().getBean(DESTINATION_NAME, DynaicConsumer.class);
        System.out.println(consumer);
        return success();
    }

    @Override
    public ResultDTO shutDownConsumer(String destinationName) {
        activeMqConsumerService.shutDownConsumer(destinationName);
        return success();
    }

    @Override
    public ResultDTO converStringToJSON(String converString, String josnKey) {
        
        return null;
    }
}
