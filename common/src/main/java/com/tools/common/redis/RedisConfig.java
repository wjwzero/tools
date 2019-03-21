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
 * 2018/11/26    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.redis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/11/26 17:06
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Resource
    private LettuceConnectionFactory redisConnectionFactory;
    @Value("${spring.redis.database}")
    int dbIndex;

    @Bean
    public RedisTemplate<String, Object> redisCacheTemplate() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        redisConnectionFactory.setDatabase(dbIndex);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        RedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisCacheTemplate() {
        redisConnectionFactory.setDatabase(dbIndex);
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        RedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 缓存Key生成策略
     *
     * @return org.springframework.cache.interceptor.KeyGenerator
     * @author yaojianping
     * @date 2018/11/26 17:08
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder("together-bdf:");
            sb.append(target.getClass().getSimpleName()).append(".");
            sb.append(method.getName());
            for (Object param : params) {
                sb.append(param);
            }
            return sb.toString();
        };
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @Bean
    KeyExpirationListener keyExpirationListener() {
        return new KeyExpirationListener(dbIndex, redisMessageListenerContainer());
    }
}
