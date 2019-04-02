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
 * 2018/12/27   wjw         Create the class
 * http://www.jimilab.com/
 */


package com.tools.auto.mapper;

import com.tools.auto.domain.entity.TableColumnsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AutoCreateByTableMapper {

    /**
     * 根据表名获得表字段信息
     * @param tableName 查询的用户ID
     * @return java.lang.String
     * @author wjw
     * @date 2019/1/3 10:28
     */
    List<TableColumnsDO> listColumnsByTable(@Param("tableName") String tableName);


}
