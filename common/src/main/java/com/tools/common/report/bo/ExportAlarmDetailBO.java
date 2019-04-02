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
import com.tools.common.excel.convert.DateTimeConverter;

import java.time.LocalDateTime;

/**
 * 
 *
 * @author zhengjunwei
 * @version 1.0
 * @date 2019年1月15日 下午3:15:30
 */
public class ExportAlarmDetailBO extends BaseBO {

    /**
     * 
     */
    private static final long serialVersionUID = -79383201133123839L;
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
     * 设备IMEI号
     */
    @Header(name = "IMEI", sort = 2)
    private String imei;
    /**
     * 所属分组
     */
    @Header(name = "所属分组", sort = 3)
    private String groupName;
    /**
     * 告警名称
     */
    @Header(name = "告警类型", sort = 4)
    private String alarmName;
    /**
     * 告警时间 == 推送时间
     */
    @Header(name = "告警时间", sort = 5, covert = DateTimeConverter.class)
    private LocalDateTime pushTime;
    /**
     * 定位时间 == 告警创建时间
     */
    @Header(name = "定位时间", sort = 6, covert = DateTimeConverter.class)
    private LocalDateTime createTime;
    /**
     * 告警地址
     */
    @Header(name = "告警地址", sort = 7)
    private String addr;

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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public LocalDateTime getPushTime() {
        return pushTime;
    }

    public void setPushTime(LocalDateTime pushTime) {
        this.pushTime = pushTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

}
