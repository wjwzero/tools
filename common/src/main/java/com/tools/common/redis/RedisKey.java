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
 * 2018/12/20    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.redis;

/**
 * RedisKey，理论上Redis单机可支撑2.5亿个key。
 * <p>
 * Redis can handle up to 2^32 keys, and was tested in practice to handle at least 250 million keys per instance.
 * Every hash, list, set, and sorted set, can hold 2^32 elements.
 * <p>
 * In other words your limit is likely the available memory in your system.
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/20 16:15
 */
public final class RedisKey {
    /**
     * RedisKey默认前缀
     */
    private static final String PREFIX_TOGETHER = "together:";

    private RedisKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * OAuth2鉴权类缓存常量
     */
    public static class Oauth {
        /**
         * OAuth Redis前缀
         */
        public static final String PREFIX_OAUTH2 = PREFIX_TOGETHER + "oauth2:";
        /**
         * OAuth Redis access前缀
         */
        public static final String PREFIX_OAUTH2_ACCESS = PREFIX_OAUTH2 + "access:";
        /**
         * Oauth2自定义Key，前缀+userId=access_token
         */
        public static final String PREFIX_OAUTH2_USER_TO_ACCESS = PREFIX_OAUTH2 + "user_to_access:";
        /**
         * Oauth2自定义Key，前缀+userId=refresh_token
         */
        public static final String PREFIX_OAUTH2_USER_TO_REFRESH = PREFIX_OAUTH2 + "user_to_refresh:";

        private Oauth() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Role-Resource类缓存常量
     */
    public static class RoleResource {

        /**
         * Web端所有URL缓存，使用Hash存储 {@value}
         */
        public static final String WEB_FUNCTIONAL_ELEMENT = PREFIX_TOGETHER + "element:web:functional";
        /**
         * App端所有URL缓存，使用Hash存储 {@value}
         */
        public static final String APP_FUNCTIONAL_ELEMENT = PREFIX_TOGETHER + "element:app:functional";

        /**
         * 角色的元素缓存key
         */
        public static final String ROLE_PERMISSION = PREFIX_TOGETHER + "role_permission";

        private RoleResource() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 短信类缓存常量
     */
    public static class Sms {
        /**
         * 短信缓存前缀
         */
        private static final String PREFIX = PREFIX_TOGETHER + "sms:";
        /**
         * 仅用于短信登录验证码：{@value}
         */
        public static final String PIN_CODE = PREFIX + "pin:";

        private Sms() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * APP缓存常量
     */
    public static class App {
        /**
         * APP缓存前缀
         */
        private static final String PREFIX = PREFIX_TOGETHER + "app:";
        /**
         * 用户手机型号
         */
        public static final String TYPE = PREFIX + "type:";

        private App() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 系统设置缓存常量
     */
    public static class AlarmPushSetting {
        /**
         * 系统告警推送设置redis的key
         */
        public static final String PREFIX_SYSTEM_ALARM_PUSH_SETTING = PREFIX_TOGETHER + "alarm:setting:system:";
        public static final String PREFIX_PLATFORM_USER_ALARM_PUSH_SETTING = PREFIX_TOGETHER + "alarm:setting:platform_user:";
        public static final String PREFIX_APP_USER_ALARM_PUSH_SETTING = PREFIX_TOGETHER + "alarm:setting:app_user:";

        private AlarmPushSetting() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 系统设置缓存常量
     */
    public static class SystemSetting {
        public static final String PREFIX_SYSTEM = PREFIX_TOGETHER + "system:";
        /**
         * 系统告警推送设置redis的key
         */
        public static final String SYSTEM_BASIC_SETTING = PREFIX_SYSTEM + "basic_setting";
        public static final String SYSTEM_COMPANY_INFO = PREFIX_SYSTEM + "company_info";
        public static final String SYSTEM_ALARM_SETTING = PREFIX_SYSTEM + "alarm_setting";
        /**
         * 系统告警推送设置的redis的key
         */
        public static final String SYSTEM_ALARM_PUSH_SETTING = PREFIX_SYSTEM + "alarm:setting:";

        private SystemSetting() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 统计缓存常量，
     */
    public static class Count {
        /**
         * 统计包名前缀，{@value}
         */
        private static final String PREFIX_COUNT = PREFIX_TOGETHER + "count:";
        /**
         * 设备统计前缀，单独的Key存储所有设备相关统计，如总量统计、未关联统计和过期统计。，{@value}
         */
        private static final String PREFIX_COUNT_DEVICE = PREFIX_COUNT + "device:";
        /**
         * 设备总量统计Key名，{@value}
         */
        public static final String COUNT_DEVICE_TOTAL = PREFIX_COUNT_DEVICE + "total";
        /**
         * 设备未关联分组统计Key名，{@value}
         */
        public static final String COUNT_DEVICE_UNCORRELATION = PREFIX_COUNT_DEVICE + "uncorrelation";
        /**
         * 设备30天到期统计Key名，{@value}
         */
        public static final String COUNT_DEVICE_EXPIRES_THIRTY_DAYS = PREFIX_COUNT_DEVICE + "expires_thirty_days";

        /**
         * 分组设备统计Key名前缀。{@value}
         */
        public static final String PREFIX_COUNT_GROUP = PREFIX_COUNT + "group:";
        /**
         * 分组设备统计Key名前缀，不含下级，不存hash。{@value}
         */
        public static final String PREFIX_COUNT_DEVICE_GROUP = PREFIX_COUNT_GROUP + "itself_device:";

        /**
         * 分组设备统计Key名前缀，含下级，不存hash。{@value}
         */
        public static final String PREFIX_COUNT_DEVICE_GROUP_LOWER = PREFIX_COUNT_GROUP + "lower_device:";

        /**
         * 分组未激活设备统计Key名前缀，不含下级，不存hash。{@value}
         */
        public static final String PREFIX_COUNT_DEVICE_GROUP_UNACTIVATED = PREFIX_COUNT_GROUP +
                "itself_unactivated_device:";

        /**
         * 分组未激活设备统计Key名前缀，含下级，不存hash。{@value}
         */
        public static final String PREFIX_COUNT_DEVICE_GROUP_LOWER_UNACTIVATED = PREFIX_COUNT_GROUP +
                "lower_unactivated_device:";

        private Count() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * 告警用户设置Redis常量
     */
    public static class AlarmPush {
        /**
         * 告警用户设置缓存前缀
         */
        private static final String PREFIX = PREFIX_TOGETHER + "alarm:push:";
        /**
         * 告警用户设置缓存前缀Key
         */
        public static final String USER_SETTING = PREFIX + "user_setting";
        /**
         * 告警通信管道纬度未阅读数
         */
        public static final String CHANNEL_UNREAD = PREFIX + "channel_unread";
        /**
         * websocket分组通信管道缓存
         */
        public static final String CHANNEL_CACHE = PREFIX + "channel_cache";

        private AlarmPush() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 轨迹回放经纬度解析
     */
    public static class TrackPoi {
        /**
         * 轨迹回放记录解析过的经纬度
         */
        public static final String TRACK_POI_LAT_LNG = PREFIX_TOGETHER + "poi:track:lat_lng";

        private TrackPoi() {
            throw new UnsupportedOperationException();
        }
    }
}
