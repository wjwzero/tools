package com.tools.base.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Excel 生成通用类，为了兼容，所有 Excel 统一生成 Excel2003 即：xx.xls
 *
 * @author zhengjunwei
 * @date 2018年11月15日 下午9:09:38
 */
public class ExcelExportHelper {
    public static final Pattern PATTERN_NUMBER = Pattern.compile("^//d+(//.//d+)?$");
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 时间格式：默认为yyyy-MM-dd
     */
    private String datePattern = "yyyy-MM-dd";
    /**
     * 图片宽度，默认为：100
     */
    private int imageWidth = 30;
    /**
     * 图片高度，默认为：50
     */
    private int imageHeight = 5;
    /**
     * 单元格的最大宽度
     */
    private int[] maxWidth;
    /**
     * 单页支持最多数据列：超过65534会出错
     * 若数据列多余65534则需要通过moreExcelFlag、moreSheetFlag来区别生成多个Excel、还是sheet
     */
    private int maxRowCount = 2500;
    /**
     * 大量数据，多个Excel标识---0001
     */
    private String moreExcelFlag = "0001";
    /**
     * 大量数据，多个sheet标识---0001
     */
    private String moreSheetFlag = "0002";

    /**
     * 默认构造函数
     */
    public ExcelExportHelper() {
    }

