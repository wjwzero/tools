package com.tools.common.excel.handle;


import com.tools.base.util.CollectionUtils;
import com.tools.common.excel.anno.Header;
import com.tools.common.excel.constant.ReportTaskTypeConstant;
import com.tools.common.excel.model.ExcelOriginHeader;
import com.tools.common.excel.model.ExcelParam;
import com.tools.common.excel.model.ExportModel;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 参数工厂
 *
 * @author zhengjunwei
 * @date 2018/3/13 15:01
 **/
public class ExcelParamFactory {

    /**
     * 参数实体
     */
    private ExcelParam param;

    /**
     * 创建参数实体
     *
     * @param exportModel      源数据
     * @return
     */
    public ExcelParam create(ExportModel exportModel) {
        param = new ExcelParam();
        // 设置源表头
        setOriginHeaders(exportModel);
        // 设置源数据
        setOriginDatas(exportModel.getDatas());
        return param;
    }

    /**
     * 设置源表头
     *
     * @param exportModel        类
     */
    private void setOriginHeaders(ExportModel exportModel) {
        // 通过反射获取源表头
        List<ExcelOriginHeader> headers = getOriginHeaders(exportModel);
        param.setOriginHeaders(headers);
    }

    /**
     * 设置源数据
     *
     * @param datas 源数据
     */
    private void setOriginDatas(List<?> datas) {
        param.setOriginDatas(datas);
    }

    /**
     * 通过反射获取源表头
     *
     * @param exportModel   导出类型
     * @return
     */
    private List<ExcelOriginHeader> getOriginHeaders(ExportModel exportModel) {
        List<ExcelOriginHeader> originHeaders = new ArrayList<ExcelOriginHeader>();
        if (exportModel == null || null == exportModel.getTargetClass()) {
            return originHeaders;
        }
        Field[] fields = exportModel.getTargetClass().getDeclaredFields();
        // key:注解Header对应的name，value:类BaseExcelData的属性
        Map<String, Field> nameFieldMap = new HashMap<>(10);
        int headerSortIndex = 99;
        String mergeHeaders = StringUtils.isBlank(exportModel.getMergeHeader()) ? ""
                : exportModel.getMergeHeader() + "|";
        for (Field field : fields) {
            Header header = field.getAnnotation(Header.class);
            // 如果类中的属性有注解Header，说明是作为可导出使用
            if (null != header) {
                List<String> headerColumn = exportModel.getHeaders();
                // 说明是动态设备模板导出
                if (ReportTaskTypeConstant.EXPORT_DEVICE.equals(exportModel.getExportType())) {
                    // 字段名称包含field开头的，说明是用来动态扩展字段的
                    if (field.getName().contains("field")) {
                        if (headerColumn != null && !headerColumn.isEmpty()) {
                            String deviceHeaderName = headerColumn.remove(0);
                            nameFieldMap.put(mergeHeaders + deviceHeaderName, field);
                            originHeaders
                                    .add(new ExcelOriginHeader(mergeHeaders + deviceHeaderName, headerSortIndex++));
                        }
                    } else {
                        String name = header.name();
                        if (!StringUtils.isEmpty(name)) {
                            nameFieldMap.put(mergeHeaders + name, field);
                            originHeaders.add(new ExcelOriginHeader(mergeHeaders + name, header.sort()));
                        }
                    }
                } else {
                    String name = header.name();
                    if (!StringUtils.isEmpty(name)) {
                        if (headerColumn != null && !headerColumn.isEmpty()) {
                            if (headerColumn.contains(name)) {
                                nameFieldMap.put(mergeHeaders + name, field);
                                originHeaders.add(new ExcelOriginHeader(mergeHeaders + name, header.sort()));
                            }
                        } else {
                            nameFieldMap.put(mergeHeaders + name, field);
                            originHeaders.add(new ExcelOriginHeader(mergeHeaders + name, header.sort()));
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(originHeaders)) {
            return originHeaders;
        }
        // 源表头排序
        sort(originHeaders);
        // key:注解Header对应的name，value:排序
        Map<String, Integer> nameSortMap = new HashMap<>(10);
        for (int i = 0; i < originHeaders.size(); i++) {
            nameSortMap.put(originHeaders.get(i).getName(), i);
        }
        // 设置每个要导出的类的属性，在excel中的列坐标
        setFieldSortMap(nameFieldMap, nameSortMap);
        return originHeaders;
    }

    /**
     * 源表头排序
     *
     * @param originHeaders 源表头数据
     */
    private void sort(List<ExcelOriginHeader> originHeaders) {
        originHeaders.sort(new Comparator<ExcelOriginHeader>() {
            @Override
            public int compare(ExcelOriginHeader o1, ExcelOriginHeader o2) {
                return o1.getSort() - o2.getSort();
            }
        });
    }

    /**
     * 设置类中的每个属性，在excel表格中应该放置的位置
     *
     * @param nameFieldMap
     * @param nameSortMap
     */
    private void setFieldSortMap(Map<String, Field> nameFieldMap, Map<String, Integer> nameSortMap) {
        Map<Field, Integer> fieldSortMap = new HashMap<>(10);
        if (!nameFieldMap.isEmpty() && !nameSortMap.isEmpty()) {
            for (Map.Entry<String, Field> entry : nameFieldMap.entrySet()) {
                String key = entry.getKey();
                int index = nameSortMap.get(key);
                fieldSortMap.put(entry.getValue(), index);
            }
        }
        param.setFieldSortMap(fieldSortMap);
    }

}
