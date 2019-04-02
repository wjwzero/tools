package com.tools.common.excel.service.impl;


import com.tools.common.excel.handle.ExcelHandleChain;
import com.tools.common.excel.handle.ExcelParamFactory;
import com.tools.common.excel.model.ExcelParam;
import com.tools.common.excel.model.ExcelResult;
import com.tools.common.excel.model.ExportModel;
import com.tools.common.excel.service.ExcelExportService;
import org.springframework.stereotype.Service;

/**
 * @author zhengjunwei
 * @date 2018/3/13 10:35
 **/
@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    /**
     * excel导出功能
     *
     * @param cls   实现bean，要求实现 com.hwy.model.BaseExcelData
     * @param datas 数据
     * @return
     */
    @Override
    public ExcelResult export(ExportModel exportModel) {
        // 封装参数
        ExcelParam param = new ExcelParamFactory().create(exportModel);
        // 封装返回
        ExcelResult result = new ExcelResult();
        // 责任链处理，返回结果
        ExcelHandleChain chain = new ExcelHandleChain(param, result);
        return chain.result();
    }
}
