package com.tools.common.excel.handle.impl;


import com.tools.common.excel.handle.ExcelHandle;
import com.tools.common.excel.model.ExcelParam;
import com.tools.common.excel.model.ExcelResult;

/**
 * 数据校验处理类
 *
 * @author zhengjunwei
 * @date 2018/3/13 11:38
 **/
public class DataValidatorHandle implements ExcelHandle {

    private ExcelParam param;

    private ExcelResult result;

    public DataValidatorHandle(ExcelParam param, ExcelResult result) {
        this.param = param;
        this.result = result;
    }

    @Override
    public void handle() {
        //todo 1.校验参数正确性
        //todo 2.校验实体是否可导出，只有继承com.hwy.model.BaseExcelData的才允许导出
    }
}
