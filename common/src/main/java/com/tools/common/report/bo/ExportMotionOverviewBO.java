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
 * 2019/1/10    zhengjunwei         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.report.bo;

import com.tools.base.basic.model.BaseBO;
import com.tools.common.excel.anno.Header;
import com.tools.common.excel.convert.DateConvert;

import java.time.LocalDate;

/**
 * @author zhengjunwei
 * @version 1.0
 * @date 2019/1/10 14:20
 */
public class ExportMotionOverviewBO extends BaseBO {

    /**
     *
     */
    private static final long serialVersionUID = 3976184576983494765L;
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
     * 分组名称
     */
    @Header(name = "所属分组", sort = 3)
    private String groupName;

    @Header(name = "开始时间", sort = 4, covert = DateConvert.class)
    private LocalDate startDay;

    @Header(name = "结束时间", sort = 5, covert = DateConvert.class)
    private LocalDate endDay;
    /**
     * 总里程，单位：米
     */
    @Header(name = "里程", sort = 6)
    private Double mileage;
    /**
     * 停留次数
     */
    @Header(name = "停留次数", sort = 7)
    private Integer stopTimes;
    /**
     * 油耗
     */
    @Header(name = "油耗", sort = 8)
    private Float oil;
    /**
     * 最后状态信息，离线包含天数
     */
    @Header(name = "最后状态", sort = 9)
    private String lastStateInfo;

    /**
     * 最后定位地址
     */
    @Header(name = "最后位置", sort = 10)
    private String lastPositionAddr;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Float getOil() {
        return oil;
    }

    public void setOil(Float oil) {
        this.oil = oil;
    }

    public String getLastStateInfo() {
        return lastStateInfo;
    }

    public void setLastStateInfo(String lastStateInfo) {
        this.lastStateInfo = lastStateInfo;
    }

    public String getLastPositionAddr() {
        return lastPositionAddr;
    }

    public void setLastPositionAddr(String lastPositionAddr) {
        this.lastPositionAddr = lastPositionAddr;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public Integer getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(Integer stopTimes) {
        this.stopTimes = stopTimes;
    }
}
