package com.tools.common.basic;

public interface IErrorCode {
    int getCode();

    String getMessage();

    default String jsonMessage() {
        return "{\"code\": " + this.getCode() + ", message: \"" + this.getMessage() + "\"}";
    }
}
