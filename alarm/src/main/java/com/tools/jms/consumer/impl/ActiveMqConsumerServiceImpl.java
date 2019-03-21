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
 * 2019/1/30    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.jms.consumer.impl;

import com.tools.jms.consumer.ActiveMqConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/30 11:44
 */
@Service
public class ActiveMqConsumerServiceImpl implements ActiveMqConsumerService {

    private Logger logger = LoggerFactory.getLogger(ActiveMqConsumerServiceImpl.class);

    @Autowired
    private JmsListenerEndpointRegistry jmsListenerEndpointRegistry;

    @Override
    public void shutDownConsumer(String queue) {
        Collection<MessageListenerContainer> containerCollections = jmsListenerEndpointRegistry.getListenerContainers();
        for (MessageListenerContainer container : containerCollections) {
            String name = ((DefaultMessageListenerContainer) container).getDestinationName();
            if (name.equals(queue)) {
                //开始
                //container.star();
                //停止
                //container.stop();
                //关闭
                ((DefaultMessageListenerContainer) container).shutdown();
                break;
            }
        }
    }
}
