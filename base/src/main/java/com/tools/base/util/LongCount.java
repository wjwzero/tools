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
 * 2019/1/16    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util;

import java.util.Objects;

/**
 * Long类型计数器
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/1/16 15:45
 */
public final class LongCount {
    private long value;

    public LongCount(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }

    public long getAndIncrement(long delta) {
        long result = value;
        value = result + delta;
        return result;
    }

    public long incrementAndGet(long delta) {
        return value += delta;
    }

    public long getAndDecrement(long delta) {
        long result = value;
        value = result + delta;
        return result;
    }

    public long decrementAndGet(long delta) {
        return value += delta;
    }

    public void set(long newValue) {
        value = newValue;
    }

    public long getAndSet(long newValue) {
        long result = value;
        value = newValue;
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LongCount && ((LongCount) obj).value == value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
