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


package com.tools.common.basic.model;

import com.jimi.together.base.basic.model.BaseDO;

/**
 * 基础DO，自增主键实体类
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 16:39
 */
@SuppressWarnings("serial")
public abstract class BaseLongKeyDO extends BaseDO {
    /**
     * 数据库主键ID
     */
    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
