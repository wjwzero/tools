package com.tools.base.exception;

public interface IErrorCode {
    int getCode();

    String getMessage();

    default String jsonMessage() {
        return "{\"code\": " + this.getCode() + ", message: \"" + this.getMessage() + "\"}";
    }
}
