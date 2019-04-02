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
 * 2018/12/26    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.basic.model.dto;


import com.tools.base.basic.model.BaseResultDTO;
import com.tools.base.constant.BasicErrorCodeEnum;
import com.tools.base.exception.IErrorCode;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/26 13:49
 */
public final class StringResultDTO extends BaseResultDTO<String> {
    private static final long serialVersionUID = 6832417987161774139L;

    public static StringResultDTO returnData(String data) {
        StringResultDTO stringResultDTO = success();
        stringResultDTO.setData(data);
        return stringResultDTO;
    }

    public static StringResultDTO returnError(IErrorCode errorCode) {
        StringResultDTO stringResultDTO = new StringResultDTO();
        stringResultDTO.setCode(errorCode.getCode());
        stringResultDTO.setMessage(errorCode.getMessage());
        return stringResultDTO;
    }

    public static StringResultDTO success() {
        StringResultDTO stringResultDTO = new StringResultDTO();
        stringResultDTO.setCode(BasicErrorCodeEnum.SUCCESS.getCode());
        stringResultDTO.setMessage(BasicErrorCodeEnum.SUCCESS.getMessage());
        return stringResultDTO;
    }

    public StringResultDTO errorCode(IErrorCode errorCode) {
        if (errorCode == null) {
            return this;
        }
        setCode(errorCode.getCode());
        setMessage(errorCode.getMessage());
        return this;
    }

    public StringResultDTO code(int code) {
        setCode(code);
        return this;
    }

    public StringResultDTO message(String message) {
        setMessage(message);
        return this;
    }

    public StringResultDTO data(String data) {
        setData(data);
        return this;
    }
}
