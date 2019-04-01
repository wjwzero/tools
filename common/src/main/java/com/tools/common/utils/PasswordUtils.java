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
 * 2018/11/27    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.utils;


/**
 * 密码工具类
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/27 17:53
 */
public final class PasswordUtils {
    private PasswordUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 默认密码生成工具，先用MD5编码，然后倒排该MD5串，再然后使用SHA256编码，最终得到密码。
     *
     * @param password 未处理的密码
     * @return java.lang.String
     * @author zhangduanfeng
     * @date 2018/12/12 17:58
     */
    /*public static String defaultEncode(String password) {
        String encodePassword = DigestUtils.md5Hex(password);
        return DigestUtils.sha256Hex(StringUtils.reverse(encodePassword)).toLowerCase();
    }*/

    /**
     * 生成随机密码，仅用于手机APP用户。用于填充
     *
     * @return java.lang.String
     * @author zhangduanfeng
     * @date 2018/12/20 16:07
     */
    /*public static String randomPassword() {
        return defaultEncode(UUID.randomUUID().toString().toLowerCase());
    }*/
}
