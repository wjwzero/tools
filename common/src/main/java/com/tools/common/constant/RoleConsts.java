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
 * 2018/12/11    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.constant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户模块，角色类型 常量
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/11 11:31
 */
public final class RoleConsts {
    /**
     * 超级管理员角色编码
     */
    public static final String ROLE_CODE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    /**
     * 系统管理员角色编码
     */
    public static final String ROLE_CODE_ADMIN = "ROLE_ADMIN";
    /**
     * 系统监控员角色编码
     */
    public static final String ROLE_CODE_MONITOR = "ROLE_MONITOR";
    /**
     * APP注册用户角色编码
     */
    public static final String ROLE_CODE_APP_USER = "ROLE_APP_USER";

    /**
     * 自定义角色编码前缀
     */
    public static final String ROLE_CODE_CUSTOM = "ROLE_CUSTOM_";

    /**
     * 角色类型：系统角色
     */
    public static final int ROLE_TYPE_SYSTEM = 1;
    /**
     * 角色类型：普通角色（由管理员创建的）
     */
    public static final int ROLE_TYPE_NORMAL = 0;

    /**
     * 权限公用URL，不关联任何资源：获取登录用户权限菜单（页面渲染）: {@value}
     */
    public static final String ROLE_COMMON_URL_INITIALIZATION_GET_PERMISSION = "/initialization/getPermission";

    /**
     * 权限公用URL，不关联任何资源：获取平台定制信息: {@value}
     */
    public static final String ROLE_COMMON_URL_GET_PLATFORM_INFO = "/getPlatform";

    /**
     * 权限公用URL，不关联任何资源：获取设备图标信息: {@value}
     */
    public static final String ROLE_COMMON_URL_LIST_DEVICE_ICON = "/device/listIcon";

    private static final Set<String> ROLE_COMMON_URLS;

    static {
        Set<String> roleCommonUrlSet = new HashSet<>(1);
        roleCommonUrlSet.add(ROLE_COMMON_URL_INITIALIZATION_GET_PERMISSION);
        roleCommonUrlSet.add(ROLE_COMMON_URL_GET_PLATFORM_INFO);
        roleCommonUrlSet.add(ROLE_COMMON_URL_LIST_DEVICE_ICON);

        ROLE_COMMON_URLS = Collections.unmodifiableSet(roleCommonUrlSet);
    }

    public static Set<String> getRoleCommonUrls() {
        return ROLE_COMMON_URLS;
    }


    private RoleConsts() {
        throw new UnsupportedOperationException();
    }

}
