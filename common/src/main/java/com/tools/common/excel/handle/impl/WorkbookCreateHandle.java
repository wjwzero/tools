package com.tools.common.excel.handle.impl;

import com.tools.base.util.StringUtils;
import com.tools.common.excel.handle.ExcelHandle;
import com.tools.common.excel.model.ExcelHeader;
import com.tools.common.excel.model.ExcelParam;
import com.tools.common.excel.model.ExcelResult;
import com.tools.common.excel.model.RowCol;
import com.tools.common.excel.util.ExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * excel工作薄处理类
 *
 * @author zhengjunwei
 * @date 2018/3/13 11:40
 **/
public class WorkbookCreateHandle implements ExcelHandle {

    private ExcelResult result;

    public WorkbookCreateHandle(ExcelParam param, ExcelResult result) {
        this.result = result;
    }

    @Override
    public void handle() {
        // 生成excel工作薄
        createWorkbook();
    }

    /**
     * 生成工作簿
     *
     * @author zhengjunwei
     * @date 2018年12月29日 下午2:16:52
     */
    private void createWorkbook() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        // 在excel中生成表头
        createHeader(sheet, ExcelUtils.cellStyle(workbook));
        // 在excel中生成数据
        createData(sheet, getDataStyle(workbook));
        result.setWorkbook(workbook);
    }

    /**
     * 数据样式
     *
     * @param workbook 工作薄
     * @return
     */
    private CellStyle getDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 字体居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 创建表头
     *
     * @param sheet sheet页
     * @param style 样式
     */
    private void createHeader(Sheet sheet, CellStyle style) {
        Row row;
        Cell cell;
        RowCol rowCol = result.getHeaderRowCol();
        ExcelHeader[][] headers = result.getHeaders();
        int r = rowCol.getRow();
        int c = rowCol.getCol();
        for (int i = 0; i < r; i++) {
            row = sheet.createRow(i);
            for (int j = 0; j < c; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(style);
                sheet.setColumnWidth(j, 20 * 256);
                // 判断表头数据是否可用
                if (isHeaderAvailable(headers[i][j])) {
                    cell.setCellValue(headers[i][j].getName());
                }
            }
        }
        // 合并表头
        mergeHeader(sheet);
    }

    /**
     * 合并表头
     *
     * @param sheet sheet页
     */
    private void mergeHeader(Sheet sheet) {
        CellRangeAddress range;
        RowCol rowCol = result.getHeaderRowCol();
        ExcelHeader[][] headers = result.getHeaders();
        int r = rowCol.getRow();
        int c = rowCol.getCol();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                // 判断当前表头单元是否可用，是否要合并
                if (isHeaderAvailable(headers[i][j]) && isMerge(headers[i][j])) {
                    // 合并单元格，起始行, 终止行, 起始列, 终止列
                    range = new CellRangeAddress(i, i + headers[i][j].getRow() - 1, j, j + headers[i][j].getCol() - 1);
                    sheet.addMergedRegion(range);
                }
            }
        }
    }

    /**
     * 判断表头单元是否要合并
     *
     * @param header 表头单元
     * @return
     */
    private boolean isMerge(ExcelHeader header) {
        return header.getRow() > 1 || header.getCol() > 1;
    }

    /**
     * 判断表头单元是否可用
     *
     * @param header
     * @return
     */
    private boolean isHeaderAvailable(ExcelHeader header) {
        return null != header && StringUtils.isNotEmpty(header.getName()) && header.isAvailable();
    }

    /**
     * 创建数据
     *
     * @param sheet sheet页
     * @param style 样式
     */
    private void createData(Sheet sheet, CellStyle style) {
        RowCol rowCol = result.getHeaderRowCol();
        int startRow = rowCol.getRow();
        List<List<String>> datas = result.getDatas();
        for (List<String> data : datas) {
            Row row = sheet.createRow(startRow);
            for (int i = 0; i < data.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(data.get(i));
                ExcelUtils.setColumnWidth(sheet, cell);
            }
            startRow++;
        }
    }


}
