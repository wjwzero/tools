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
 * 2019/1/14    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util;

/**
 * 整数型计数器
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/1/14 20:24
 */
public final class Count {
    private int value;

    public Count(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public int getAndAdd(int delta) {
        int result = value;
        value = result + delta;
        return result;
    }

    public int addAndGet(int delta) {
        return value += delta;
    }

    public void set(int newValue) {
        value = newValue;
    }

    public int getAndSet(int newValue) {
        int result = value;
        value = newValue;
        return result;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Count && ((Count) obj).value == value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
