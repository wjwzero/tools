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
 * 2018/12/14    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.constant;

/**
 * 正则表达式常量类
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/14 17:36
 */
public final class RegexConsts {

    /**
     * 设备名称校验规则，允许为空，限0-25个字，不支持特殊字符，除 +-_空格 4种
     */
    public static final String PATTERN_DEVICE_NAME = "(^$)|(^[\\u4e00-\\u9fa5a-zA-Z0-9\\\\+\\-_ ]{0,25}$)";
    /**
     * SIM卡号校验规则，允许为空，限11-20个纯数字
     */
    public static final String PATTERN_SIM = "(^$)|^[0-9]{11,20}$";

    /**
     * imei检验规则
     */
    public static final String IMEI_FORMAT = "^\\d{15}$";


    public static final String ONLY_NUMBER = "^[0-9]*$";
    /**
     * 限制仅允许输入中文、英文和数字
     */
    public static final String ONLY_CHINESE_ENGLISH_NUMBER = "^[\\u4e00-\\u9fa5a-zA-Z0-9]+$";
    /**
     * 限制仅允许输入中文、英文、数字、加号、减号、下划线、空格
     */
    public static final String ONLY_CHINESE_ENGLISH_NUMBER_PLUSSIGN_MINUS_UNDERLINE_SPACE =
            "^[\\u4e00-\\u9fa5a-zA-Z0-9\\\\+\\-_ ]+$";


    /**
     * 限制仅允许输入中文、英文、数字、加号、减号
     */
    public static final String ONLY_CHINESE_ENGLISH_NUMBER_PLUSSIGN_MINUS =
            "^[\\u4e00-\\u9fa5a-zA-Z0-9\\\\+\\-]+$";
    /**
     * 限制仅允许输入中文、英文、数字、加号、减号、下划线,长度1-25位
     */
    public static final String ONLY_CHINESE_ENGLISH_NUMBER_PLUSSIGN_MINUS_UNDERLINE_1_25 =
            "[\\u4e00-\\u9fa5a-zA-Z0-9\\+\\-\\_]{1,25}";
    /** 仅匹配1开头的11位电话号码 */
    public static final String MOBILE_PHONE = "(^$)|(^1\\d{10}$)";
//            "/^[1]([3-9])[0-9]{9}$/";
//            "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    public static final String TELEPHONE = "^[\\d+-]+$";


    private RegexConsts() {
        throw new UnsupportedOperationException();
    }

}
