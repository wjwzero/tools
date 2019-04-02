/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
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
 * 2019年1月18日    Administrator         Create the class
 * http://www.jimilab.com/
*/

package com.tools.common.excel.convert;

import com.tools.base.util.date.DatePattern;
import com.tools.base.util.date.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

/**
 * 
 *
 * @author zhengjunwei
 * @version 1.0
 * @date 2019年1月18日 上午10:39:10
 */
public class DateConvert implements ValueConverter<LocalDate> {

    @Override
    public String convert(LocalDate value) {
        if (value == null) {
            return StringUtils.EMPTY;
        }
        return DateUtils.format(value, DatePattern.DATE_YYYY_MM_DD);
    }

}