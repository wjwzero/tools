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

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/30 11:28
 */
public class DynamicLoadPropertySource extends MapPropertySource {

    private static Map<String, Object> map = new ConcurrentHashMap<String, Object>(64);

    public static void setConsumerDestinationName(String destinationName){
        map.put("destinationName", destinationName);
    }

    public DynamicLoadPropertySource(String name, Map<String, Object> source) {
        super(name, map);
    }


    @Override
    public Object getProperty(String name) {
        return map.get(name);
    }
}
