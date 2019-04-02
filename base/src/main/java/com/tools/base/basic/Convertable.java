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
 * 2018/12/10    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.basic;


import com.tools.base.basic.model.IBaseModel;
import com.tools.base.util.BeanUtils;

import java.util.List;

/**
 * 提供默认方法转换Bean类型
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/10 16:41
 */
public interface Convertable {
    /**
     * Bean转换方法
     *
     * @param t      源对象
     * @param rClass 目标类型
     * @return R
     * @author zhangduanfeng
     * @date 2018/12/9 14:23
     */
    default <R extends IBaseModel, T extends IBaseModel> R convert(T t, Class<R> rClass) {
        return BeanUtils.copy(t, rClass);
    }

    /**
     * Bean转换方法
     *
     * @param t 源对象
     * @param r 目标对象
     * @author zhangduanfeng
     * @date 2018/12/9 17:31
     */
    default <R extends IBaseModel, T extends IBaseModel> void convert(T t, R r) {
        BeanUtils.copyProperties(t, r);
    }

    /**
     * 集合转换类型方法
     *
     * @param tList  源集合
     * @param rClass 目标类型
     * @return java.util.List<R>
     * @author zhangduanfeng
     * @date 2018/12/10 16:45
     */
    default <R extends IBaseModel, T extends IBaseModel> List<R> convert(List<T> tList, Class<R> rClass) {
        return BeanUtils.copyList(tList, rClass);
    }

}
