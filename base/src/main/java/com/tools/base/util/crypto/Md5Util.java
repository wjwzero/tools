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
 * 2018/10/19    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util.crypto;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/10/19 15:35
 */
public class Md5Util {

    /**
     * MD5加密，返回32位MD5值
     *
     * @param str 需加密的字符串
     * @return java.lang.String
     * @author yaojianping
     * @date 2018/10/19 15:48
     */
    public static final String md5Hex(String str) {
        return DigestUtils.md5Hex(str.getBytes());
    }
}
