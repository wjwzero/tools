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

import com.tools.base.basic.model.BaseVO;
import com.tools.common.excel.anno.Header;
import com.tools.common.excel.convert.DateTimeConverter;
import com.tools.common.excel.convert.IntTimeConverter;

import java.time.LocalDateTime;

/**
 * @author zhengjunwei
 * @version 1.0
 * @date 2019/1/10 15:23
 */
public class ExportDeviceMileageBO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = -4789419564162376170L;
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
     * 开始时间
     */
    @Header(name = "开始时间", sort = 3, covert = DateTimeConverter.class)
    private LocalDateTime startTime;
    /**
     * 开始地址
     */
    @Header(name = "开始地址", sort = 4)
    private String startAddr;
    /**
     * 结束时间
     */
    @Header(name = "结束时间", sort = 5, covert = DateTimeConverter.class)
    private LocalDateTime endTime;
    /**
     * 结束地址
     */
    @Header(name = "结束地址", sort = 6)
    private String endAddr;

    /**
     * 用时(秒) json序列化成用的类型
     */
    @Header(name = "持续时长", sort = 7,covert = IntTimeConverter.class)
    private Integer runTimeSecond;

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStartAddr() {
        return startAddr;
    }

    public void setStartAddr(String startAddr) {
        this.startAddr = startAddr;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getEndAddr() {
        return endAddr;
    }

    public void setEndAddr(String endAddr) {
        this.endAddr = endAddr;
    }

    public Integer getRunTimeSecond() {
        return runTimeSecond;
    }

    public void setRunTimeSecond(Integer runTimeSecond) {
        this.runTimeSecond = runTimeSecond;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

}
