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


import com.tools.base.basic.model.BaseDO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

/**
 * 公共service接口，提供增删改查四个基础方法
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 16:02
 */
@org.springframework.validation.annotation.Validated
public interface BaseService<T extends BaseDO, ID extends Serializable> extends Loggable, Convertable {
    /**
     * 根据主键ID查询行数据
     *
     * @param id 主键ID
     * @return java.util.Optional<T>
     * @author zhangduanfeng
     * @date 2018/12/9 14:13
     */
    Optional<T> get(@NotNull(message = "主键ID不可为空") ID id);

    /**
     * 插入一条数据，插入时间和修改时间自动更新，无需输入。有创建人和修改人字段的数据必须填写创建和修改人<br>
     * 自动接入事务
     *
     * @param data 实体DO
     * @author zhangduanfeng
     * @date 2018/12/9 14:14
     */
    void insert(@NotNull(message = "新增数据时，实体对象DO不可为空") T data);

    /**
     * 根据主键ID修改一条数据，修改时间自动更新，无需输入。有修改人字段的数据必须填写修改人。<br>
     * 自动接入事务
     *
     * @param data 实体DO
     * @author zhangduanfeng
     * @date 2018/12/9 14:15
     */
    void update(@NotNull(message = "修改数据时，实体对象DO不可为空") T data);

    /**
     * 根据主键ID删除一条数据，ID不可为空。<br>
     * 自动接入事务
     *
     * @param id 主键ID
     * @author zhangduanfeng
     * @date 2018/12/9 14:16
     */
    void delete(@NotNull(message = "主键ID不可为空") ID id);
}
