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


package com.tools.base.constant;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/11/16 11:16
 */
public enum DateFormatEnum {

    /**
     *
     */
    YYYY("yyyy"),
    YYYY_MM("yyyy-MM"),
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    YYYYMMDD("yyyyMMdd"),
    YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
    YYYY_ZH("yyyy年"),
    YYYY_MM_ZH("yyyy年MM月"),
    YYYY_MM_DD_ZH("yyyy年MM月dd日"),
    YYYY_MM_DD_HH_MM_ZH("yyyy年MM月dd日 HH时mm分"),
    YYYY_MM_DD_HH_MM_SS_ZH("yyyy年MM月dd日 HH时mm分ss秒"),
    ;

    private String format;

    DateFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
