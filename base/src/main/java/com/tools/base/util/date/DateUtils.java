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


import com.tools.base.util.Assert;
import com.tools.base.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * Java8时间操作类，只用于Java8的时间数据转换。
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/23 17:20
 */
public final class DateUtils {
    private DateUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 将{@link Date}对象转换为"yyyy-MM-dd HH:mm:ss"格式的时间字符串。
     *
     * @param date 接收java.util包下的{@link Date}时间对象。
     * @return 返回固定格式为"yyyy-MM-dd HH:mm:ss"的时间字符串(java.lang.String)。
     * @author zhangduanfeng
     * @date 2018/10/23 10:03
     */
    public static String format(Date date) {
        return format(date, DatePattern.ofPattern(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 将{@link Date}对象转换为"pattern"指定格式的时间字符串。
     *
     * @param date    接收java.util包下的{@link Date}时间对象。
     * @param pattern 接收符合时间格式化规则的字符串模板
     * @return 返回符合时间格式化模板"pattern"规则的时间字符串(java.lang.String)。
     * @author zhangduanfeng
     * @date 2018/10/23 10:06
     */
    public static String format(Date date, String pattern) {
        return format(date, DatePattern.ofPattern(pattern));
    }

    /**
     * 将{@link Date}对象转换为"pattern"指定格式的时间字符串
     * @param date      接收java.util包下的{@link Date}时间对象
     * @param formatter {@link DateTimeFormatter}
     * @return 返回符合时间格式化模板"pattern"规则的时间字符串(java.lang.String)。
     * @author zhangduanfeng
     * @date 2018/10/23 10:06
     */
    public static String format(Date date, DateTimeFormatter formatter) {
        if (date == null) {
            return StringUtils.EMPTY;
        }

        if (formatter == null) {
            formatter = DatePattern.ofPattern(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS);
        }

        return formatter.format(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     *
     * @param localTime
     * @return 返回固定格式为"HH:mm:ss"的时间字符串(java.lang.String)。
     * @author  zhangduanfeng
     * @date 2019/2/13 9:53
     */
    public static String format(LocalTime localTime) {
        return format(localTime, DatePattern.ofPattern(DatePattern.TIME_HH_MM_SS));
    }

    public static String format(LocalTime localTime, String pattern) {
        return format(localTime, DatePattern.ofPattern(pattern));
    }

    public static String format(LocalTime localTime, DateTimeFormatter formatter) {
        if (localTime == null) {
            return StringUtils.EMPTY;
        }

        if (formatter == null) {
            formatter = DatePattern.ofPattern(DatePattern.TIME_HH_MM_SS);
        }

        return formatter.format(localTime);
    }

    public static String format(LocalDate localDate) {
        return format(localDate, DatePattern.ofPattern(DatePattern.DATE_YYYY_MM_DD));
    }

    public static String format(LocalDate localDate, String pattern) {
        return format(localDate, DatePattern.ofPattern(pattern));
    }

    public static String format(LocalDate localDate, DateTimeFormatter formatter) {
        if (localDate == null) {
            return StringUtils.EMPTY;
        }
        if (formatter == null) {
            formatter = DatePattern.ofPattern(DatePattern.DATE_YYYY_MM_DD);
        }
        return formatter.format(localDate);
    }

    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, DatePattern.ofPattern(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS));
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime, DatePattern.ofPattern(pattern));
    }

    public static String format(LocalDateTime localDateTime, DateTimeFormatter formatter) {

        if (localDateTime == null) {
            return StringUtils.EMPTY;
        }

        if (formatter == null) {
            formatter = DatePattern.ofPattern(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS);
        }

        return formatter.format(localDateTime);
    }

    /**
     * 将1970-01-01 08:00:00到现在的长整型时间戳格式化为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param millis 1970-01-01 08:00:00到现在的长整型时间戳
     * @return java.lang.String
     * @author zhangduanfeng
     * @date 2018/12/24 17:04
     */
    public static String format(long millis) {
        return format(millis, DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将1970-01-01 08:00:00到现在的长整型时间戳格式化为指定格式的字符串
     *
     * @param millis  1970-01-01 08:00:00到现在的长整型时间戳
     * @param pattern 指定格式化字符串
     * @return java.lang.String
     * @author zhangduanfeng
     * @date 2018/12/24 17:04
     */
    public static String format(long millis, String pattern) {
        return format(millis, DatePattern.ofPattern(pattern));
    }

    /**
     * 将1970-01-01 08:00:00到现在的长整型时间戳格式化为指定格式的字符串
     *
     * @param millis    1970-01-01 08:00:00到现在的长整型时间戳
     * @param formatter 指定格式化对象{@link DateTimeFormatter}
     * @return java.lang.String
     * @author zhangduanfeng
     * @date 2018/12/24 17:04
     */
    public static String format(long millis, DateTimeFormatter formatter) {

        if (formatter == null) {
            formatter = DatePattern.ofPattern(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS);
        }

        return formatter.format(toLocalDateTime(millis));
    }


    public static Date parse(String strVal, String pattern) {
        Assert.hasText(strVal, "Empty string {strVal}");
        if (pattern == null) {
            pattern = DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS;
        }
        return Date.from(parseDateTime(strVal, pattern).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime parseDateTime(String strVal, String pattern) {
        Objects.requireNonNull(strVal);
        if (pattern == null) {
            pattern = DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS;
        }
        return LocalDateTime.parse(strVal, DatePattern.ofPattern(pattern));
    }


    /**
     * @return java.util.Date
     * @author zhangduanfeng
     * @date 2018/10/24 9:31
     */
    public static Date now() {
        return new Date();
    }

    public static Date toDate(LocalTime localTime) {
        return toDate(localTime.atDate(LocalDate.now()));
    }

    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atTime(LocalTime.now()));
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date yesterday() {
        return toDate(LocalDateTime.now().minusDays(1));
    }

    public static Date yesterday(long millisOfDay) {
        return toDate(LocalDateTime.now().minusDays(1).with(ChronoField.MILLI_OF_DAY, millisOfDay));
    }

    public static Date tomorrow() {
        return toDate(LocalDateTime.now().plusDays(1));
    }

    public static Date tomorrow(long millisOfDay) {
        return toDate(LocalDateTime.now().plusDays(1).with(ChronoField.MILLI_OF_DAY, millisOfDay));
    }

    /**
     * @param date 接收java.util包下的{@link Date}时间对象
     * @return java.time.LocalDateTime
     * @author zhangduanfeng
     * @date 2019/2/13 9:51
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    /**
     * 取两个日期之间的天数，当开始时间大于结束时间时，返回值带负数。
     *
     * @param startDate 接收java.util包下的{@link Date}时间对象。
     * @param endDate 接收java.util包下的{@link Date}时间对象。
     * @return {@link Long}
     * @author zhangduanfeng
     * @date 2017年12月9日 上午11:03:32
     */
    public static long daysBetween(Date startDate, Date endDate) {
        return startDate.toInstant().until(endDate.toInstant(), ChronoUnit.DAYS);
    }

    /**
     * 取两个日期之间的天数，当开始时间大于结束时间时，返回值带负数。
     *
     * @param start {@link LocalDateTime} Java8日期时间对象。
     * @param end {@link LocalDateTime} Java8日期时间对象。
     * @return {@link Long}
     * @author zhangduanfeng
     * @date 2017年12月9日 上午11:03:32
     */
    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        return start.until(end, ChronoUnit.DAYS);
    }

    /**
     * 取两个日期之间的天数，不会有负数。
     *
     * @param startDate 接收java.util包下的{@link Date}时间对象。
     * @param endDate 接收java.util包下的{@link Date}时间对象。
     * @author zhangduanfeng
     * @date 2017年12月9日 上午11:04:34
     */
    public static long daysBetweenAbs(Date startDate, Date endDate) {
        return Math.abs(daysBetween(startDate, endDate));
    }

    /**
     * 取两个日期之间的天数，不会有负数。
     *
     * @param start {@link LocalDateTime} Java8日期时间对象。
     * @param end {@link LocalDateTime} Java8日期时间对象。
     * @author zhangduanfeng
     * @date 2017年12月9日 上午11:04:34
     */
    public static long daysBetweenAbs(LocalDateTime start, LocalDateTime end) {
        return Math.abs(daysBetween(start, end));
    }

    /**
     * 将传入日期对象的时间部分设置为00:00:00，即设置当天的毫秒值为0L。
     *
     * @param date {@link Date} 旧的日期对象。
     * @return java.util.Date
     * @author zhangduanfeng
     * @date 2019/2/13 9:42
     */
    public static Date startOfDay(Date date) {
        return toDate(toLocalDateTime(date).with(ChronoField.MILLI_OF_DAY,
                ChronoField.MILLI_OF_DAY.range().getMinimum()));
    }

    /**
     * 将传入日期时间对象的时间部分设置为00:00:00，即设置当天的毫秒值为0L。
     *
     * @param localDateTime {@link LocalDateTime} Java8日期时间对象。
     * @return java.time.LocalDateTime
     * @author zhangduanfeng
     * @date 2019/2/13 9:42
     */
    public static LocalDateTime startOfDay(LocalDateTime localDateTime) {
        return localDateTime.with(ChronoField.MILLI_OF_DAY, ChronoField.MILLI_OF_DAY.range().getMinimum());
    }


    /**
     * 将传入日期时间对象的时间部分设置为23:59:59，即设置当天的毫秒值为86399999L。
     *
     * @param localDateTime {@link LocalDateTime} Java8日期时间对象。
     * @return java.time.LocalDateTime
     * @author zhangduanfeng
     * @date 2019/2/13 9:38
     */
    public static LocalDateTime endOfDay(LocalDateTime localDateTime) {
        return localDateTime.with(ChronoField.MILLI_OF_DAY, ChronoField.MILLI_OF_DAY.range().getMaximum());
    }
}
