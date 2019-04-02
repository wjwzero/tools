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
 * 2018年12月24日    zhengjunwei         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.excel.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author zhengjunwei
 * @version 1.0
 * @date 2018年12月24日 上午9:54:01
 */
public class ExcelUtils {
    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static Sheet setSheetDownList(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol,
                                         int endCol) {
        XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidationHelper help = new XSSFDataValidationHelper((XSSFSheet) sheet);
        DataValidation validation = help.createValidation(constraint, regions);
        sheet.addValidationData(validation);
        return sheet;
    }

    /**
     * 设置列自适应宽度
     *
     * @param sheet
     * @param cell
     * @author zhengjunwei
     * @date 2018年12月24日 下午4:09:43
     */
    public static void setColumnWidth(Sheet sheet, Cell cell) {
        /** 设置每列自适应宽度，保证最长能显示出来 */
        int column = cell.getColumnIndex();
        int width = sheet.getColumnWidth(column) / 256;
        int length = cell.toString().getBytes(Charset.forName("GBK")).length;

        if (width < length + 4) {
            width = length + 4;
        }
        if ((255 / 2) < width) {
            width = 255 / 2;
        }
        sheet.setColumnWidth(column, width * 256);
    }

    /**
     * 设置合并单元格边框
     *
     * @param cellAddress
     * @param sheet
     * @author zhengjunwei
     * @date 2018年12月24日 下午6:47:33
     */
    public static void setMergeColumBorder(CellRangeAddress cellAddress, Sheet sheet) {
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellAddress, sheet);
    }

    /**
     * 设置列头，表头单元格样式
     *
     * @param workbook
     * @return
     * @author zhengjunwei
     * @date 2018年12月24日 下午6:48:00
     */
    public static CellStyle cellStyle(Workbook workbook) {
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        // 设置边框
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        // 字体大小
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        // 粗体
        return headerStyle;
    }

    /**
     * 保存excel
     *
     * @param workbook
     * @param file
     * @author zhengjunwei
     * @date 2018年12月24日 下午6:48:29
     */
    public static void saveExcelFile(Workbook workbook, File file) {

        /* 文件不存在时 */
        if (!file.exists()) {
            int index = file.getName().indexOf('.');
            if (index < 0) {
                // 当作目录
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(out);
        }

    }

    public static String getCellString(Cell cell) {
        String value = "";
        if (cell != null) {
            cell.setCellType(CellType.STRING);
            value = cell.getStringCellValue();
        }
        return value.trim();
    }

    /**
     * 关闭流资源
     *
     * @param resource
     * @author zhengjunwei
     * @date 2019年1月4日 上午9:57:54
     */
    public static void closeResource(Closeable... resource) {
        if (resource == null) {
            return;
        }
        for (Closeable closeable : resource) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setColumnTextStyle(CellStyle textStyle, int columnstart, int columnend, Sheet sheet) {
        /**设置单元格格式为文本格式*/
        for (int i = 3; i < 50003; i++) {
            Row row = sheet.createRow(i);

            for (int j = columnstart; j < columnend; j++) {
                Cell cell = row.createCell(j);
                //设置单元格格式为"文本"
                cell.setCellStyle(textStyle);
                cell.setCellType(CellType.STRING);
            }

        }
    }

}
