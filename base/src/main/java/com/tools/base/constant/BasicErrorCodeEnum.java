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
 * @date 2018/12/7 14:19
 */
public enum BasicErrorCodeEnum implements IErrorCode {
    /**
     * 失败
     */
    FAILED(-1, "失败"),
    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 非法请求
     */
    BAD_REQUEST(400, "非法请求"),
    /**
     * 缺少必要参数
     */
    MISSING_PARAMETER(400, "缺少必要参数"),
    /**
     * 无访问权限
     */
    UNAUTHORIZED(401, "无访问权限"),
    /**
     * 无效请求
     */
    NOT_FOUND(404, "无效请求"),
    /**
     * 方法不支持
     */
    METHOD_NOT_ALLOWED(405, "方法不支持"),
    /**
     * 服务器内部异常
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部异常"),
    /**
     * 参数不合法
     */
    INVALID_PARAMETER(1001, "参数不合法"),

    /**
     * 开始时间不能超过结束时间
     */
    INVALID_BEGIN_END_DATETIME(1002, "开始时间不能超过结束时间"),

    /**
     * 暂不支持跨月查询
     */
    CROSS_MONTH(1003, "暂不支持跨月查询");
    private int code;
    private String message;

    BasicErrorCodeEnum(int code, String message) {
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
}
