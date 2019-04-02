/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2017.
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
 * 2019年1月17日    zhengjunwei         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.excel.convert;


import com.tools.base.util.date.DateUtils;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

/**
 * @author zhengjunwei
 * @version 1.0
 * @FileName IntTimeConverter.java
 * @Description: 将int时间类型的值转换成天时分秒
 * @Date 2019年1月17日 下午2:59:07
 */
public class IntTimeConverter implements ValueConverter<Integer> {
    private static final long MAX_MILLIS_OF_DAY = ChronoField.SECOND_OF_DAY.range().getMaximum();

    @Override
    public String convert(Integer value) {
        if (value == null) {
            return "0天00小时00分00秒";
        }
        StringBuilder builder = new StringBuilder();
        int days = 0;
        if (value > MAX_MILLIS_OF_DAY) {
            days = (int) (value / MAX_MILLIS_OF_DAY);
        }
        if (value > 0) {
            return builder.append(days).append("天").append(DateUtils.format(LocalTime.ofSecondOfDay(value - (days * MAX_MILLIS_OF_DAY)), "HH小时mm分ss秒")).toString();
        } else {
            return builder.append("0天00小时00分00秒").toString();
        }
    }
}
