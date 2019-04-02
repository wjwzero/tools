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
 * 2018/12/7    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.constant;


import com.tools.base.exception.IErrorCode;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/7 15:48
 */
public enum OauthErrorCodeEnum implements IErrorCode {
    /**
     * 无效的刷新令牌
     */
    INVALID_REFRESH_TOKEN(1201, "无效的刷新令牌，请重新登录"),
    /**
     * 无效的访问令牌
     */
    INVALID_ACCESS_TOKEN(1202, "无效的访问令牌，请重新登录"),
    /**
     * 不支持的授权类型
     */
    UNSUPPORTED_GRANT_TYPE(1203, "不支持的授权类型"),
    /**
     * 无效的AppKey或Secret
     */
    INVALID_CLIENT(1204, "无效的AppKey或Secret"),
    /**
     * 无效的短信验证码
     */
    PIN_CODE_NOT_FOUND(1205, "无效的短信验证码"),
    /**
     * 用户未登录
     */
    NOT_LOGGED_IN(1206, "用户未登录");

    private int code;
    private String message;

    OauthErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{\"code\": " + this.code + ", message: \"" + this.message + "\"}";
    }
}
