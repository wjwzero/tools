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
 * 2019/1/5    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.constant;


import com.tools.base.util.StringUtils;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/1/5 13:10
 */
public class CommonConsts {
    /**
     * 分组默认最顶级ID，{@value}
     */
    public static final long GROUP_DEFAULT_TOP_ID = 1L;

    /**
     * 分组默认最顶级CODE，{@value}
     */
    public static final String GROUP_DEFAULT_TOP_CODE = CommonConsts.GROUP_DEFAULT_TOP_ID + StringUtils.COMMA;
}
