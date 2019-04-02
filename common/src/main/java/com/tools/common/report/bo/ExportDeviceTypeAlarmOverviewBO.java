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
 * 
 *
 * @author zhengjunwei
 * @version 1.0
 * @date 2019年1月15日 下午3:16:44
 */
public class ExportDeviceTypeAlarmOverviewBO extends BaseBO {

    /**
     * 
     */
    private static final long serialVersionUID = -5901168731260538592L;
    /**
     * 序号
     */
    @Header(name = "序号", sort = 0)
    private String seqNum;

    /**
     * 设备名称
     */
    @Header(name = "设备名称", sort = 1)
    private String deviceName;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

}
