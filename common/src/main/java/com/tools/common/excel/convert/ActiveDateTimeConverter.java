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

import com.tools.base.util.date.DatePattern;
import com.tools.base.util.date.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author zhengjunwei
 * @version 1.0
 * @FileName ActiveDateTimeConverter.java
 * @Description:
 * @Date 2019年1月17日 下午2:59:07
 */
public class ActiveDateTimeConverter implements ValueConverter<LocalDateTime> {

    @Override
    public String convert(LocalDateTime value) {
        if (value == null) {
            return StringUtils.EMPTY;
        }
        if (!DateUtils.toLocalDateTime(0L).isBefore(value)) {
            return "未激活";
        }
        return DateUtils.format(value, DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS);
    }

}
