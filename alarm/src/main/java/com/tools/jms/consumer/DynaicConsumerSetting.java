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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/30 11:25
 */
@Configuration
public class DynaicConsumerSetting {

    public static final String DYNAMIC_CONSUMER_CONFIG = "destinationName";

    @Autowired
    AbstractEnvironment environment;

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(new DynamicLoadPropertySource(DYNAMIC_CONSUMER_CONFIG, null));
    }

}
