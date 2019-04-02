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


package com.tools.base.util;

import java.math.BigDecimal;

/**
 * 数值型工具类
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/11 10:25
 */
public class NumberUtils {
    /**
     * 默认值：{@value}
     */
    public static final int ZERO_INT = 0;
    /**
     * 默认值：{@value}
     */
    public static final double ZERO_DOUBLE = 0D;
    /**
     * 默认值：{@value}
     */
    public static final float ZERO_FLOAT = 0F;
    /**
     * 默认值：{@value}
     */
    public static final long ZERO_LONG = 0L;
    /**
     * 默认值：{@value}
     */
    public static final short ZERO_SHORT = (short) ZERO_INT;
    /**
     * 默认值：{@value}
     */
    public static final byte ZERO_BYTE = (byte) ZERO_INT;

    /**
     * 判断字符串是否是数值类型
     *
     * @param str 字符串
     * @return java.lang.Boolean
     * {@link org.apache.commons.lang3.math.NumberUtils#isCreatable(String)}
     * @author apache
     * @date 2017年12月20日 上午11:35:50
     */
    public static boolean isNumber(final String str) {
        return org.apache.commons.lang3.math.NumberUtils.isCreatable(str);
    }

    /**
     * Boolean、Integer、Long、Double、Short、Byte、Float转int基本数据类型，若数值超出int范围，会返回-1
     *
     * @return int
     * @author zhangduanfeng
     * @date 2018/12/11 10:28
     */
    public static int toInt(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Boolean) {
            return boolToInt((Boolean) o);
        } else if (o instanceof Integer) {
            return toPrimitive((Integer) o);
        } else if (o instanceof Long) {
            return (int) toPrimitive((Long) o);
        } else if (o instanceof Double) {
            return (int) toPrimitive((Double) o);
        } else if (o instanceof Short) {
            return toPrimitive((Short) o);
        } else if (o instanceof Byte) {
            return toPrimitive((Byte) o);
        } else if (o instanceof Float) {
            return (int) toPrimitive((Float) o);
        }

