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
import com.tools.base.basic.model.BaseVO;
import com.tools.base.exception.IErrorCode;

import java.util.Collection;
import java.util.List;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/26 14:08
 */
public final class ListResultDTO<T extends BaseVO> extends BaseResultDTO<Collection<T>> {
    private static final long serialVersionUID = -4741925144694510642L;

    public ListResultDTO() {
        super();
    }

    public ListResultDTO(Integer code, String message) {
        super(code, message);
    }

    public ListResultDTO(IErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 获取一个空的返回结果DTO
     *
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:23
     */
    public static <T extends BaseVO> ListResultDTO<T> empty() {
        return new ListResultDTO<>();
    }

    /**
     * 获取一个返回结果DTO
     *
     * @param errorCode {@link IErrorCode}
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:23
     */
    public static <T extends BaseVO> ListResultDTO<T> of(IErrorCode errorCode) {
        return new ListResultDTO<>(errorCode);
    }

    /**
     * 获取一个返回结果DTO
     *
     * @param data 继承自{@link BaseVO}的数据集合
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:24
     */
    public static <T extends BaseVO> ListResultDTO<T> of(List<T> data) {
        ListResultDTO<T> dto = new ListResultDTO<>();
        dto.setData(data);
        return dto;
    }

    public ListResultDTO<T> errorCode(IErrorCode errorCode) {
        if (errorCode == null) {
            return this;
        }
        setCode(errorCode.getCode());
        setMessage(errorCode.getMessage());
        return this;
    }


    public ListResultDTO<T> code(int code) {
        setCode(code);
        return this;
    }


    public ListResultDTO<T> message(String message) {
        setMessage(message);
        return this;
    }


    public ListResultDTO<T> data(Collection<T> data) {
        setData(data);
        return this;
    }
}
