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
 * 2019/3/21    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.auto.domain.entity;

import com.tools.common.basic.model.BaseLongKeyDO;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/3/21 17:11
 */
public class TableColumnsDO extends BaseLongKeyDO {

    private String Field;

    private String Type;

    private String Null;

    private String Key;

    private String Comment;

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNull() {
        return Null;
    }

    public void setNull(String aNull) {
        Null = aNull;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
