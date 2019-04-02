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
 * 2018/10/18    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.constant;


import com.tools.base.util.Assert;

import java.util.Optional;

/**
 * 地图类型枚举
 *
 * @author yaojianping
 * @version 1.0
 * @date 2018/10/18 17:50
 */
public enum MapsEnum {

    /**
     * 百度
     */
    BAIDU("BAIDU", "baidu", "百度地图"),
//GOOGLE("GOOGLE", "google", "谷歌地图"),
    /**
     * 高德
     */
    AMAP("AMAP", "amap", "高德地图"),
    ;

    private String upName;

    private String lowName;

    private String description;

    MapsEnum(String upName, String lowName, String description) {
        this.upName = upName;
        this.lowName = lowName;
        this.description = description;
    }

    /**
     * 获取地图大写编码
     *
     * @return java.lang.String
     * @author yaojianping
     * @date 2018/10/18 18:01
     */
    public String getUpName() {
        return upName;
    }

    /**
     * 获取地图小写编码
     *
     * @return java.lang.String
     * @author yaojianping
     * @date 2018/10/18 18:01
     */
    public String getLowName() {
        return lowName;
    }

    /**
     * 获取描述
     *
     * @return java.lang.String
     * @author yaojianping
     * @date 2018/10/18 18:01
     */
    public String getDescription() {
        return description;
    }

    private static MapsEnum[] VALUES = MapsEnum.values();

    /**
     * 根据地图名称获取地图类型。
     *
     * @param name 地图名称
     * @return java.util.Optional&lt;com.jimi.together.base.constant.MapsEnum&gt;
     * @author zhangduanfeng
     * @date 2019/2/13 11:17
     */
    public static Optional<MapsEnum> findByName(String name) {
        Assert.notNull(name);
        name = name.toUpperCase();
        for (MapsEnum mapsEnum : VALUES) {
            if (mapsEnum.upName.equals(name)) {
                return Optional.of(mapsEnum);
            }
        }
        return Optional.empty();
    }
}
