/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2018.
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
 * 2018/12/16    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.constant;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/16 22:35
 */
public enum UserTypeEnum {
    /**
     * Web平台用户
     */

    PLATFORM(1),
    /**
     * APP用户
     */
    APP(2);
    private int type;

    UserTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
