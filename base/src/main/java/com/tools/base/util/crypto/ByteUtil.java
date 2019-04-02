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

/**
 * 字节操作工具类
 *
 * @author yaojianping
 * @version 1.0
 * @date 2018/10/19 11:49
 */
public class ByteUtil {

    /**
     * 二行制转字符串
     *
     * @param bytes 二进制字节数组
     * @return java.lang.String
     * @author yaojianping
     * @date 2018/10/19 13:55
     */
    public static String byteToHex2(byte[] bytes) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 字符串转二进制
     *
     * @param str 字符串数据
     * @return byte[]
     * @author yaojianping
     * @date 2018/10/19 14:52
     */
    public static byte[] hex2ToByte(String str) {
        byte[] bytes = str.getBytes();
        if ((bytes.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[bytes.length / 2];
        for (int n = 0; n < bytes.length; n += 2) {
            String item = new String(bytes, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 把16进制的字符串转换成字节数组
     *
     * @param digits 字符串数据
     * @return byte[]
     * @author yaojianping
     * @date 2018/10/19 14:56
     */
    public static byte[] hex16ToByte(String digits) {
        int length = digits.trim().length();
        if (length % 2 != 0) {
            digits = digits + "f";
            length++;
        }
        byte[] buffer = new byte[length / 2];
        for (int i = 0; i < length - 1; ) {
            String temp = digits.substring(i, i + 1);
            String temp2 = digits.substring(i + 1, i + 2);
            buffer[(i / 2)] = ((byte) (0xF0 & Integer.parseInt(temp, 16) << 4 | 0xF & Integer.parseInt(temp2, 16)));
            i += 2;
        }
        return buffer;
    }

    /**
     * 把16进制字节数组转换成字符串
     *
     * @return java.lang.String
     * @author yaojianping
     * @date 2018/10/19 14:57
     */
    public static String byteToHex16(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            buffer.append(temp);
        }
        return buffer.toString();
    }
}