        String str = o.toString();
        if (!isNumber(str)) {
            return 0;
        }
        BigDecimal bigDecimal = new BigDecimal(str);
        return bigDecimal.intValue();
    }

    /**
     * 对象转长整型数值，默认返回0
     *
     * @param o 对象
     * @return long
     * @author zhangduanfeng
     * @date 2019/2/18 14:25
     */
    public static long toLong(Object o) {
        if (o == null) {
            return 0L;
        }
        if (o.getClass().isPrimitive()) {
            return (long) o;
        }
        if (o.getClass().isAssignableFrom(Long.class)) {
            return (Long) o;
        }

        if (o.getClass().isAssignableFrom(Number.class)) {
            return ((Number) o).longValue();
        }

        String str = o.toString();
        if (!isNumber(str)) {
            return 0;
        }
        BigDecimal bigDecimal = new BigDecimal(str);
        return bigDecimal.intValue();
    }

    /**
     * String转int，当字符串非数字或无法转换时，返回默认值
     *
     * @return int
     * @author zhangduanfeng
     * @date 2018/12/11 10:30
     */
    public static int parseInt(String str, int defaultValue) {
        if (!isNumber(str)) {
            return defaultValue;
        }
        BigDecimal bigDecimal = new BigDecimal(str);
        return bigDecimal.intValue();
    }

    /**
     * String转float，当字符串非数字或无法转换时，返回默认值
     *
     * @param str          字符串
     * @param defaultValue 默认数值
     * @return float
     * @author zhangduanfeng
     * @date 2018/12/11 10:30
     */
    public static float parseFloat(String str, float defaultValue) {
        if (!isNumber(str)) {
            return defaultValue;
        }
        BigDecimal bigDecimal = new BigDecimal(str);
        return bigDecimal.floatValue();
    }



    /**
     * boolean转数字，当boolean为true时返回1，false返回0
     *
     * @param b 布尔值
     * @return int
     * @author zhangduanfeng
     * @date 2018/12/11 10:31
     */
    public static int boolToInt(Boolean b) {
        return toPrimitive(b) ? 1 : 0;
    }

    /**
     * 布尔值转长整型
     *
     * @param b 布尔值
     * @return long 长整型
     * @author zhangduanfeng
     * @date 2019/2/18 14:28
     */
    public static long boolToLong(Boolean b) {
        return toPrimitive(b) ? 1L : 0L;
    }


    /**
     * 装箱类型转基本类型
     *
     * @param b 布尔值
     * @return boolean
     * @author zhangduanfeng
     * @date 2018/12/11 10:31
     */
    public static boolean toPrimitive(Boolean b) {
        return Boolean.TRUE.equals(b);
    }

    /**
     * 装箱类型转基本类型
     *
     * @param b 布尔值
     * @return byte
     * @author zhangduanfeng
     * @date 2018/12/11 10:31
     */
    public static byte toPrimitive(Byte b) {
        return toPrimitive(b, ZERO_BYTE);
    }

    /**
     * 装箱类型转基本类型
     *
     * @param b            布尔值
     * @param defaultValue 默认数值
     * @return byte
     * @author zhangduanfeng
     * @date 2018/12/11 10:31
     */
    public static byte toPrimitive(Byte b, byte defaultValue) {
        if (b == null) {
            return defaultValue;
        }
        return b;
    }

    /**
     * 装箱类型转基本类型
     *
     * @param s 短整数
     * @return short
     * @author zhangduanfeng
     * @date 2018/12/11 10:31
     */
    public static short toPrimitive(Short s) {
        return toPrimitive(s, ZERO_SHORT);
    }

    /**
     * 装箱类型转基本类型
     *
     * @param s            短整数
     * @param defaultValue 默认数值
     * @return short
     * @author zhangduanfeng
     * @date 2018/12/11 10:31
     */
    public static short toPrimitive(Short s, short defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        return s;
    }

    /**
     * 装箱类型转基本类型
     *
     * @param i java.lang.Integer
     * @return int
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static int toPrimitive(Integer i) {
        return toPrimitive(i, ZERO_INT);
    }

    /**
     * 装箱类型转基本类型
     *
     * @param i            java.lang.Integer
     * @param defaultValue 默认数值
     * @return int
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static int toPrimitive(Integer i, int defaultValue) {
        if (i == null) {
            return defaultValue;
        }
        return i;
    }

    /**
     * 装箱类型转基本类型
     *
     * @param f java.lang.Float
     * @return float
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static float toPrimitive(Float f) {
        return toPrimitive(f, ZERO_FLOAT);
    }

    /**
     * 装箱类型转基本类型
     *
     * @param f            java.lang.Float
     * @param defaultValue 默认数值
     * @return float
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static float toPrimitive(Float f, float defaultValue) {
        if (f == null) {
            return defaultValue;
        }
        return f;
    }

    /**
     * 装箱类型转基本类型
     *
     * @param l java.lang.Long
     * @return long
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static long toPrimitive(Long l) {
        return toPrimitive(l, ZERO_LONG);
    }

    /**
     * 装箱类型转基本类型
     *
     * @param l            java.lang.Long
     * @param defaultValue 默认数值
     * @return long
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static long toPrimitive(Long l, long defaultValue) {
        if (l == null) {
            return defaultValue;
        }
        return l;
    }

    /**
     * 装箱类型转基本类型
     *
     * @param d java.lang.Double
     * @return double
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static double toPrimitive(Double d) {
        return toPrimitive(d, ZERO_DOUBLE);
    }

    /**
     * 装箱类型转基本类型
     *
     * @param d            java.lang.Double
     * @param defaultValue 默认数值
     * @return double
     * @author zhangduanfeng
     * @date 2018/12/11 10:32
     */
    public static double toPrimitive(Double d, double defaultValue) {
        if (d == null) {
            return defaultValue;
        }
        return d;
    }
}
