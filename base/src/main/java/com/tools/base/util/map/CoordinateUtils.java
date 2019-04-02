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
 * 2018/11/16    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util.map;

/**
 * 坐标工具类
 *
 * @author yaojianping
 * @version 1.0
 * @date 2018/11/16 15:42
 */
public class CoordinateUtils {

    /**
     * 元周率
     */
    private static final double PI = 3.1415926535897932384626;

    /**
     * 百度坐标转火星坐标使用
     */
    private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 卫星椭球坐标投影到平面地图坐标系的投影因子
     */
    private static final double A = 6378245.0;

    /**
     * 椭球的偏心率
     */
    private static final double EE = 0.00669342162296594323;

    /**
     * wgs-84转BD-09（GPS原始转百度系）
     *
     * @param lat 纬度
     * @param lng 经度
     * @return com.jimi.tuqiang.base.maps.Point
     * @author yaojianping
     * @date 2018/11/16 17:56
     */
    public static Point wgs84ToBd09(double lat, double lng) {
        Point point = wgs84ToGcj20(lat, lng);
        return gcj02ToBd09(point.getLat(), point.getLng());
    }

    /**
     * BD-09转wgs-84（百度系转GPS原始）
     *
     * @param lat 纬度
     * @param lng 经度
     * @return com.jimi.tuqiang.base.maps.Point
     * @author yaojianping
     * @date 2018/11/16 18:00
     */
    public static Point bd09ToWgs84(double lat, double lng) {
        Point point = bd09ToGcj02(lat, lng);
        return gcj02ToWgs84(point.getLat(), point.getLng());
    }

    /**
     * BD-09转换GCJ-02（百度系转国标系坐标）
     *
     * @param lat 纬度
     * @param lng 经度
     * @return com.jimi.tuqiang.base.maps.Point
     * @author yaojianping
     * @date 2018/11/16 16:55
     */
    public static Point bd09ToGcj02(double lat, double lng) {
        Point point = new Point();
        double x = lng - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double ggLng = z * Math.cos(theta);
        double ggLat = z * Math.sin(theta);
        point.setLat(ggLat);
        point.setLng(ggLng);
        return point;
    }

    /**
     * GCJ-02转BD-09（国标系转百度系坐标）
     *
     * @param lat 纬度
     * @param lng 经度
     * @return com.jimi.tuqiang.base.maps.Point
     * @author yaojianping
     * @date 2018/11/16 15:51
     */
    public static Point gcj02ToBd09(double lat, double lng) {
        Point point = new Point();
        double x = lng, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double bdLng = z * Math.cos(theta) + 0.0065;
        double bdLat = z * Math.sin(theta) + 0.006;
        point.setLat(bdLat);
        point.setLng(bdLng);
        return point;
    }

    /**
     * WGS-84转GCJ-02（GPS原始转国标系）
     *
     * @param lat 纬度
     * @param lng 经度
     * @return com.jimi.tuqiang.base.maps.Point
     * @author yaojianping
     * @date 2018/11/16 16:05
     */
    public static Point wgs84ToGcj20(double lat, double lng) {
        Point point = new Point();
        if (isNotChina(lat, lng)) {
            point.setLat(lat);
            point.setLng(lng);
            return point;
        }
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLng = transformLng(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLng = (dLng * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
        double nLat = lat + dLat;
        double nLng = lng + dLng;
        point.setLat(nLat);
        point.setLng(nLng);
        return point;
    }

    /**
     * GCJ-02转WGS-84(国标系转GPS原始)
     *
     * @param lat 纬度
     * @param lng 经度
     * @return com.jimi.tuqiang.base.maps.Point
     * @author yaojianping
     * @date 2018/11/16 17:19
     */
    public static Point gcj02ToWgs84(double lat, double lng) {
        Point gps = wgs84ToGcj20(lat, lng);
        double lontitude = lng * 2 - gps.getLng();
        double latitude = lat * 2 - gps.getLat();
        return new Point(lontitude, latitude);
    }

    /**
     * 是否在中国坐标范围内
     *
     * @param lat 纬度
     * @param lng 经度
     * @return boolean true-不在 false-在
     * @author yaojianping
     * @date 2018/11/16 16:04
     */
    private static boolean isNotChina(double lat, double lng) {
        if (lng < 72.004 || lng > 137.8347) {
            return true;
        }
        if (lat < 0.8293 || lat > 55.8271) {
            return true;
        }
        return false;
    }

    /**
     * 纬度纠偏
     *
     * @param x
     * @param y
     * @return double
     * @author yaojianping
     * @date 2018/11/16 16:25
     */
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 经度纠偏
     *
     * @param x
     * @param y
     * @return double
     * @author yaojianping
     * @date 2018/11/16 16:25
     */
    private static double transformLng(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }
}
