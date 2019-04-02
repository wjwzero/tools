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
 * 2018/11/26    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供默认方法获取日志对象{@link Logger}
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 16:01
 */
public interface Loggable {
    /**
     * 获取当前类的{@link Logger}对象
     *
     * @return org.slf4j.Logger
     * @author zhangduanfeng
     * @date 2018/12/10 16:39
     */
    default Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
