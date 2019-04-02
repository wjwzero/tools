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

package com.tools.common.report.constant;

/**
 * @author zhengjuwnei
 * @version 1.0
 * @date 2019年1月9日 上午11:19:15
 */
public enum ReportTaskTypeEnum {
    /**
     * 报表导出类型,设备导出
     */
    EXPORT_DEVICE("export_device", "设备导出", "com.jimi.together.device.service.impl.DeviceImportExportServiceImpl"),
    /**
     * 运动统计报表
     */
    RUN_COUNT("run_count", "运动统计报表", "com.jimi.together.web.service.report.ReportServiceImpl"),
    /**
     * 里程详情报表
     */
    MILEAGE_DETAIL("mileage_detail", "里程详情报表", "com.jimi.together.web.service.report.ReportServiceImpl"),
    /**
     * 告警报表_时间
     */
    ALARM_COUNT_TIME("alarm_count_time", "告警报表_时间", "com.jimi.together.web.service.report.ReportServiceImpl"),
    /**
     * 告警报表_设备
     */
    ALARM_COUNT_DEVICE("alarm_count_device", "告警报表_设备", "com.jimi.together.web.service.report.ReportServiceImpl"),
    /**
     * 告警报表_告警类型
     */
    ALARM_COUNT_TYPE("alarm_count_type", "告警报表_告警类型", "com.jimi.together.web.service.report.ReportServiceImpl"),
    /**
     * 告警详情
     */
    ALARM_DETAIL("alarm_detail", "告警详情", "com.jimi.together.web.service.report.ReportServiceImpl"),
    /**
     *
     */
    INSTRUCTION_LOG("instruction_log", "指令日志", "com.jimi.together.instruct.service.impl.InstructionLogServiceImpl"),
    /**
     * 轨迹总览导出
     */
    TRACK_INFO_TOTAL("track_info_total", "轨迹总览", "com.jimi.together.web.service.track.TrackExtendServiceImpl"),
    /**
     * 轨迹明细导出
     */
    TRACK_INFO_DETAIL("track_info_detail", "轨迹明细", "com.jimi.together.web.service.track.TrackExtendServiceImpl");

    /**
     * 导出类型
     */
    private String type;
    /**
     * 导出描述,用于导出文件名称
     */
    private String describe;
    /**
     * 导出调用的服务类名称
     */
    private String className;

    private ReportTaskTypeEnum(String type, String describe, String className) {
        this.type = type;
        this.describe = describe;
        this.className = className;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public static ReportTaskTypeEnum getEnumByType(String type) {
        ReportTaskTypeEnum[] values = ReportTaskTypeEnum.values();
        for (ReportTaskTypeEnum reportTaskTypeEnum : values) {
            if (reportTaskTypeEnum.getType().equals(type)) {
                return reportTaskTypeEnum;
            }
        }
        return null;
    }
}
