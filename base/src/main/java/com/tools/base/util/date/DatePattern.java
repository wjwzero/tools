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
 * 2018/11/23    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util.date;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间格式化模板类
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/23 17:18
 */
public final class DatePattern {

    /**
     * 时间格式化模板：{@value}
     */
    public static final String TIME_HHMMSS = "HHmmss";

    // public static final String TIME_h = "h";
    // public static final String TIME_a = "a";
    // public static final String TIME_ah = "ah";
    // public static final String TIME_HH = "HH";
    // public static final String TIME_mm = "mm";
    // public static final String TIME_ss = "ss";
    // public static final String TIME_SSS = "SSS";
    // public static final String TIME_hmmss = "hmmss";
    // public static final String TIME_h_mm_ss = "h:mm:ss";
    // public static final String TIME_ahmmss = "ahmmss";
    // public static final String TIME_ah_mm_ss = "ah:mm:ss";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String TIME_HH_MM_SS = "HH:mm:ss";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATE_MMDD = "MMdd";
    // public static final String DATE_yy = "yy";
    // public static final String DATE_yyyy = "yyyy";
    // public static final String DATE_MM = "MM";
    // public static final String DATE_dd = "dd";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATE_MM_DD = "MM-dd";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATE_YYYYMM = "yyyyMM";
    // public static final String DATE_yyMM = "yyMM";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATE_YYYY_MM = "yyyy-MM";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATE_YYYYMMDD = "yyyyMMdd";
    // public static final String DATE_yyMMdd = "yyMMdd";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
    // public static final String DATE_yy_MM_dd = "yy-MM-dd";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATETIME_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATETIME_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATETIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式化模板：{@value}
     */
    public static final String DATETIME_MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    // public static final String DATETIME_yy_MM_dd_h_mm_ss = "yyyy-MM-dd
    // h:mm:ss";
    // public static final String DATETIME_yy_MM_dd_h_mm_ss_Z = "yyyy-MM-dd
    // h:mm:ssZ";
    // public static final String DATETIME_yy_MM_dd_h_mm_ss_UTC = "yyyy-MM-dd
    // h:mm:ss'Z'";
    // public static final String DATETIME_yy_MM_dd_h_mm_ss_SSS = "yyyy-MM-dd
    // h:mm:ss.SSS";
    // public static final String DATETIME_yy_MM_dd_h_mm_ss_SSS_Z = "yyyy-MM-dd
    // h:mm:ss.SSSZ";
    // public static final String DATETIME_yy_MM_dd_h_mm_ss_SSS_UTC =
    // "yyyy-MM-dd h:mm:ss.SSS'Z'";
    // public static final String DATETIME_yy_MM_dd_ah_mm_ss = "yyyy-MM-dd
    // ah:mm:ss";
    // public static final String DATETIME_yy_MM_dd_ah_mm_ss_Z = "yyyy-MM-dd
    // ah:mm:ssZ";
    // public static final String DATETIME_yy_MM_dd_ah_mm_ss_SSS = "yyyy-MM-dd
    // ah:mm:ss.SSS";
    // public static final String DATETIME_yy_MM_dd_ah_mm_ss_SSS_Z = "yyyy-MM-dd
    // ah:mm:ss.SSSZ";
    // public static final String DATETIME_yy_MM_dd_ah_mm_ss_SSS_UTC =
    // "yyyy-MM-dd ah:mm:ss.SSS'Z'";
    // public static final String DATETIME_yy_MM_dd_HH_mm_ss =
    // "yyyy-MM-dd'T'HH:mm:ss";
    // public static final String DATETIME_yy_MM_dd_HH_mm_ss_Z =
    // "yyyy-MM-dd'T'HH:mm:ssZ";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String SLASH_DATE_YYYY_MM_DD = "yyyy/MM/dd";
    // public static final String DATETIME_yyyy_MM_dd_HH_mm_ss_Z = "yyyy-MM-dd
    // HH:mm:ssZ";
    // public static final String DATETIME_yyyy_MM_ddTHH_mm_ss =
    // "yyyy-MM-dd'T'HH:mm:ss";
    // public static final String DATETIME_yyyy_MM_ddTHH_mm_ss_Z =
    // "yyyy-MM-dd'T'HH:mm:ssZ";
    // public static final String DATETIME_yyyy_MM_ddTHH_mm_ss_SSS =
    // "yyyy-MM-dd'T'HH:mm:ss.SSS";
    // public static final String DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_Z =
    // "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    // public static final String DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_UTC =
    // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // public static final String SLASH_DATE_yy_MM_dd = "yy/MM/dd";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String SLASH_DATETIME_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    // public static final String SLASH_DATETIME_yyyy_MM_dd_h_mm_ss =
    // "yyyy/MM/dd h:mm:ss";
    // public static final String SLASH_DATETIME_yyyy_MM_dd_h_mm_ss_Z =
    // "yyyy/MM/dd h:mm:ssZ";
    // public static final String SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss =
    // "yyyy/MM/dd ah:mm:ss";
    // public static final String SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss_Z =
    // "yyyy/MM/dd ah:mm:ssZ";
    // public static final String SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss_UTC =
    // "yyyy/MM/dd ah:mm:ss'Z'";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String CHINESE_DATE_YYYY_MM_DD = "yyyy年MM月dd日";
    // public static final String SLASH_DATETIME_yyyy_MM_dd_HH_mm_ss_Z =
    // "yyyy/MM/dd HH:mm:ssZ";
    // public static final String SLASH_DATETIME_yyyy_MM_dd_HH_mm_ss_UTC =
    // "yyyy/MM/dd HH:mm:ss'Z'";
    // public static final String SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss =
    // "yyyy/MM/dd'T'HH:mm:ss";
    // public static final String SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS =
    // "yyyy/MM/dd'T'HH:mm:ss.SSS";
    // public static final String SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_Z =
    // "yyyy/MM/dd'T'HH:mm:ss.SSSZ";
    // public static final String SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_UTC =
    // "yyyy/MM/dd'T'HH:mm:ss.SSS'Z'";

