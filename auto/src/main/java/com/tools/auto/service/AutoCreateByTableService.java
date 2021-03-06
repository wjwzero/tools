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
 * 2019/3/21    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.auto.service;

import com.tools.auto.domain.entity.TableColumnsDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/3/21 17:28
 */
public interface AutoCreateByTableService  {

    List<TableColumnsDO> listColumnsByTable(@Param("tableName") String tableName);
}
