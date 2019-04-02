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
 * 2018年12月12日    zhengjunwei         Create the class
 * http://www.jimilab.com/
 */

package com.tools.base.constant;

/**
 * @author zhengjunwei
 * @version 1.0
 * @date 2018年12月12日 上午9:48:32
 */
public enum ExcelJavaTypeEnum {
    /**
     *
     */
    LANG_STRING("java.lang.String"),
    LANG_LOWER_STRING("java.lang.string"),
    STRING("String"),
    LOWER_STRING("string"),
    LANG_INTEGER("java.lang.Integer"),
    LANG_INT("java.lang.int"),
    INT("int"),
    INTEGER("Integer"),
    LANG_FLOAT("java.lang.Float"),
    LANG_LOWER_FLOAT("java.lang.float"),
    FLOAT("Float"),
    LOWER_FLOAT("float"),
    LANG_DOUBLE("java.lang.Double"),
    LANG_LOWER_DOUBLE("java.lang.double"),
    DOUBLE("Double"),
    LOWER_DOUBLE("double"),
    MATH_BIGDECIMAL("java.math.BigDecimal"),
    BIGDECIMAL("BigDecimal"),
    UTIL_DATE("java.util.Date"),
    DATE("Date"),
    TIMESTAMP("java.sql.Timestamp"),
    LANG_BOOLEAN("java.lang.Boolean"),
    BOOLEAN("Boolean"),
    LANG_LONG("java.lang.Long"),
    LANG_LOWER_LONG("java.lang.long"),
    LONG("Long"),
    LOWER_LONG("long");


    private String type;

    ExcelJavaTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
