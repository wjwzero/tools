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
 * 2018/12/5    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.basic;

import com.tools.base.basic.BaseMapper;
import com.tools.base.basic.BaseService;
import com.tools.base.basic.model.BaseDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 抽象service，仅提供get、insert、update、delete等四种基础方法的实现
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/5 19:04
 */

public abstract class AbstractService<T extends BaseDO, ID extends Serializable> implements BaseService<T, ID> {

    @Autowired
    private BaseMapper<T, ID> mapper;

    @Override
    public Optional<T> get(ID id) {
        return Optional.ofNullable(mapper.get(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(T data) {
        Objects.requireNonNull(data, "Persistence data must not be null");
        mapper.insert(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(T data) {
        Objects.requireNonNull(data, "Persistence data must not be null");
        mapper.update(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ID id) {
        if (get(id).isPresent()) {
            mapper.delete(id);
        }
    }
}
