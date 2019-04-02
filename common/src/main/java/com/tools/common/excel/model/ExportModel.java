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
 * 2018年12月29日    Administrator         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.excel.model;

import java.util.List;

/**
 * @author zhengjuwnei
 * @version 1.0
 * @date 2018年12月29日 上午11:55:25
 */
public class ExportModel<E> {
    /**
     * 要导出的类
     */
    private Class<E> targetClass;

    /**
     * 要导出的数据
     */
    private List<E> datas;

    /**
     * 导出的类型
     */
    private String exportType;

    /**
     * 要导出的列头名称
     */
    private List<String> headers;

    /**
     * 表头合并列
     */
    private String mergeHeader;

    /**
     * 查询数据接口是否需要分页查询，true--不分页，false-分页
     */
    private boolean noPageFlag;

    public ExportModel(Class<E> targetClass, List<E> datas, List<String> headers, String exportType,
            String mergeHeader) {
        super();
        this.targetClass = targetClass;
        this.datas = datas;
        this.exportType = exportType;
        this.headers = headers;
        this.mergeHeader = mergeHeader;
    }

    public ExportModel(Class<E> targetClass, List<E> datas) {
        super();
        this.targetClass = targetClass;
        this.datas = datas;
    }

    public void append(List<E> list) {
        if (datas == null) {
            datas = list;
        } else {
            datas.addAll(list);
        }
    }

    public Class<E> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<E> targetClass) {
        this.targetClass = targetClass;
    }

    public List<E> getDatas() {
        return datas;
    }

    public void setDatas(List<E> datas) {
        this.datas = datas;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public String getMergeHeader() {
        return mergeHeader;
    }

    public void setMergeHeader(String mergeHeader) {
        this.mergeHeader = mergeHeader;
    }

    public boolean isNoPageFlag() {
        return noPageFlag;
    }

    public void setNoPageFlag(boolean noPageFlag) {
        this.noPageFlag = noPageFlag;
    }

}
