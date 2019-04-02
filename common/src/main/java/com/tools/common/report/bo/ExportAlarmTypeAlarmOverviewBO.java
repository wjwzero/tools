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
 * 2019年1月15日    Administrator         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.report.bo;

import com.tools.base.basic.model.BaseBO;
import com.tools.common.excel.anno.Header;

/**
 * @author zhengjunwei
 * @version 1.0
 * @date 2019年1月15日 下午3:17:37
 */
public class ExportAlarmTypeAlarmOverviewBO extends BaseBO {

    /**
     *
     */
    private static final long serialVersionUID = 6436479294145213656L;
    /**
     * 序号
     */
    @Header(name = "序号", sort = 0)
    private String seqNum;

    /**
     * 告警名称
     */
    @Header(name = "告警名称", sort = 1)
    private String name;
    /**
     * 告警次数
     */
    @Header(name = "告警次数", sort = 2)
    private Integer times;

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
