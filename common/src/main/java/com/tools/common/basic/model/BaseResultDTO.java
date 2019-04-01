package com.tools.common.basic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tools.common.basic.BasicErrorCodeEnum;
import com.tools.common.basic.IErrorCode;
import com.tools.common.utils.NumberUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;

public class BaseResultDTO<T extends BaseVO> {
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    @ApiModelProperty(
            required = true,
            value = "状态码",
            notes = "0 = 成功",
            example = "0"
    )
    protected Integer code;
    @ApiModelProperty(
            required = true,
            value = "请求处理消息",
            notes = "输出成功、失败等等中文消息提示",
            example = "成功"
    )
    protected String message;
    @ApiModelProperty(
            value = "请求处理结果",
            notes = "输出字符串、对象、集合等不同的结果"
    )
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

        this.code = ((IErrorCode)errorCode).getCode();
        this.message = ((IErrorCode)errorCode).getMessage();
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return NumberUtils.toPrimitive(this.code) == BasicErrorCodeEnum.SUCCESS.getCode();
    }
}