    /**
     * @param datePattern 指定的时间格式
     */
    public ExcelExportHelper(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * @param imageWidth  指定图片的宽度
     * @param imageHeight 指定图片的高度
     */
    public ExcelExportHelper(int imageWidth, int imageHeight) {
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }

    /**
     * @param datePatter  指定时间格式
     * @param imageWidth  指定图片的宽度
     * @param imageHeight 指定图片的高度
     */
    public ExcelExportHelper(String datePatter, int imageWidht, int imageHeight) {
        this.datePattern = datePatter;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidht;
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]
     * <br>
     * header、excelList规则如下：<br>
     * header、excelList中的Bean必须对应（javaBean的属性顺序）：如下<br>
     * header：姓名、年龄、性别、班级<br>
     * Bean：name、age、sex、class<br>
     *
     * @param header     表格属性列名数组
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 javabean
     *                   属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @return 生成的HSSFWorkBook
     * @throws Exception
     * @author
     * @date 2018年11月15日 下午9:18:37
     * @version 1.0
     */
    public HSSFWorkbook exportExcel(String[] header, List<Object> excelList, String sheetTitle) {
        try {
            // 生成一个Excel
            HSSFWorkbook book = new HSSFWorkbook();
            // 生成一个表格,判断、设置sheetTitle
            sheetTitle = getSheetTitle(sheetTitle);
            HSSFSheet sheet = book.createSheet(sheetTitle);
            // 设置Excel里面数据
            setExcelContentData(book, sheet, header, excelList);
            log.info("——————————————————ExcelExportHelper:Excel生成成功...");
            return book;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]
     * <br>
     * header、properties需要一一对应：<Br>
     * header = ["学号","年龄","性别","班级"] properties =
     * ["id","age","sex","class"],其对应的excelList中javaBean的属性值
     *
     * @param header     Excel表头
     * @param properties 表头对应javaBean中的属性
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @return 生成的HSSFWorkbook
     * @author
     * @date 2018年11月19日 下午6:02:02
     * @version 1.0
     */
    public HSSFWorkbook exportExcel(String[] header, String[] properties, List<Object> excelList, String sheetTitle) {
        // 生成一个Excel
        HSSFWorkbook book = new HSSFWorkbook();
        // 生成一个表格,判断、设置sheetTitle
        sheetTitle = getSheetTitle(sheetTitle);
        HSSFSheet sheet = book.createSheet(sheetTitle);

        // 设置Excel里面数据
        setExcelContentData(book, sheet, header, properties, excelList);

        log.info("——————————————————ExcelExportHelper:Excel生成成功...");

        return book;
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
     * 如有图片请转换为byte[]<br>
     * header、excelList规则如下：<br>
     * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
     * header：姓名、年龄、性别、班级<br>
     * Bean：name、age、sex、class<br>
     *
     * @param header     表格属性列名数组
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param filePath   Excel文件保存位置
     * @param fileName   Excel文件名
     * @return
     * @author
     * @date 2018年11月17日 下午2:24:38
     * @version 1.0
     */
    public void exportExcelAndSave(String[] header, List<Object> excelList, String sheetTitle, String filePath,
                                   String fileName) {
        // 生成Excel
        HSSFWorkbook book = exportExcel(header, excelList, sheetTitle);

        // 保存生成的Excel
        saveExcel(book, filePath, fileName);
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
     * 如有图片请转换为byte[]<br>
     * header、properties需要一一对应：<Br>
     * header = ["学号","年龄","性别","班级"]<Br>
     * properties = ["id","age","sex","class"],其对应的excelList中javaBean的属性值
     *
     * @param header     表格属性列名数组
     * @param properties 表头对应javaBean中的属性
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param filePath   Excel文件保存位置
     * @param fileName   Excel文件名
     * @author
     * @date 2018年11月19日 下午6:24:56
     * @version 1.0
     */
    public void exportExcelAndSave(String[] header, String[] properties, List<Object> excelList, String sheetTitle,
                                   String filePath, String fileName) {
        // 生成Excel
        HSSFWorkbook book = exportExcel(header, properties, excelList, sheetTitle);
        // 保存生成的Excel
        saveExcel(book, filePath, fileName);
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包 zip
     * 格式保存至某个路径下, 如有图片请转换为byte[]<br>
     * header、excelList规则如下：<br>
     * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
     * header：姓名、年龄、性别、班级<br>
     * Bean：name、age、sex、class<br>
     *
     * @param header     表格属性列名数组
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param filePath   zip文件保存位置
     * @param excelName  Excel名称
     * @param zipName    zip名称
     * @author
     * @date 2018年11月18日 下午12:36:01
     * @version 1.0
     */
    public void exportExcelAndZip(String[] header, List<Object> excelList, String sheetTitle, String filePath,
                                  String excelName, String zipName) {
        // 生成Excel
        HSSFWorkbook book = exportExcel(header, excelList, sheetTitle);

        // 将生成的Excel打包保存起来
        List<HSSFWorkbook> books = new ArrayList<HSSFWorkbook>();
        books.add(book);
        zipExcelAndSave(books, filePath, zipName, excelName);
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包 zip
     * 格式保存至某个路径下, 如有图片请转换为byte[]<br>
     * header、properties需要一一对应：<Br>
     * header = ["学号","年龄","性别","班级"] properties =
     * ["id","age","sex","class"],其对应的excelList中javaBean的属性值
     *
     * @param header     表格属性列名数组
     * @param properties 表头对应javaBean的属性
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param filePath   zip文件保存位置
     * @param excelName  Excel名称
     * @param zipName    zip名称
     * @author
     * @date 2018年11月19日 下午6:33:04
     * @version 1.0
     */
    public void exportExcelAndZip(String[] header, String[] properties, List<Object> excelList, String sheetTitle,
                                  String filePath, String excelName, String zipName) {
        // 生成Excel
        HSSFWorkbook book = exportExcel(header, properties, excelList, sheetTitle);

        // 将生成的Excel打包保存起来
        List<HSSFWorkbook> books = new ArrayList<HSSFWorkbook>();
        books.add(book);
        zipExcelAndSave(books, filePath, zipName, excelName);
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]
     * <br>
     * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
     * header、excelList规则如下：<br>
     * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
     * header：姓名、年龄、性别、班级<br>
     * Bean：name、age、sex、class<br>
     *
     * @param header     表格属性列名数组
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param flag       分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
     * @return List<HSSFWorkbook>
     * @author
     * @date 2018年11月17日 下午9:53:15
     * @version 1.0
     */
    public List<HSSFWorkbook> exportExcelForBigData(String[] header, List<Object> excelList, String sheetTitle,
                                                    String flag) {
        // 创建表数据结果集
        List<HSSFWorkbook> list = new ArrayList<>();

        // 判断需要生成几个Excel
        int num = excelList.size() % maxRowCount == 0 ? excelList.size() / maxRowCount
                : excelList.size() / maxRowCount + 1;

        HSSFWorkbook book = new HSSFWorkbook();
        // 新数据列表
        List<Object> newList = null;
        // 新title
        String newTitle = null;
        for (int i = 0; i < num; i++) {
            // 计算新的数据列表
            int beginRowNum = maxRowCount * i;
            int endRowNum = maxRowCount * (i + 1) > excelList.size() ? excelList.size() : maxRowCount * (i + 1);
            newList = excelList.subList(beginRowNum, endRowNum);
            newTitle = getSheetTitle(sheetTitle) + "_" + i;
            if (moreExcelFlag.equals(flag)) {
                // 如果是创建多个Excel
                book = exportExcel(header, newList, newTitle);
                list.add(book);
            } else if (moreSheetFlag.equals(flag)) {
                // 创建多sheet
                HSSFSheet sheet = book.createSheet(newTitle);
                setExcelContentData(book, sheet, header, newList);
            }
        }

        if (moreSheetFlag.equals(flag)) {
            // 创建多sheet
            list.add(book);
        }

        return list;
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]
     * <br>
     * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
     * header、properties需要一一对应：<Br>
     * header = ["学号","年龄","性别","班级"] properties =
     * ["id","age","sex","class"],其对应的excelList中javaBean的属性值
     *
     * @param header     表格属性列名数组
     * @param properties 表头对应javaBean的属性
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param flag       分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
     * @return List<HSSFWorkbook>
     * @author
     * @date 2018年11月19日 下午6:41:23
     * @version 1.0
     */
    public List<HSSFWorkbook> exportExcelForBigData(String[] header, String[] properties, List<Object> excelList,
                                                    String sheetTitle, String flag) {
        // 创建表数据结果集
        List<HSSFWorkbook> list = new ArrayList<>();
        // 判断需要生成几个Excel
        int num = excelList.size() % maxRowCount == 0 ? excelList.size() / maxRowCount
                : excelList.size() / maxRowCount + 1;

        HSSFWorkbook book = new HSSFWorkbook();
        List<Object> newList = null;
        String newTitle = null;
        for (int i = 0; i < num; i++) {
            // 计算新的数据列表
            int beginRowNum = maxRowCount * i;
            int endRowNum = maxRowCount * (i + 1) > excelList.size() ? excelList.size() : maxRowCount * (i + 1);
            newList = excelList.subList(beginRowNum, endRowNum);
            newTitle = getSheetTitle(sheetTitle) + "_" + i;
            if (moreExcelFlag.equals(flag)) {
                // 如果是创建多个Excel
                book = exportExcel(header, properties, newList, newTitle);
                list.add(book);
            } else if (moreSheetFlag.equals(flag)) {
                // 创建多sheet
                HSSFSheet sheet = book.createSheet(newTitle);
                setExcelContentData(book, sheet, header, properties, newList);
            }
        }

        if (moreSheetFlag.equals(flag)) {
            // 创建多sheet
            list.add(book);
        }
        return list;
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
     * 如有图片请转换为byte[]<br>
     * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
     * header、excelList规则如下：<br>
     * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
     * header：姓名、年龄、性别、班级<br>
     * Bean：name、age、sex、class<br>
     *
     * @param header     表格属性列名数组
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param flag       分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
     * @param filePath   文件保存路径
     * @param fileName   保存文件名
     * @return
     * @author
     * @date 2018年11月17日 下午10:39:15
     * @version 1.0
     */
    public void exportExcelForBigDataAndSave(String[] header, List<Object> excelList, String sheetTitle, String flag,
                                             String filePath, String fileName) {
        // 获取数据结果集
        List<HSSFWorkbook> books = exportExcelForBigData(header, excelList, sheetTitle, flag);
        String newFileName = "";
        for (int i = 0; i < books.size(); i++) {
            HSSFWorkbook book = books.get(i);
            newFileName = getFileName(fileName) + "_0" + i;
            // 保存Excel文件
            saveExcel(book, filePath, newFileName);
        }
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
     * 如有图片请转换为byte[]<br>
     * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
     * header、properties需要一一对应：<Br>
     * header = ["学号","年龄","性别","班级"] properties =
     * ["id","age","sex","class"],其对应的excelList中javaBean的属性值
     *
     * @param header     表格属性列名数组
     * @param properties 表头对应javaBean属性
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param flag       分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
     * @param filePath   文件保存路径
     * @param fileName   保存文件名
     * @author
     * @date 2018年11月19日 下午8:22:25
     * @version 1.0
     */
    public void exportExcelForBigDataAndSave(String[] header, String[] properties, List<Object> excelList,
                                             String sheetTitle, String flag, String filePath, String fileName) {
        // 获取数据结果集
        List<HSSFWorkbook> books = exportExcelForBigData(header, properties, excelList, sheetTitle, flag);

        String newFileName = "";
        for (int i = 0; i < books.size(); i++) {
            HSSFWorkbook book = books.get(i);
            newFileName = getFileName(fileName) + "_0" + i;
            // 保存Excel文件
            saveExcel(book, filePath, newFileName);
        }
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包成 ZIP
     * 保存至某个路径下,如有图片请转换为byte[]<br>
     * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
     * header、excelList规则如下：<br>
     * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
     * header：姓名、年龄、性别、班级<br>
     * Bean：name、age、sex、class<br>
     *
     * @param header     表格属性列名数组
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param flag       分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
     * @param filePath   文件保存路径
     * @param excelName  Excel文件名
     * @param zipName    zip文件名
     * @return
     * @author
     * @date 2018年11月19日 下午10:39:15
     * @version 1.0
     */
    public void exportExcelForBigDataAndZipAndSave(String[] header, List<Object> excelList, String sheetTitle,
                                                   String flag, String filePath, String excelName, String zipName) {
        // 获取生成的Excel集合
        List<HSSFWorkbook> books = exportExcelForBigData(header, excelList, sheetTitle, flag);

        // 将生成的Excel打包并保存
        zipExcelAndSave(books, filePath, zipName, excelName);
    }

    /**
     * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包成 ZIP
     * 保存至某个路径下,如有图片请转换为byte[]<br>
     * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
     * header、properties需要一一对应：<Br>
     * header = ["学号","年龄","性别","班级"] properties =
     * ["id","age","sex","class"],其对应的excelList中javaBean的属性值
     *
     * @param header     表格属性列名数组
     * @param properties 表头对应javaBean属性
     * @param excelList  需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                   javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param sheetTitle 表格标题名
     * @param flag       分页标识为。 flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
     * @param filePath   文件保存路径
     * @param excelName  Excel文件名
     * @param zipName    ZIP文件名
     * @author
     * @date 2018年11月19日 下午8:24:21
     * @version 1.0
     */
    public void exportExcelForBigDataAndZipAndSave(String[] header, String[] properties, List<Object> excelList,
                                                   String sheetTitle, String flag, String filePath, String excelName,
												   String zipName) {
        // 获取生成的Excel集合
        List<HSSFWorkbook> books = exportExcelForBigData(header, properties, excelList, sheetTitle, flag);

        // 将生成的Excel打包并保存
        zipExcelAndSave(books, filePath, zipName, excelName);
    }

    /**
     * 填充Excel数据内容
     *
     * @param book      Excel
     * @param sheet     sheet
     * @param header    Excel头部title
     * @param excelList Excel数据列
     * @author
     * @date 2018年11月17日 下午10:32:34
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setExcelContentData(HSSFWorkbook book, HSSFSheet sheet, String[] header, List<Object> excelList) {
        // 设置列头样式(居中、变粗、蓝色)
        HSSFCellStyle headerStyle = book.createCellStyle();
        setHeaderStyle(headerStyle, book);

        // 设置单元格样式
        HSSFCellStyle cellStyle = book.createCellStyle();
        setCellStyle(cellStyle, book);

        // 创建头部
        HSSFRow row = createHeader(sheet, headerStyle, header);

        // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        int index = 0;
        /* 避免在迭代过程中产生的新对象太多，这里讲循环内部变量全部移出来 */
        Object t = null;
        HSSFCell cell = null;
        Field field = null;
        String fieldName = null;
        String getMethodName = null;
        Class tCls = null;
        Method getMethod = null;
        Object value = null;
        // 遍历集合数据，产生数据行
        Iterator<Object> it = excelList.iterator();
        // 初始化单元格宽度
        maxWidth = new int[header.length];
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            // 设置数据列
            t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                field = fields[i];
                fieldName = field.getName();
                // 构建getter方法
                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    value = (Object) getMethod.invoke(t, new Object[]{});
                    // 将value设置当单元格指定位置
                    setCellData(row, index, i, value, cell, sheet, patriarch, book);
                } catch (Exception e) {
                    log.error("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + e.getMessage());
                }
            }
        }

        log.info("-------------------------填充Excel数据成功..........");
    }

    /**
     * 填充Excel内容
     *
     * @param book
     * @param sheet
     * @param header
     * @param properties
     * @param excelList
     * @author
     * @date 2018年11月19日 下午6:00:35
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    private void setExcelContentData(HSSFWorkbook book, HSSFSheet sheet, String[] header, String[] properties,
                                     List<Object> excelList) {
        // 设置列头样式(居中、变粗、蓝色)
        HSSFCellStyle headerStyle = book.createCellStyle();
        setHeaderStyle(headerStyle, book);

        // 设置单元格样式
        HSSFCellStyle cellStyle = book.createCellStyle();
        setCellStyle(cellStyle, book);

        // 创建头部
        HSSFRow row = createHeader(sheet, headerStyle, header);

        // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        /* 为了避免迭代过程中产生过多的新对象，这里将循环内部变量全部移出来 */
        int index = 0;
        Object t = null;
        HSSFCell cell = null;
        Object o = null;
        Class clazz = null;
        PropertyDescriptor pd = null;
        Method getMethod = null;
        // 遍历集合数据，产生数据行
        Iterator<Object> it = excelList.iterator();
        // 初始化单元格宽度
        maxWidth = new int[header.length];
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            // 设置数据列
            t = it.next();
            for (int i = 0; i < header.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                // 每一个单元格都需要将O设置为null
                o = null;
                try {
                    clazz = t.getClass();
                    pd = new PropertyDescriptor(properties[i], clazz);
                    // 获得get方法
                    getMethod = pd.getReadMethod();
                    if (pd != null) {
                        // 执行get方法返回一个Object
                        o = getMethod.invoke(t);
                    }
                    setCellData(row, index, i, o, cell, sheet, patriarch, book);
                } catch (Exception e) {
                    log.error("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + e.getMessage());
                }
            }
        }

        log.info("——————————————————填充Excel数据成功..........");
    }

    /**
     * 设置sheet的title，若为空则为yyyyMMddHH24mmss
     *
     * @param sheetTitle
     * @return
     * @author
     * @date 2018年11月16日 下午1:46:06
     * @version 1.0
     */
    private String getSheetTitle(String sheetTitle) {
        String title = null;
        if (sheetTitle != null && !"".equals(sheetTitle)) {
            title = sheetTitle;
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
            title = sdf.format(date);
        }
        return title;
    }

    /**
     * 设置Excel图片的格式：字体居中、变粗、蓝色、12号
     *
     * @param headerStyle 头部样式
     * @param book        生产的excel book HSSFWorkbook对象
     * @author
     * @date 2018年11月16日 下午8:46:49
     * @version 1.0
     */
    private void setHeaderStyle(HSSFCellStyle headerStyle, HSSFWorkbook book) {
        // 水平居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置字体
        HSSFFont font = book.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setColor(HSSFColorPredefined.BLUE.getIndex());

        headerStyle.setFont(font);
    }

    /**
     * 设置单元格样式
     *
     * @param cellStyle 单元格样式
     * @param book      book HSSFWorkbook对象
     * @author
     * @date 2018年11月17日 上午11:00:53
     * @version 1.0
     */
    private void setCellStyle(HSSFCellStyle cellStyle, HSSFWorkbook book) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 水平居中
        HSSFFont font = book.createFont();
        font.setFontHeightInPoints((short) 12);

        cellStyle.setFont(font);
    }

    /**
     * 根据头部样式、头部数据创建Excel头部
     *
     * @param sheet       sheet
     * @param headerStyle 头部样式
     * @param header      头部数据
     * @return 设置完成的头部Row
     * @author
     * @date 2018年11月17日 上午11:37:28
     * @version 1.0
     */
    private HSSFRow createHeader(HSSFSheet sheet, HSSFCellStyle headerStyle, String[] header) {
        HSSFRow headRow = sheet.createRow(0);
        headRow.setHeightInPoints((short) (20));
        // 设置头部高度
        // 添加数据
        HSSFCell cell = null;
        for (int i = 0; i < header.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headerStyle);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            cell.setCellValue(text);
        }

        return headRow;
    }

    /**
     * 设置单元格数据
     *
     * @param row       指定行
     * @param index
     * @param i         行数
     * @param value     单元格值 cellValue
     * @param cell      单元格 HSSFCell对象
     * @param sheet     sheet HSSFSheet对象
     * @param patriarch 顶级画板 用于实现突破
     * @param book      Excel HSSFWorkbook对象
     * @author
     * @date 2018年11月17日 上午11:48:14
     * @version 1.0
     */
    private void setCellData(HSSFRow row, int index, int i, Object value, HSSFCell cell, HSSFSheet sheet,
                             HSSFPatriarch patriarch, HSSFWorkbook book) {
        String textValue = null;
        if (value instanceof Date) {
            // 为日期设置时间格式
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            textValue = sdf.format(date);
        }
        if (value instanceof byte[]) {
            // byte为图片
            // 设置图片单元格宽度、高度
            row.setHeightInPoints((short) (imageHeight * 10));
            sheet.setColumnWidth(i, imageWidth * 256);
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) i, index, (short) i, index);
            // 插入图片
            byte[] bsValue = (byte[]) value;
            patriarch.createPicture(anchor, book.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
        } else {
            // 其余全部当做字符处理
            if (value != null) {
                textValue = String.valueOf(value);
            } else {
                textValue = "";
            }
        }
        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
        if (textValue != null) {
            Matcher matcher = PATTERN_NUMBER.matcher(textValue);

            // 设置单元格宽度，是文字能够全部显示
            setCellMaxWidth(textValue, i);
            sheet.setColumnWidth(i, maxWidth[i]);
            // 设置单元格宽度
            row.setHeightInPoints((short) (20));
            // 设置单元格高度
            if (matcher.matches()) {
                // 是数字当作double处理
                cell.setCellValue(Double.parseDouble(textValue));
            } else {
                cell.setCellValue(textValue);
            }
        }
    }

    /**
     * 获取文件名，若为空，则规则为：yyyyMMddHH24mmss+6位随机数
     *
     * @param fileName 文件名
     * @return
     * @author
     * @date 2018年11月17日 下午5:44:27
     * @version 1.0
     */
    private String getFileName(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            // 日期
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
            // 随机数
            Random random = new Random();
            fileName = sdf.format(date) + String.valueOf(Math.abs(random.nextInt() * 1000000));
        }
        return fileName;
    }

    /**
     * 根据字数来获取单元格大小,并更新当前列的最大宽度
     *
     * @param textValue
     * @param 指定列
     * @return
     * @author
     * @date 2018年11月17日 下午7:35:52
     * @version 1.0
     */
    private void setCellMaxWidth(String textValue, int i) {
        int size = textValue.length();
        int width = (size + 6) * 256;
        if (maxWidth[i] <= width) {
            maxWidth[i] = width;
        }
    }

    /**
     * 将生成的Excel保存到指定路径下
     *
     * @param book     生成的Excel HSSFWorkbook对象
     * @param filePath 需要保存的路劲
     * @param fileName Excel文件名
     * @author
     * @date 2018年11月19日 下午6:10:17
     * @version 1.0
     */
    private void saveExcel(HSSFWorkbook book, String filePath, String fileName) {
        // 检测保存路劲是否存在，不存在则新建
        checkFilePathIsExist(filePath);
        // 将Excel保存至指定目录下
        fileName = getFileName(fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath + "\\" + fileName + ".xls");
            book.write(out);
            log.info("——————————————————保存Excel文件成功，保存路径：" + filePath + "\\" + fileName + ".xls");
        } catch (Exception e) {
            log.error("——————————————————保存Excel文件失败。exportExcelForListAndSave,message：" + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                    book.close();
                } catch (IOException e) {
                    log.error("表格出错", e);
                }
            }
        }
    }

    /**
     * 将生成的Excel打包并保存到指定路径下
     *
     * @param book      生成的Excel HSSFWorkbook list集合
     * @param filePath  保存路劲
     * @param zipName   zip 文件名
     * @param excelName Excel文件名
     * @author
     * @date 2018年11月19日 下午6:18:09
     * @version 1.0
     */
    private void zipExcelAndSave(List<HSSFWorkbook> books, String filePath, String zipName, String excelName) {
        // 检测保存路径是否存在，若不存在则新建
        checkFilePathIsExist(filePath);

        zipName = getFileName(zipName);
        excelName = getFileName(excelName);

        // 将Excel打包并保存至指定目录下
        FileOutputStream out = null;
        ZipOutputStream zip = null;
        try {
            out = new FileOutputStream(filePath + "\\" + zipName + ".zip");
            zip = new ZipOutputStream(out);
            HSSFWorkbook book = null;
            String excelNameNew = "";
            for (int i = 0; i < books.size(); i++) {
                book = books.get(i);
                excelNameNew = getFileName(excelNameNew) + "_0" + i;
                ZipEntry entry = new ZipEntry(excelNameNew + ".xls");
                zip.putNextEntry(entry);
                book.write(zip);
            }
            log.info("——————————————————保存Excel文件成功，保存路径：" + filePath + "\\" + zipName + ".xls");
        } catch (FileNotFoundException e) {
            log.error(
                    "——————————————————保存Excel文件失败。method:exportExcelForBigDataAndSave,message：" + e.getMessage());
        } catch (IOException e) {
            log.error(
                    "——————————————————保存Excel文件失败。method:exportExcelForBigDataAndSave,message：" + e.getMessage());
        } finally {
            if (zip != null) {
                try {
                    zip.flush();
                    zip.close();
                } catch (IOException e) {
                    log.error("表格出错", e);
                }
            }

            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    log.error("表格出错", e);
                }
            }

            if (books != null && !books.isEmpty()) {
                for (HSSFWorkbook hssfWorkbook : books) {
                    try {
                        hssfWorkbook.close();
                    } catch (IOException e) {
                        log.error("表格出错", e);
                    }
                }
            }
        }
    }

    /**
     * 检测保存路径是否存在，不存在则新建
     *
     * @param filePath 文件路径
     * @author
     * @date 2018年11月18日 下午1:05:17
     * @version 1.0
     */
    private void checkFilePathIsExist(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
