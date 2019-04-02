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
 * 2018/12/10    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.constant;

/**
 * 数据库常量映射关系，一般为bool2int。必要在Mybatis.xml文件中写死的常量。<br>
 * mybatis的mapper文件中项要使用常量的话${@类的全限定类名@常量名称}
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/10 16:28
 */
@SuppressWarnings("unused")
public class DbRefConsts {
    /**
     * 1=不可见
     */
    public static final int INVISIBLE = 1;

    /**
     * 0=可见
     */
    public static final int VISIBLE = 0;
    /**
     * 0=可删除
     */
    public static final int UNDELETABLE = 1;
    /**
     * 1=不可删除
     */
    public static final int DELETABLE = 0;

    /**
     * 1=已禁用
     */
    public static final int DISABLED = 1;
    /**
     * 0=未禁用
     */
    public static final int ENABLED = 0;

    /**
     * 最大in查询数量
     */
    public static final int MAX_IN_SIZE = 1000;
}
