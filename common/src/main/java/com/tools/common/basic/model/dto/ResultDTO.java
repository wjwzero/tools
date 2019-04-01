package com.tools.common.basic.model.dto;

import com.tools.common.basic.BasicErrorCodeEnum;
import com.tools.common.basic.IErrorCode;
import com.tools.common.basic.model.BaseResultDTO;
import com.tools.common.basic.model.BaseVO;

public final class ResultDTO<T extends BaseVO> extends BaseResultDTO<T> {
    private static final long serialVersionUID = 1152551832135739520L;

    public ResultDTO() {
    }

    public ResultDTO(Integer code, String message) {
        super(code, message);
    }

    public ResultDTO(IErrorCode errorCode) {
        super(errorCode);
    }

    public static <T extends BaseVO> ResultDTO<T> empty() {
        return new ResultDTO();
    }

    public static <T extends BaseVO> ResultDTO<T> success() {
        return new ResultDTO(BasicErrorCodeEnum.SUCCESS);
    }

    public static <T extends BaseVO> ResultDTO<T> of(IErrorCode errorCode) {
        return new ResultDTO(errorCode);
    }

    public static <T extends BaseVO> ResultDTO<T> of(T data) {
        ResultDTO<T> dto = new ResultDTO((IErrorCode) null);
        dto.setData(data);
        return dto;
    }

    public ResultDTO<T> errorCode(IErrorCode errorCode) {
        if (errorCode == null) {
            return this;
        } else {
            this.setCode(errorCode.getCode());
            this.setMessage(errorCode.getMessage());
            return this;
        }
    }

    public ResultDTO<T> code(int code) {
        this.setCode(code);
        return this;
    }

    public ResultDTO<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResultDTO<T> data(T data) {
        this.setData(data);
        return this;
    }
}
