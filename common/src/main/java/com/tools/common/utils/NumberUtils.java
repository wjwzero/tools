package com.tools.common.utils;


import java.math.BigDecimal;

public class NumberUtils {
    public static final int ZERO_INT = 0;
    public static final double ZERO_DOUBLE = 0.0D;
    public static final float ZERO_FLOAT = 0.0F;
    public static final long ZERO_LONG = 0L;
    public static final short ZERO_SHORT = 0;
    public static final byte ZERO_BYTE = 0;

    public NumberUtils() {
    }

    public static boolean isNumber(String str) {
        return org.apache.commons.lang3.math.NumberUtils.isCreatable(str);
    }

    public static int toInt(Object o) {
        if (o == null) {
            return 0;
        } else if (o instanceof Boolean) {
            return boolToInt((Boolean)o);
        } else if (o instanceof Integer) {
            return toPrimitive((Integer)o);
        } else if (o instanceof Long) {
            return (int)toPrimitive((Long)o);
        } else if (o instanceof Double) {
            return (int)toPrimitive((Double)o);
        } else if (o instanceof Short) {
            return toPrimitive((Short)o);
        } else if (o instanceof Byte) {
            return toPrimitive((Byte)o);
        } else if (o instanceof Float) {
            return (int)toPrimitive((Float)o);
        } else {
            String str = o.toString();
            if (!isNumber(str)) {
                return 0;
            } else {
                BigDecimal bigDecimal = new BigDecimal(str);
                return bigDecimal.intValue();
            }
        }
    }

    public static long toLong(Object o) {
        if (o == null) {
            return 0L;
        } else if (o.getClass().isPrimitive()) {
            return (Long)o;
        } else if (o.getClass().isAssignableFrom(Long.class)) {
            return (Long)o;
        } else if (o.getClass().isAssignableFrom(Number.class)) {
            return ((Number)o).longValue();
        } else {
            String str = o.toString();
            if (!isNumber(str)) {
                return 0L;
            } else {
                BigDecimal bigDecimal = new BigDecimal(str);
                return (long)bigDecimal.intValue();
            }
        }
    }

    public static int parseInt(String str, int defaultValue) {
        if (!isNumber(str)) {
            return defaultValue;
        } else {
            BigDecimal bigDecimal = new BigDecimal(str);
            return bigDecimal.intValue();
        }
    }

    public static float parseFloat(String str, float defaultValue) {
        if (!isNumber(str)) {
            return defaultValue;
        } else {
            BigDecimal bigDecimal = new BigDecimal(str);
            return bigDecimal.floatValue();
        }
    }

    public static int boolToInt(Boolean b) {
        return toPrimitive(b) ? 1 : 0;
    }

    public static long boolToLong(Boolean b) {
        return toPrimitive(b) ? 1L : 0L;
    }

    public static boolean toPrimitive(Boolean b) {
        return Boolean.TRUE.equals(b);
    }

    public static byte toPrimitive(Byte b) {
        return toPrimitive((Byte)b, (byte)0);
    }

    public static byte toPrimitive(Byte b, byte defaultValue) {
        return b == null ? defaultValue : b;
    }

    public static short toPrimitive(Short s) {
        return toPrimitive((Short)s, (short)0);
    }

    public static short toPrimitive(Short s, short defaultValue) {
        return s == null ? defaultValue : s;
    }

    public static int toPrimitive(Integer i) {
        return toPrimitive((Integer)i, (int)0);
    }

    public static int toPrimitive(Integer i, int defaultValue) {
        return i == null ? defaultValue : i;
    }

    public static float toPrimitive(Float f) {
        return toPrimitive(f, 0.0F);
    }

    public static float toPrimitive(Float f, float defaultValue) {
        return f == null ? defaultValue : f;
    }

    public static long toPrimitive(Long l) {
        return toPrimitive(l, 0L);
    }

    public static long toPrimitive(Long l, long defaultValue) {
        return l == null ? defaultValue : l;
    }

    public static double toPrimitive(Double d) {
        return toPrimitive(d, 0.0D);
    }

    public static double toPrimitive(Double d, double defaultValue) {
        return d == null ? defaultValue : d;
    }
}
