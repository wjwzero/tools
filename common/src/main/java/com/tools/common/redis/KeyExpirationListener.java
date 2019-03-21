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
 * 2019/1/2    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/1/2 17:09
 */
public class KeyExpirationListener extends KeyExpirationEventMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyExpirationListener.class);
    private final PatternTopic TOPIC;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public KeyExpirationListener(int dbIndex, RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
        TOPIC = new PatternTopic(String.format("__keyevent@%d__:expired", dbIndex));
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, TOPIC);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        if (new String(pattern).equals(TOPIC.getTopic())) {
            String redisKey = message.toString();
            LOGGER.debug(redisKey);
        }
    }
}
