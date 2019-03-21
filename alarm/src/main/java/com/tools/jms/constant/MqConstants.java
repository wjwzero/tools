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
 * 2019/1/9    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.jms.constant;

import com.tools.utils.StringUtils;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/9 15:19
 */
public class MqConstants {


    /**
     * 队列
     */
    public final static String QUEUE = "queue";
    /**
     * 主题订阅
     */
    public final static String TOPIC = "topic";
    /**
     * 项目代码
     */
    private final static String PROJECT_CODE = "BDF";
    /**
     * PAAS推送队列
     */
    public final static String MQ_QUEUE_PAAS_PROJECT = QUEUE + StringUtils.PERIOD + PROJECT_CODE;
    /**
     * 终端设备
     */
    public final static String MQ_QUEUE_DISTRIBUTE_DEVICE = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD + "device";
    /**
     * 终端设备 离线告警
     */
    public final static String MQ_QUEUE_DISTRIBUTE_DEVICE_OFFLINE = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD +
            "device" + StringUtils.PERIOD + AlarmType.OFFLINE_ALARM;
    /**
     * 终端设备 停留告警
     */
    public final static String MQ_QUEUE_DISTRIBUTE_DEVICE_STAY_ALERT = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD +
            "device" + StringUtils.PERIOD + AlarmType.STAY_ALERT;
    /**
     * 围栏
     */
    public final static String MQ_QUEUE_DISTRIBUTE_FENCE = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD + "fence";
    /**
     * ACC
     */
    public final static String MQ_QUEUE_DISTRIBUTE_ACC = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD + "acc";
    /**
     * ICCID
     */
    public final static String MQ_QUEUE_DISTRIBUTE_ICCID = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD + "iccid";
    /**
     * 超速告警
     */
    public final static String MQ_QUEUE_DISTRIBUTE_OVER_SPEED = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD +
            "overSpeed";
    /**
     * 离线指令
     */
    public final static String MQ_QUEUE_DISTRIBUTE_OFFLINE_CMD_REPONSE = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD +
            "offlineCmdReponse";
    /**
     * OBD
     */
    public final static String MQ_QUEUE_DISTRIBUTE_OBD = MQ_QUEUE_PAAS_PROJECT + StringUtils.PERIOD + "obd";

    /**
     * 告警推送告警类别用户默认推送设置代码
     */
    public static final String ALARM_PUSH_USER_SETTING_ALL_CODE = "-1";
    /**
     * 告警推送信息MQ订阅topic代码
     */
    public static final String MQ_TOPIC_WEB_SOCKET_ALARM_INFO = "topic.BDF.ActiveMQ.AlarmInfo";
}
