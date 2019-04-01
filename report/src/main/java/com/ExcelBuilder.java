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
 * 2018年1月25日    WangJianWei         Create the class
 * http://www.jimilab.com/
 */

package com;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import java.util.List;

/**
 * @author WangJianWei
 * @version 1.0
 * @FileName ExcelBuilder.java
 * @Description:
 * @Date 2018年1月25日 下午7:23:56
 */
public abstract class ExcelBuilder {
/*
    *//**
     * excel表头
     *//*
    private List<String> excelHeader;
    *//**
     * excel页名
     *//*
    private String sheetName;

    *//**
     * 行数
     *//*
    private int rowNo = 50000;

    public ExcelBuilder(List<String> excelHeader, String sheetName, int rowNo) {
        this.excelHeader = excelHeader;
        this.sheetName = sheetName;
        this.rowNo = rowNo;
    }

    *//**
     * @param list
     * @return
     * @Title: bulidRow
     * @Description: 获得项目抽象方法
     * @author WangJianWei
     * @date 2018年1月26日 上午9:15:48
     *//*
    protected abstract void bulidRow(HSSFRow row, Object obj);

    public HSSFWorkbook Export(List list) {

        if (list.size() == 0) {
            return null;
        }

        int list_size = list.size();
        int num = list_size % rowNo;
        int num1 = num == 0 ? list_size / rowNo : list_size / rowNo + 1;

        String[] header = excelHeader.toArray(new String[excelHeader.size()]);
        HSSFWorkbook wb = new HSSFWorkbook();

        for (int j = 1; j <= num1; j++) {

            HSSFSheet sheet = wb.createSheet(sheetName + j);
            HSSFRow row = sheet.createRow((int) 0);
            HSSFCellStyle style = wb.createCellStyle();

            Font font = wb.createFont();
            // 粗体
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
            // 水平居中
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            row.setHeight((short) (15.625 * 20));

            for (int i = 0; i < header.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(style);
                sheet.setColumnWidth(i, 5000);
            }

            //当没一个sheet满50000条的时候就新建一个sheet。每个sheet最多放65535条数据
            for (int i = rowNo * j - rowNo; i < rowNo * j && i < list_size; i++) {
                row = sheet.createRow(i - rowNo * j + rowNo + 1);
                Object obj = list.get(i);
                bulidRow(row, obj);
            }
        }

        return wb;
    }*/
}
