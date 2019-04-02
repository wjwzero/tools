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

import java.io.Serializable;
import java.util.Objects;

/**
 * 用于构造地图中的坐标点
 *
 * @author yaojianping
 * @version 1.0
 * @date 2018/11/16 14:59
 */
public class Point implements Serializable {

    private static final long serialVersionUID = 7990001138583672539L;
    /**
     * 经度
     */
    private double lng;

    /**
     * 纬度
     */
    private double lat;

    Point() {

    }

    Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Double.compare(point.lng, lng) == 0 &&
                Double.compare(point.lat, lat) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lng, lat);
    }
}
