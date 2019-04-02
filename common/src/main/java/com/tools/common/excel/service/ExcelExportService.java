package com.tools.common.excel.service;


import com.tools.common.excel.model.ExcelResult;
import com.tools.common.excel.model.ExportModel;

/**
 * excel导出服务
 *
 * @author zhengjunwei
 * @date 2018/3/13 10:35
 **/
public interface ExcelExportService {

    /**
     * excel导出功能
     *
     * @param exportModel
     * @return
     * @author zhengjunwei
     * @date 2018年12月29日 下午2:17:46
     */
    ExcelResult export(ExportModel exportModel);

}
