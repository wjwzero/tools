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


package com.tools.auto.service.impl;

import com.tools.auto.domain.entity.TableColumnsDO;
import com.tools.auto.mapper.AutoCreateByTableMapper;
import com.tools.auto.service.AutoCreateByTableService;
import com.tools.common.basic.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/3/21 17:30
 */
@Service
public class AutoCreateByTableServiceImpl implements AutoCreateByTableService {

    @Resource
    private AutoCreateByTableMapper autoCreateByTableMapper;

    @Override
    public List<TableColumnsDO> listColumnsByTable(String tableName) {
        return autoCreateByTableMapper.listColumnsByTable(tableName);
    }
}
