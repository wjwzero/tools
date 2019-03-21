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
 * 2018/11/28    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/11/28 10:21
 */
@Component
public class RedisUtils extends AbstractRedisCmd {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public RedisTemplate<String, Object> getTemple() {
        return this.redisTemplate;
    }
}
