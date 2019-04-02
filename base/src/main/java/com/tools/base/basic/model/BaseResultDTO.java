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
 * 2018/11/26    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.basic.model;


import com.tools.base.constant.BasicErrorCodeEnum;
import com.tools.base.exception.IErrorCode;
import com.tools.base.util.NumberUtils;

/**
 * 基础Web返回结果
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 20:13
 */
@io.swagger.annotations.ApiModel(description = "响应结果")
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@SuppressWarnings("serial")
public abstract class BaseResultDTO<T> extends BaseDTO {
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    /**
     * 错误编码，默认0-无错误
     */
    @io.swagger.annotations.ApiModelProperty(required = true, value = "状态码", notes = "0 = 成功", example = "0")
    protected Integer code;

    /**
     * 消息内容 错误描述
     */
    @io.swagger.annotations.ApiModelProperty(required = true, value = "请求处理消息", notes = "输出成功、失败等等中文消息提示", example = "成功")
    protected String message;

    @io.swagger.annotations.ApiModelProperty(value = "请求处理结果", notes = "输出字符串、对象、集合等不同的结果")
    private T data;

    public BaseResultDTO() {
        this(BasicErrorCodeEnum.SUCCESS);
    }

    public BaseResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResultDTO(IErrorCode errorCode) {
        if (errorCode == null) {
            errorCode = BasicErrorCodeEnum.SUCCESS;
        }
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isSuccess() {
        return NumberUtils.toPrimitive(code) == BasicErrorCodeEnum.SUCCESS.getCode();
    }
}
