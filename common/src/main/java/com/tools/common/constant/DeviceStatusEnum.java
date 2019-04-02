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
 * 2019/1/14    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.constant;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/1/14 15:59
 */
public enum DeviceStatusEnum {

    /**
     * 设备在线运动
     */
    MOTION(1),
    /**
     * 设备在线静止
     */
    MOTIONLESS(2),
    /**
     * 设备离线
     */
    OFFLINE(3),
    /**
     * 未激活
     */
    UNACTIVATED(4);

    private int state;

    DeviceStatusEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
