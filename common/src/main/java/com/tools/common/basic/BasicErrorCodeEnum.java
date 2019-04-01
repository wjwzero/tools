package com.tools.common.basic;

public enum BasicErrorCodeEnum implements IErrorCode {
    FAILED(-1, "失败"),
    SUCCESS(0, "成功"),
    BAD_REQUEST(400, "非法请求"),
    MISSING_PARAMETER(400, "缺少必要参数"),
    UNAUTHORIZED(401, "无访问权限"),
    NOT_FOUND(404, "无效请求"),
    METHOD_NOT_ALLOWED(405, "方法不支持"),
    INTERNAL_SERVER_ERROR(500, "服务器内部异常"),
    INVALID_PARAMETER(1001, "参数不合法"),
    INVALID_BEGIN_END_DATETIME(1002, "开始时间不能超过结束时间"),
    CROSS_MONTH(1003, "暂不支持跨月查询");

    private int code;
    private String message;

    private BasicErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
