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
 * 2019/3/28    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.basic.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DO基类
 * @author wangjianwei
 * @version 1.0
 * @date 2019/3/28 18:01
 */
public abstract class BaseDO extends BaseModel {
    /**
     * 创建时间
     */
    protected LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    protected LocalDateTime gmtModified;

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取主键ID
     *
     * @return ID
     * @author zhangduanfeng
     * @date 2018/12/9 14:44
     */
    public abstract <ID extends Serializable> ID getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseDO baseDO = (BaseDO) o;
        return Objects.equals(baseDO.getId(), this.getId());
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return Objects.hash(this.getClass().getName(), this.getId());
    }
}
