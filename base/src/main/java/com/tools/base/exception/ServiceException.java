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


package com.tools.base.exception;

import com.tools.base.constant.BasicErrorCodeEnum;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 16:49
 */
public class ServiceException extends RuntimeException {
    private IErrorCode errorCode;

    public ServiceException() {
        errorCode = BasicErrorCodeEnum.FAILED;
    }

    public ServiceException(IErrorCode errorCode) {
        super(errorCode.jsonMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(IErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(IErrorCode errorCode, String message, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
