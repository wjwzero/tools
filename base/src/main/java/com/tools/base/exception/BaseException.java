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
 * 2018/10/18    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.exception;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/10/18 14:59
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = 7339926210889984106L;

    /**
     * 错误码
     */
    protected IErrorCode errorCode;

    protected BaseException() {
    }

    public BaseException(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(IErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseException(IErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(IErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return String.format("错误码：%，错误描述：%", errorCode.getCode(), errorCode.getMessage());
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
