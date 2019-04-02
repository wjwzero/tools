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


import com.tools.base.basic.model.BaseDTO;

/**
 * @author wanghe
 * @version 1.0
 * @date 2019-2-22 14:34:59
 */
public final class PaaSResultDTO extends BaseDTO {
    private static final long serialVersionUID = 6832417987161774139L;

    private String code;

    private String message;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
