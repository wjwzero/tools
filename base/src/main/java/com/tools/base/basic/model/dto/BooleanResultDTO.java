/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
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
 * 2019/2/21    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.basic.model.dto;


import com.tools.base.basic.model.BaseResultDTO;
import com.tools.base.constant.BasicErrorCodeEnum;
import com.tools.base.exception.IErrorCode;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/2/21 13:51
 */
public class BooleanResultDTO extends BaseResultDTO<Boolean> {

    private static final long serialVersionUID = -2042806153235188216L;

    public BooleanResultDTO() {
        super();
    }

    public BooleanResultDTO(Integer code, String message) {
        super(code, message);
    }

    public BooleanResultDTO(IErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 获取一个空的返回结果DTO
     *
     * @return com.jimi.together.base.basic.model.dto.NoDataResultDTO
     * @author zhangduanfeng
     * @date 2018/12/7 14:23
     */
    public static BooleanResultDTO success() {
        return new BooleanResultDTO(BasicErrorCodeEnum.SUCCESS);
    }

    public BooleanResultDTO code(int code) {
        setCode(code);
        return this;
    }

    public BooleanResultDTO message(String message) {
        setMessage(message);
        return this;
    }

    public BooleanResultDTO data(Boolean data) {
        setData(data);
        return this;
    }
}