    // public static final String CHINESE_TIME_h_mm_ss = "h时mm分ss秒";
    // public static final String CHINESE_TIME_AH_MM_SS = "ah时mm分ss秒";
    // public static final String CHINESE_TIME_HH_mm_ss = "HH时mm分ss秒";
    // public static final String CHINESE_DATE_yy_MM_dd = "yy年MM月dd日";
    /**
     * 时间格式化模板：{@value}
     */
    public static final String CHINESE_DATETIME_YYYY_MM_DDHH_MM_SS = "yyyy年MM月dd日HH时mm分ss秒";
    // public static final String CHINESE_DATETIME_yyyy_MM_ddh_mm_ss =
    // "yyyy年MM月dd日h时mm分ss秒";
    // public static final String CHINESE_DATETIME_yyyy_MM_ddh_mm_ss_Z =
    // "yyyy年MM月dd日h时mm分ss秒Z";
    // public static final String CHINESE_DATETIME_yyyy_MM_ddah_mm_ss =
    // "yyyy年MM月dd日ah时mm分ss秒";
    // public static final String CHINESE_DATETIME_yyyy_MM_ddah_mm_ss_Z =
    // "yyyy年MM月dd日ah时mm分ss秒Z";
    // public static final String CHINESE_DATETIME_yyyy_MM_ddHH_mm_ss_Z =
    // "yyyy年MM月dd日HH时mm分ss秒Z";
    // public static final String CHINESE_DATETIME_yyyy_MM_dd_HH_mm_ss =
    // "yyyy年MM月dd日 HH时mm分ss秒";
    // public static final String CHINESE_DATETIME_yyyy_MM_dd_HH_mm_ss_Z =
    // "yyyy年MM月dd日 HH时mm分ss秒";
    /**
     * {@link DateTimeFormatter}缓存Map。
     */
    private static final Map<String, DateTimeFormatter> DATE_PATTERN_CACHE = new HashMap<>(20);
    static {
        // cached.put(TIME_h, DateTimeFormatter.ofPattern(TIME_h));
        // cached.put(TIME_a, DateTimeFormatter.ofPattern(TIME_a));
        // cached.put(TIME_ah, DateTimeFormatter.ofPattern(TIME_ah));
        // cached.put(TIME_HH, DateTimeFormatter.ofPattern(TIME_HH));
        // cached.put(TIME_mm, DateTimeFormatter.ofPattern(TIME_mm));
        // cached.put(TIME_ss, DateTimeFormatter.ofPattern(TIME_ss));
        // cached.put(TIME_SSS, DateTimeFormatter.ofPattern(TIME_SSS));
        // cached.put(TIME_hmmss, DateTimeFormatter.ofPattern(TIME_hmmss));
        // cached.put(TIME_h_mm_ss, DateTimeFormatter.ofPattern(TIME_h_mm_ss));
        // cached.put(TIME_ahmmss, DateTimeFormatter.ofPattern(TIME_ahmmss));
        // cached.put(TIME_ah_mm_ss, DateTimeFormatter.ofPattern(TIME_ah_mm_ss));
        DATE_PATTERN_CACHE.put(TIME_HHMMSS, DateTimeFormatter.ofPattern(TIME_HHMMSS));
        DATE_PATTERN_CACHE.put(TIME_HH_MM_SS, DateTimeFormatter.ofPattern(TIME_HH_MM_SS));
        // cached.put(DATE_yy, DateTimeFormatter.ofPattern(DATE_yy));
        // cached.put(DATE_yyyy, DateTimeFormatter.ofPattern(DATE_yyyy));
        // cached.put(DATE_MM, DateTimeFormatter.ofPattern(DATE_MM));
        // cached.put(DATE_dd, DateTimeFormatter.ofPattern(DATE_dd));
        DATE_PATTERN_CACHE.put(DATE_MM_DD, DateTimeFormatter.ofPattern(DATE_MM_DD));
        DATE_PATTERN_CACHE.put(DATE_MMDD, DateTimeFormatter.ofPattern(DATE_MMDD));
        // cached.put(DATE_yyMM, DateTimeFormatter.ofPattern(DATE_yyMM));
        DATE_PATTERN_CACHE.put(DATE_YYYYMM, DateTimeFormatter.ofPattern(DATE_YYYYMM));
        DATE_PATTERN_CACHE.put(DATE_YYYY_MM, DateTimeFormatter.ofPattern(DATE_YYYY_MM));
        // cached.put(DATE_yyMMdd, DateTimeFormatter.ofPattern(DATE_yyMMdd));
        DATE_PATTERN_CACHE.put(DATE_YYYYMMDD, DateTimeFormatter.ofPattern(DATE_YYYYMMDD));
        // cached.put(DATE_yy_MM_dd, DateTimeFormatter.ofPattern(DATE_yy_MM_dd));
        DATE_PATTERN_CACHE.put(DATE_YYYY_MM_DD, DateTimeFormatter.ofPattern(DATE_YYYY_MM_DD));
        DATE_PATTERN_CACHE.put(DATETIME_YYYYMMDDHHMMSS, DateTimeFormatter.ofPattern(DATETIME_YYYYMMDDHHMMSS));
        DATE_PATTERN_CACHE.put(DATETIME_YYYYMMDDHHMMSSSSS, DateTimeFormatter.ofPattern(DATETIME_YYYYMMDDHHMMSSSSS));
        // cached.put(DATETIME_yy_MM_dd_h_mm_ss,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_h_mm_ss));
        // cached.put(DATETIME_yy_MM_dd_h_mm_ss_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_h_mm_ss_Z));
        // cached.put(DATETIME_yy_MM_dd_h_mm_ss_UTC,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_h_mm_ss_UTC));
        // cached.put(DATETIME_yy_MM_dd_h_mm_ss_SSS,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_h_mm_ss_SSS));
        // cached.put(DATETIME_yy_MM_dd_h_mm_ss_SSS_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_h_mm_ss_SSS_Z));
        // cached.put(DATETIME_yy_MM_dd_h_mm_ss_SSS_UTC,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_h_mm_ss_SSS_UTC));
        // cached.put(DATETIME_yy_MM_dd_ah_mm_ss,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_ah_mm_ss));
        // cached.put(DATETIME_yy_MM_dd_ah_mm_ss_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_ah_mm_ss_Z));
        // cached.put(DATETIME_yy_MM_dd_ah_mm_ss_SSS,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_ah_mm_ss_SSS));
        // cached.put(DATETIME_yy_MM_dd_ah_mm_ss_SSS_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_ah_mm_ss_SSS_Z));
        // cached.put(DATETIME_yy_MM_dd_ah_mm_ss_SSS_UTC,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_ah_mm_ss_SSS_UTC));
        // cached.put(DATETIME_yy_MM_dd_HH_mm_ss,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_HH_mm_ss));
        // cached.put(DATETIME_yy_MM_dd_HH_mm_ss_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yy_MM_dd_HH_mm_ss_Z));
        DATE_PATTERN_CACHE.put(DATETIME_YYYY_MM_DD_HH_MM_SS, DateTimeFormatter.ofPattern(DATETIME_YYYY_MM_DD_HH_MM_SS));
        // cached.put(DATETIME_yyyy_MM_dd_HH_mm_ss_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yyyy_MM_dd_HH_mm_ss_Z));
        // cached.put(DATETIME_yyyy_MM_ddTHH_mm_ss,
        // DateTimeFormatter.ofPattern(DATETIME_yyyy_MM_ddTHH_mm_ss));
        // cached.put(DATETIME_yyyy_MM_ddTHH_mm_ss_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yyyy_MM_ddTHH_mm_ss_Z));
        // cached.put(DATETIME_yyyy_MM_ddTHH_mm_ss_SSS,
        // DateTimeFormatter.ofPattern(DATETIME_yyyy_MM_ddTHH_mm_ss_SSS));
        // cached.put(DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_Z,
        // DateTimeFormatter.ofPattern(DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_Z));
        // cached.put(DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_UTC,
        // DateTimeFormatter.ofPattern(DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_UTC));
        // cached.put(SLASH_DATE_yy_MM_dd,
        // DateTimeFormatter.ofPattern(SLASH_DATE_yy_MM_dd));
        DATE_PATTERN_CACHE.put(SLASH_DATE_YYYY_MM_DD, DateTimeFormatter.ofPattern(SLASH_DATE_YYYY_MM_DD));
        // cached.put(SLASH_DATETIME_yyyy_MM_dd_h_mm_ss,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_dd_h_mm_ss));
        // cached.put(SLASH_DATETIME_yyyy_MM_dd_h_mm_ss_Z,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_dd_h_mm_ss_Z));
        // cached.put(SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss));
        // cached.put(SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss_Z,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss_Z));
        // cached.put(SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss_UTC,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_dd_ah_mm_ss_UTC));
        DATE_PATTERN_CACHE.put(SLASH_DATETIME_YYYY_MM_DD_HH_MM_SS,
                DateTimeFormatter.ofPattern(SLASH_DATETIME_YYYY_MM_DD_HH_MM_SS));
        // cached.put(SLASH_DATETIME_yyyy_MM_dd_HH_mm_ss_Z,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_dd_HH_mm_ss_Z));
        // cached.put(SLASH_DATETIME_yyyy_MM_dd_HH_mm_ss_UTC,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_dd_HH_mm_ss_UTC));
        // cached.put(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss));
        // cached.put(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS));
        // cached.put(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_Z,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_Z));
        // cached.put(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_UTC,
        // DateTimeFormatter.ofPattern(SLASH_DATETIME_yyyy_MM_ddTHH_mm_ss_SSS_UTC));
        // cached.put(CHINESE_TIME_h_mm_ss,
        // DateTimeFormatter.ofPattern(CHINESE_TIME_h_mm_ss));
        // cached.put(CHINESE_TIME_AH_MM_SS,
        // DateTimeFormatter.ofPattern(CHINESE_TIME_AH_MM_SS));
        // cached.put(CHINESE_TIME_HH_mm_ss,
        // DateTimeFormatter.ofPattern(CHINESE_TIME_HH_mm_ss));
        // cached.put(CHINESE_DATE_yy_MM_dd,
        // DateTimeFormatter.ofPattern(CHINESE_DATE_yy_MM_dd));
        DATE_PATTERN_CACHE.put(CHINESE_DATE_YYYY_MM_DD, DateTimeFormatter.ofPattern(CHINESE_DATE_YYYY_MM_DD));
        // cached.put(CHINESE_DATETIME_yyyy_MM_ddh_mm_ss,
        // DateTimeFormatter.ofPattern(CHINESE_DATETIME_yyyy_MM_ddh_mm_ss));
        // cached.put(CHINESE_DATETIME_yyyy_MM_ddh_mm_ss_Z,
        // DateTimeFormatter.ofPattern(CHINESE_DATETIME_yyyy_MM_ddh_mm_ss_Z));
        // cached.put(CHINESE_DATETIME_yyyy_MM_ddah_mm_ss,
        // DateTimeFormatter.ofPattern(CHINESE_DATETIME_yyyy_MM_ddah_mm_ss));
        // cached.put(CHINESE_DATETIME_yyyy_MM_ddah_mm_ss_Z,
        // DateTimeFormatter.ofPattern(CHINESE_DATETIME_yyyy_MM_ddah_mm_ss_Z));
        DATE_PATTERN_CACHE.put(CHINESE_DATETIME_YYYY_MM_DDHH_MM_SS,
                DateTimeFormatter.ofPattern(CHINESE_DATETIME_YYYY_MM_DDHH_MM_SS));
        // cached.put(CHINESE_DATETIME_yyyy_MM_ddHH_mm_ss_Z,
        // DateTimeFormatter.ofPattern(CHINESE_DATETIME_yyyy_MM_ddHH_mm_ss_Z));
        // cached.put(CHINESE_DATETIME_yyyy_MM_dd_HH_mm_ss,
        // DateTimeFormatter.ofPattern(CHINESE_DATETIME_yyyy_MM_dd_HH_mm_ss));
        // cached.put(CHINESE_DATETIME_yyyy_MM_dd_HH_mm_ss_Z,
        // DateTimeFormatter.ofPattern(CHINESE_DATETIME_yyyy_MM_dd_HH_mm_ss_Z));

    }

    private DatePattern() {
        throw new UnsupportedOperationException();
    }

    /**
     * 将时间格式化字符串转换为java8时间格式化类
     *
     * @param pattern 时间字符串格式模板
     * @return {@link DateTimeFormatter}
     * @author zhangduanfeng
     * @date 2018/10/24 10:18
     */
    public static DateTimeFormatter ofPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return null;
        }
        if (DATE_PATTERN_CACHE.containsKey(pattern)) {
            return DATE_PATTERN_CACHE.get(pattern);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        DateTimeFormatter old = DATE_PATTERN_CACHE.putIfAbsent(pattern, DateTimeFormatter.ofPattern(pattern));
        if (old != null) {
            DATE_PATTERN_CACHE.replace(pattern, old, formatter);
        }
        return formatter;
    }
}
