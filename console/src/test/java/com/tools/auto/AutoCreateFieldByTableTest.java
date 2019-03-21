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


package com.tools.auto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.BaseTest;
import com.tools.auto.domain.bo.DataBaseFieldsBO;
import com.tools.auto.domain.entity.TableColumnsDO;
import com.tools.auto.mapper.AutoCreateByTableMapper;
import com.tools.auto.service.AutoCreateByTableService;
import javafx.print.Collation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/3/21 17:35
 */
public class AutoCreateFieldByTableTest extends BaseTest {

    @Resource
    private AutoCreateByTableService autoCreateByTableServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createJsonTest() throws JsonProcessingException {

        List<TableColumnsDO> columnsList = autoCreateByTableServiceImpl.listColumnsByTable("t_user_account_info");

        List<DataBaseFieldsBO> dataBaseFieldsBOList =  columnsList.stream().map(columns -> {
            DataBaseFieldsBO dbf = new DataBaseFieldsBO(columns.getField(),columns.getType(),columns.getKey(),columns.getNull(),columns.getComment());
            return dbf;
        }).collect(Collectors.toList());
        logger.info(objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writerWithDefaultPrettyPrinter().writeValueAsString(dataBaseFieldsBOList));
        logger.info(JSONObject.toJSONString(dataBaseFieldsBOList));
    }
}
