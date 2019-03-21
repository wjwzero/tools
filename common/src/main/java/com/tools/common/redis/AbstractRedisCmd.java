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
 * 2018/11/27    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/11/27 17:42
 */
public abstract class AbstractRedisCmd {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 必须实现方法，返回对应redisTemplate
     *
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String   ,   java.lang.Object>
     * @author yaojianping
     * @date 2018/11/28 14:50
     */
    public abstract RedisTemplate<String, Object> getTemple();

    /**
     * 字符串方式存储数据，数据不过期
     *
     * @param key   键
     * @param value 值
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 14:51
     */
    public boolean set(String key, Object value) {
        try {
            ValueOperations<String, Object> valueOperations = getTemple().opsForValue();
            valueOperations.set(key, value);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return false;
        }
        return true;
    }

    /**
     * 字符串方式存储数据，数据会过期
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间，单位：秒
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 14:52
     */
    public boolean set(String key, Object value, long expireTime) {
        return set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 字符串方式存储数据，数据会过期
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 15:00
     */
    public boolean set(String key, Object value, long expireTime, TimeUnit timeUnit) {
        try {
            ValueOperations<String, Object> valueOperations = getTemple().opsForValue();
            valueOperations.set(key, value, expireTime, timeUnit);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return false;
        }
        return true;
    }

    /**
     * 键是否存在
     *
     * @param key 键
     * @return boolean true-存在，false-不存在
     * @author yaojianping
     * @date 2018/11/28 15:01
     */
    public boolean exists(final String key) {
        try {
            return getTemple().hasKey(key);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return false;
        }
    }

    /**
     * 模糊匹配拿到所有的key
     *
     * @param pattern 表达式
     * @return java.util.Set<java.lang.String> 失败返回null
     * @author yaojianping
     * @date 2018/11/28 15:04
     */
    public Set<String> getKeys(String pattern) {
        try {
            return getTemple().keys(pattern + "*");
        } catch (Exception e) {
            logger.error("redis异常，pattern：{}", pattern, e);
            return null;
        }
    }

    /**
     * 获取key对应的值
     *
     * @param key 键
     * @return java.lang.Object 失败返回null
     * @author yaojianping
     * @date 2018/11/28 15:07
     */
    public Object get(final String key) {
        try {
            ValueOperations<String, Object> valueOperations = getTemple().opsForValue();
            return valueOperations.get(key);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return null;
        }
    }

    /**
     * 获取key对应的值，并更新缓存时间
     *
     * @param key        键
     * @param expireTime 过期时间，单位：秒
     * @return java.lang.Object 失败返回null
     * @author yaojianping
     * @date 2018/11/28 15:08
     */
    public Object getAndUpdateTime(String key, long expireTime) {
        try {
            Object result = get(key);
            if (result != null) {
                set(key, result, expireTime);
            }
            return result;
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return null;
        }
    }

    /**
     * 获取key对应的值，并更新缓存时间
     *
     * @param key        键
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return java.lang.Object 失败返回null
     * @author yaojianping
     * @date 2018/11/28 15:10
     */
    public Object getAndUpdateTime(String key, long expireTime, TimeUnit timeUnit) {
        try {
            Object result = get(key);
            if (result != null) {
                set(key, result, expireTime, timeUnit);
            }
            return result;
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return null;
        }
    }

    /**
     * String递增，key不存在，先被初始化为0，再把递增值返回，key不过期
     *
     * @param key 键
     * @param l   递增数
     * @return long 失败返回-1
     * @author yaojianping
     * @date 2018/11/28 15:12
     */
    public long incr(String key, long l) {
        try {
            ValueOperations<String, Object> valueOperations = getTemple().opsForValue();
            return valueOperations.increment(key, l);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return -1;
        }
    }

    /**
     * String递增，key不存在，先被初始化为0，再把递增值返回，key会过期
     *
     * @param key        键
     * @param l          递增数
     * @param expireTime 过期时间，单位：秒
     * @return long 失败返回-1
     * @author yaojianping
     * @date 2018/11/28 15:19
     */
    public long incr(String key, long l, long expireTime) {
        try {
            return incr(key, l, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return -1;
        }
    }

    /**
     * String递增，key不存在，先被初始化为0，再把递增值返回，key会过期
     *
     * @param key        键
     * @param l          递增数
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return long 失败返回-1
     * @author yaojianping
     * @date 2018/11/28 15:21
     */
    public long incr(String key, long l, long expireTime, TimeUnit timeUnit) {
        try {
            ValueOperations<String, Object> valueOperations = getTemple().opsForValue();
            long increment = valueOperations.increment(key, l);
            if (expireTime > 0) {
                getTemple().expire(key, expireTime, timeUnit);
            }
            return increment;
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return -1;
        }
    }

    /**
     * 删除key
     *
     * @param key 键
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 15:22
     */
    public boolean delete(String key) {
        try {
            getTemple().delete(key);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return false;
        }
        return true;
    }

    /**
     * 从hash表中获取数据
     *
     * @param key  键
     * @param item 域field
     * @return java.lang.Object 失败返回null
     * @author yaojianping
     * @date 2018/11/28 15:26
     */
    public Object hget(String key, String item) {
        try {
            return getTemple().opsForHash().get(key, item);
        } catch (Exception e) {
            logger.error("redis异常，key：{}，item：{}", key, item, e);
            return null;
        }
    }

    /**
     * 从hash表中批量获取数据
     *
     * @param key  键
     * @param item 域field
     * @return java.util.List<java.lang.Object> 失败返回null
     * @author yaojianping
     * @date 2018/11/28 15:29
     */
    public List<Object> hmget(String key, String... item) {
        try {
            return getTemple().opsForHash().multiGet(key, CollectionUtils.arrayToList(item));
        } catch (Exception e) {
            logger.error("redis异常，key：{}，item：{}", key, item, e);
            return null;
        }
    }

    /**
     * 从hash表中获取所有数据
     *
     * @param key 键
     * @return java.util.Map<java.lang.Object   ,   java.lang.Object> 失败返回null
     * @author yaojianping
     * @date 2018/11/28 15:31
     */
    public Map<Object, Object> hgetAll(String key) {
        try {
            return getTemple().opsForHash().entries(key);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return null;
        }
    }

    /**
     * 向hash表中存储数据
     *
     * @param key   键
     * @param item  域field
     * @param value 值
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 15:31
     */
    public boolean hset(String key, String item, Object value) {
        try {
            getTemple().opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            logger.error("redis异常，key：{}，item：{}，value：{}", key, item, value, e);
            return false;
        }
    }

    /**
     * 向hash表中存储数据，批量处理
     *
     * @param key 键
     * @param map 数据集
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 15:32
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            getTemple().opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error("redis异常，key：{}，map：{}", key, map, e);
            return false;
        }
    }

    /**
     * 从hash表中删除一个域field
     *
     * @param key  键
     * @param item 域field
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 15:34
     */
    public boolean hdel(String key, Object... item) {
        try {
            getTemple().opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            logger.error("redis异常，key：{}，item：{}", key, item, e);
            return false;
        }
    }

    /**
     * 从hash表中查找域field
     *
     * @param key  键
     * @param item 域field
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 15:35
     */
    public boolean hHasKey(String key, Object item) {
        try {
            getTemple().opsForHash().hasKey(key, item);
            return true;
        } catch (Exception e) {
            logger.error("redis异常，key：{}，item：{}", key, item, e);
            return false;
        }
    }

    /**
     * hash表递增，key不存在，先被初始化为0，再把递增值返回，key不过期
     *
     * @param key  键
     * @param item 域field
     * @param l    递增数
     * @return long 失败返回-1
     * @author yaojianping
     * @date 2018/11/28 15:12
     */
    public long hincr(String key, String item, long l) {
        try {
            return getTemple().opsForHash().increment(key, item, l);
        } catch (Exception e) {
            logger.error("redis异常，key：{}，item：{}", key, item, e);
            return -1;
        }
    }

    /**
     * 设置过期时间
     *
     * @param key      键
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return boolean true-成功，false-失败
     * @author yaojianping
     * @date 2018/11/28 15:47
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                getTemple().expire(key, time, timeUnit);
            }
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return false;
        }
        return true;
    }

    /**
     * 获取当前剩余过期时间，单位：秒
     *
     * @param key 键
     * @return long 返回0代表为永久有效，返回-1代表获取过期时间失败
     * @author yaojianping
     * @date 2018/11/28 15:48
     */
    public long getExpire(String key) {
        try {
            return getTemple().getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("redis异常，key：{}", key, e);
            return -1;
        }
    }

}
