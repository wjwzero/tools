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


package com.tools.jms.consumer;

import com.tools.jms.consumer.impl.ActiveMqConsumerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/30 16:09
 */
public class DynaicConsumer {

    private Logger logger = LoggerFactory.getLogger(ActiveMqConsumerServiceImpl.class);

    @Autowired
    private JmsListenerEndpointRegistry jmsListenerEndpointRegistry;

    @JmsListener(destination = "${destinationName}")
    public void consumeMsg(byte msg[]) {
        logger.info(new String(msg));
    }
}
