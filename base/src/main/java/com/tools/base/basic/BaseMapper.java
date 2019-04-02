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

import java.io.Serializable;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 16:06
 */
public interface BaseMapper<T extends BaseDO, ID extends Serializable> {

    /**
     * 根据主键ID查询行数据
     *
     * @param id 主键ID
     * @return T
     * @author zhangduanfeng
     * @date 2018/12/9 14:13
     */
    T get(ID id);

    /**
     * 插入一条数据，插入时间和修改时间自动更新，无需输入。有创建人和修改人字段的数据必须填写创建和修改人<br/>
     *
     * @param data 实体DO
     * @author zhangduanfeng
     * @date 2018/12/9 14:14
     */
    void insert(T data);

    /**
     * 根据主键ID修改一条数据，修改时间自动更新，无需输入。有修改人字段的数据必须填写修改人。
     *
     * @param data 实体DO
     * @author zhangduanfeng
     * @date 2018/12/9 14:15
     */
    void update(T data);

    /**
     * 根据主键ID删除一条数据，ID不可为空。
     *
     * @param id 主键ID
     * @author zhangduanfeng
     * @date 2018/12/9 14:16
     */
    void delete(ID id);

}
