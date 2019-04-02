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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * DES 对称加密工具类
 *
 * @author yaojianping
 * @version 1.0
 * @date 2018/10/19 11:35
 */
public class DesUtil {

    /**
     * 默认密钥
     */
    private static final String DEFAULT_CRYPT_KEY = "_@_j_jimi_^jimi";

    /**
     * 加密算法
     */
    private static final String DES = "DES";

    /**
     * PBKDF2 加密
     */
    private static final String PBKDF = "PBKDF2WithHmacSHA1";

    /**
     * 加密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception
     */
    private static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    private static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);
    }

    /**
     * 数据加密，采用默认秘钥加密
     *
     * @param str 需加密字符串
     * @return java.lang.String
     * @throws Exception
     * @author yaojianping
     * @date 2018/10/19 15:04
     */
    public static final String decrypt(String str) throws Exception {
        return new String(decrypt(ByteUtil.hex2ToByte(str), DEFAULT_CRYPT_KEY.getBytes()));
    }

    /**
     * 数据解密，采用默认秘钥解密
     *
     * @param str 需解密的字符串
     * @return java.lang.String
     * @throws Exception
     * @author yaojianping
     * @date 2018/10/19 15:15
     */
    public static final String encrypt(String str) throws Exception {
        return ByteUtil.byteToHex2(encrypt(str.getBytes(), DEFAULT_CRYPT_KEY.getBytes()));
    }

    /**
     * PBKDF2加密，结果不可逆
     *
     * @param str 需加密的字符串
     * @return java.lang.String
     * @throws Exception
     * @author yaojianping
     * @date 2018/10/19 15:30
     */
    public static final String encryptPBKDF2(String str) throws Exception {
        // 迭代次数
        int iterations = 1000;
        // 加密明文
        char[] chars = str.toCharArray();
        String salt = getSaltSHA1();
        // 随机串
        byte[] bytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, bytes, iterations, 16 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF);
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return ByteUtil.byteToHex2(hash);
    }

    /**
     * PBKDF2加密，结果不可逆
     *
     * @param str  需加密的字符串
     * @param salt 盐值
     * @return java.lang.String
     * @throws Exception
     * @author yaojianping
     * @date 2018/10/19 15:30
     */
    public static final String encryptPBKDF2(String str, String salt) throws Exception {
        // 迭代次数
        int iterations = 1000;
        // 加密明文
        char[] chars = str.toCharArray();
        // 随机串
        byte[] bytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, bytes, iterations, 16 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF);
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return ByteUtil.byteToHex2(hash);
    }

    /**
     * 获取随机串
     *
     * @return java.lang.String
     * @author yaojianping
     * @date 2018/10/19 15:31
     */
    public static final String getSaltSHA1() {
        SecureRandom sr;
        byte[] salt = new byte[16];
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt.toString();
    }
}
