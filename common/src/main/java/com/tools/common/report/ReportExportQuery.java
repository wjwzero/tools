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
 * 2019年1月9日    Administrator         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.report;


import com.tools.base.basic.model.BaseQuery;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author zhengjuwnei
 * @version 1.0
 * @date 2019年1月9日 上午11:43:08
 */
public class ReportExportQuery extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = -6973248674144515661L;

    /**
     * 导出类型,必须参数，不可以为空，具体类型可配置在ReportTaskTypeEnum枚举类
     */
    @NotBlank
    private String type;

    /**
     * 导出基础查询参数，imei列表
     */
    private Set<String> imeis;
    /**
     * 导出基础查询参数，开始时间
     */
    private LocalDateTime startTime;
    /**
     * 导出基础查询参数，结束时间
     */
    private LocalDateTime endTime;

    /**************** 设备导出基础属性 **********************/
    /**
     * 查询IMEI号，支持模糊搜索
     */
    @Deprecated
    private String imei;

    /**
     * 查询设备名称，支持模糊搜索
     */
    private String deviceName;
    /**
     * 查询机型CODE
     */
    private String mctype;
    /**
     * 查询分组ID
     */
    @Deprecated
    private Long groupId;
    private List<Long> groupIds;
    /**
     * 查询模板ID
     */
    private Long templateId;

    /**
     * 查询IMEI号，模糊搜索
     */
    private String imeiLike;

    /**
     * SIM卡号
     */
    private String sim;

    private String groupCode;

    /**************** 指令日志属性 **********************/
    /**
     * 发送类型（1：在线发送 0：离线发送）
     */
    private Integer isOffLine;
    /**
     * 发送状态（0：执行失败,1：执行成功,3 : 待发送,4:已取消）
     */
    private Integer executeStatus;
    /**
     * 创建开始时间
     */
    private String createBeginTime;
    /**
     * 创建结束时间
     */
    private String createEndTime;
    /**
     * 执行开始时间
     */
    private String executeBeginTime;
    /**
     * 执行开始时间
     */
    private String executeEndTime;

    /**************** 告警统计查询参数 **********************/
    /**
     * 筛选后要查询的告警codes
     */
    private Set<String> alarmCodes;
    /**
     * 告警开始时间
     */
    private LocalDateTime alarmStartTime;
    /**
     * 告警结束时间
     */
    private LocalDateTime alarmEndTime;
    /**
     * 定位开始时间
     */
    private LocalDateTime positionStartDate;
    /**
     * 定位结束时间
     */
    private LocalDateTime positionEndDate;
    /**
     * 处理状态，1=已处理，2=未处理
     */
    private Integer dealStatus;

    /**
     * 是否支持跨月。true-支持，false-不支持
     */
    private Boolean supportCrossMonth;

    /**************** 运动统计-运动总览-导出 **********************/
    private Float oil;

    /**************** 运动统计-设备里程详情-导出 **********************/

    /********* 轨迹回放-总览和明细导出（查询条件一样都是imei,startTime,endTime,posType） ************/
    /**
     * 上报类型,1-gps 2-lbs 3-wifi
     */
    private Integer posType;

    /********* 轨迹回放-总览和明细导出（查询条件一样都是imei,startTime,endTime,posType） ************/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getMctype() {
        return mctype;
    }

    public void setMctype(String mctype) {
        this.mctype = mctype;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIsOffLine() {
        return isOffLine;
    }

    public void setIsOffLine(Integer isOffLine) {
        this.isOffLine = isOffLine;
    }

    public Integer getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(Integer executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(String createBeginTime) {
        this.createBeginTime = createBeginTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getExecuteBeginTime() {
        return executeBeginTime;
    }

    public void setExecuteBeginTime(String executeBeginTime) {
        this.executeBeginTime = executeBeginTime;
    }

    public String getExecuteEndTime() {
        return executeEndTime;
    }

    public void setExecuteEndTime(String executeEndTime) {
        this.executeEndTime = executeEndTime;
    }

    public Set<String> getImeis() {
        return imeis;
    }

    public void setImeis(Set<String> imeis) {
        this.imeis = imeis;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Set<String> getAlarmCodes() {
        return alarmCodes;
    }

    public void setAlarmCodes(Set<String> alarmCodes) {
        this.alarmCodes = alarmCodes;
    }

    public LocalDateTime getAlarmStartTime() {
        return alarmStartTime;
    }

    public void setAlarmStartTime(LocalDateTime alarmStartTime) {
        this.alarmStartTime = alarmStartTime;
    }

    public LocalDateTime getAlarmEndTime() {
        return alarmEndTime;
    }

    public void setAlarmEndTime(LocalDateTime alarmEndTime) {
        this.alarmEndTime = alarmEndTime;
    }

    public LocalDateTime getPositionStartDate() {
        return positionStartDate;
    }

    public void setPositionStartDate(LocalDateTime positionStartDate) {
        this.positionStartDate = positionStartDate;
    }

    public LocalDateTime getPositionEndDate() {
        return positionEndDate;
    }

    public void setPositionEndDate(LocalDateTime positionEndDate) {
        this.positionEndDate = positionEndDate;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Float getOil() {
        return oil;
    }

    public void setOil(Float oil) {
        this.oil = oil;
    }

    public Integer getPosType() {
        return posType;
    }

    public void setPosType(Integer posType) {
        this.posType = posType;
    }

    public String getImeiLike() {
        return imeiLike;
    }

    public void setImeiLike(String imeiLike) {
        this.imeiLike = imeiLike;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Boolean getSupportCrossMonth() {
        return supportCrossMonth;
    }

    public void setSupportCrossMonth(Boolean supportCrossMonth) {
        this.supportCrossMonth = supportCrossMonth;
    }
}
