/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2017.
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
 * 2017年12月21日    zhangduanfeng         Create the class
 * http://www.jimilab.com/
*/

package com.tools.common.excel.convert;

/**
 * @FileName ValueConverter.java
 * @Description: Excel属性转换器类
 *
 * @Date 2017年12月21日 上午9:13:07
 * @author zhangduanfeng
 * @version 1.0
 */
public interface ValueConverter<T> {
    /**
     * Excel属性转换
     * 
     * @Title: convert
     * @Description: Excel属性转换
     * @param value
     * @return
     * @author zhangduanfeng
     * @date 2017年12月22日 下午2:12:17
     */
    String convert(T value);

    public static abstract class NONE implements ValueConverter<Void> {

    }
}
