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
 * 2018/12/28    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.basic.model.dto;


import com.tools.base.basic.model.BaseResultDTO;
import com.tools.base.constant.BasicErrorCodeEnum;
import com.tools.base.exception.IErrorCode;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/28 9:53
 */
public final class NoDataResultDTO extends BaseResultDTO<Void> {

    private static final long serialVersionUID = 7970235069209858598L;


    public NoDataResultDTO() {
        super();
    }

    public NoDataResultDTO(Integer code, String message) {
        super(code, message);
    }

    public NoDataResultDTO(IErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 获取一个空的返回结果DTO
     *
     * @return com.jimi.together.base.basic.model.dto.NoDataResultDTO
     * @author zhangduanfeng
     * @date 2018/12/7 14:23
     */
    public static NoDataResultDTO success() {
        return new NoDataResultDTO(BasicErrorCodeEnum.SUCCESS);
    }

    /**
     * 获取一个返回结果DTO
     *
     * @param errorCode {@link IErrorCode}
     * @return com.jimi.together.base.basic.model.dto.NoDataResultDTO
     * @author zhangduanfeng
     * @date 2018/12/7 14:23
     */
    public static NoDataResultDTO of(IErrorCode errorCode) {
        return new NoDataResultDTO(errorCode);
    }


    public NoDataResultDTO errorCode(IErrorCode errorCode) {
        if (errorCode == null) {
            return this;
        }
        setCode(errorCode.getCode());
        setMessage(errorCode.getMessage());
        return this;
    }

    public NoDataResultDTO code(int code) {
        setCode(code);
        return this;
    }

    public NoDataResultDTO message(String message) {
        setMessage(message);
        return this;
    }


    @Override
    public Void getData() {
        return null;
    }

    @Override
    public void setData(Void data) {
        throw new UnsupportedOperationException();
    }
}
