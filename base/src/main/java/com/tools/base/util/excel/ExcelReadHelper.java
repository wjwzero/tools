package com.tools.base.util.excel;

import com.tools.base.constant.DateFormatEnum;
import com.tools.base.constant.ExcelJavaTypeEnum;
import com.tools.base.util.StringUtils;
import com.tools.base.util.date.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析Excel
 *
 * @author
 * @date2018年11月3日
 */
public class ExcelReadHelper {

    private static final int NINETEEN_DATE_LENGTH = 19;
    private static final int FOURTEEN_DATE_LENGTH = 19;

    /**
     * 解析Excel 利用反射技术完成propertis到obj对象的映射，并将相对应的值利用相对应setter方法设置到obj对象中最后add到list集合中<br>
     * properties、obj需要符合如下规则：<br>
     * 1、obj对象必须存在默认构造函数，且属性需存在setter方法<br>
     * 2、properties中的值必须是在obj中存在的属性，且obj中必须存在这些属性的setter方法。<br>
     * 3、properties中值得顺序要与Excel中列相相应，否则值会设置错：<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;excel:编号 姓名 年龄 性别<br>
     * properties:id name age sex<br>
     *
     * @param properties
     *            与Excel相对应的属性
     * @param obj
     *            反射对象的Class
     * @return
     * @throws Exception
     * @autor:
     * @date2018年11月9日
     */
    @SuppressWarnings("rawtypes")
    public static List<Object> excelRead(Workbook book, String[] properties, Class obj, int startRow)
            throws Exception {
        return getExcelContent(book, properties, obj, startRow);
    }

    /**
     * 根据params、object解析Excel，并且构建list集合
     *
     * @param book
     *            WorkBook对象，他代表了待将解析的Excel文件
     * @param properties
     *            需要参考Object的属性
     * @return
     * @throws Exception
     * @autor:
     * @date2018年11月9日
     */
    @SuppressWarnings("rawtypes")
    private static List<Object> getExcelContent(Workbook book, String[] properties, Class obj, int startRow)
            throws Exception {
        // 初始化结果解
        List<Object> resultList = new ArrayList<Object>();
        Map<String, Method> methodMap = getObjectSetterMethod(obj);
        Map<String, Field> fieldMap = getObjectField(obj);
        for (int numSheet = 0; numSheet < book.getNumberOfSheets(); numSheet++) {
            Sheet sheet = book.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            // 一个row就相当于一个Object
            for (int numRow = startRow; numRow <= sheet.getLastRowNum(); numRow++) {
                Row row = sheet.getRow(numRow);
                if (row == null) {
                    continue;
                }
                Object object = getObject(row, properties, methodMap, fieldMap, obj);
                if(object != null){
                    resultList.add(object);
                }
            }
        }
        return resultList;
    }

    /**
     * 获取row的数据，利用反射机制构建Object对象
     *
     * @param row
     *            row对象
     * @param properties
     *            Object参考的属性
     * @param methodMap
     *            object对象的setter方法映射
     * @param fieldMap
     *            object对象的属性映射
     * @return
     * @throws Exception
     * @autor:
     * @date2018年11月9日
     */
    @SuppressWarnings("rawtypes")
    private static Object getObject(Row row, String[] properties, Map<String, Method> methodMap,
            Map<String, Field> fieldMap, Class obj) throws Exception {
        Object object = obj.newInstance();
        boolean blankFlag = true;
        for (int numCell = 0; numCell < row.getLastCellNum(); numCell++) {
            Cell cell = row.getCell(numCell);
            if (cell == null) {
                continue;
            }
            String cellValue = getValue(cell);
            if (StringUtils.isNotBlank(cellValue)){
                cellValue = cellValue.trim();
                blankFlag = false;
            }
            String property = properties[numCell].toLowerCase();
            // 该property在object对象中对应的属性
            Field field = fieldMap.get(property);
            // 该property在object对象中对应的setter方法
            Method method = methodMap.get(property);
            setObjectPropertyValue(object, field, method, cellValue);
        }
        if(blankFlag){
            return null;
        }
        return object;
    }

    /**
     * 根据指定属性的的setter方法给object对象设置值
     *
     * @param obj
     *            object对象
     * @param field
     *            object对象的属性
     * @param method
     *            object对象属性的相对应的方法
     * @param value
     *            需要设置的值
     * @throws Exception
     * @autor:
     * @date2018年11月10日
     */
    private static void setObjectPropertyValue(Object obj, Field field, Method method, String value) throws Exception {
        Object[] oo = new Object[1];

        String type = field.getType().getName();
        if (ExcelJavaTypeEnum.LANG_STRING.getType().equals(type)
                || ExcelJavaTypeEnum.LANG_LOWER_STRING.getType().equals(type)
                || ExcelJavaTypeEnum.STRING.getType().equals(type)
                || ExcelJavaTypeEnum.LOWER_STRING.getType().equals(type)) {
            oo[0] = value;
        } else if (ExcelJavaTypeEnum.LANG_INTEGER.getType().equals(type)
                || ExcelJavaTypeEnum.LANG_INT.getType().equals(type) || ExcelJavaTypeEnum.INTEGER.getType().equals(type)
                || ExcelJavaTypeEnum.INT.getType().equals(type)) {
            if (value.length() > 0) {
                oo[0] = Integer.valueOf(value);
            }
        } else if (ExcelJavaTypeEnum.LANG_FLOAT.getType().equals(type)
                || ExcelJavaTypeEnum.LANG_LOWER_FLOAT.getType().equals(type)
                || ExcelJavaTypeEnum.FLOAT.getType().equals(type)
                || ExcelJavaTypeEnum.LOWER_FLOAT.getType().equals(type)) {
            if (value.length() > 0) {
                oo[0] = Float.valueOf(value);
            }
        } else if (ExcelJavaTypeEnum.LANG_DOUBLE.getType().equals(type)
                || ExcelJavaTypeEnum.LANG_LOWER_DOUBLE.getType().equals(type)
                || ExcelJavaTypeEnum.DOUBLE.getType().equals(type)
                || ExcelJavaTypeEnum.LOWER_DOUBLE.getType().equals(type)) {
            if (value.length() > 0) {
                oo[0] = Double.valueOf(value);
            }
        } else if (ExcelJavaTypeEnum.MATH_BIGDECIMAL.getType().equals(type)
                || ExcelJavaTypeEnum.BIGDECIMAL.getType().equals(type)) {
            if (value.length() > 0) {
                oo[0] = new BigDecimal(value);
            }
        } else if (ExcelJavaTypeEnum.UTIL_DATE.getType().equals(type)
                || ExcelJavaTypeEnum.DATE.getType().equals(type)) {
            if (value.length() > 0) {
                // 当长度为19(yyyy-MM-dd HH24:mm:ss)或者为14(yyyyMMddHH24mmss)时Date格式转换为yyyyMMddHH24mmss
                if (value.length() == NINETEEN_DATE_LENGTH || value.length() == FOURTEEN_DATE_LENGTH) {
                    oo[0] = DateUtils.parse(value, DateFormatEnum.YYYYMMDDHHMMSS.getFormat());
                } else {
                    // 其余全部转换为yyyyMMdd格式
                    oo[0] = DateUtils.parse(value, DateFormatEnum.YYYYMMDD.getFormat());
                }
            }
        } else if (ExcelJavaTypeEnum.TIMESTAMP.getType().equals(type)) {
            if (value.length() > 0) {
                oo[0] = DateUtils.parse(value, DateFormatEnum.YYYYMMDDHHMMSS.getFormat());
            }
        } else if (ExcelJavaTypeEnum.LANG_BOOLEAN.getType().equals(type)
                || ExcelJavaTypeEnum.BOOLEAN.getType().equals(type)) {
            if (value.length() > 0) {
                oo[0] = Boolean.valueOf(value);
            }
        } else if (ExcelJavaTypeEnum.LANG_LONG.getType().equals(type)
                || ExcelJavaTypeEnum.LANG_LOWER_LONG.getType().equals(type)
                || ExcelJavaTypeEnum.LONG.getType().equals(type)
                || ExcelJavaTypeEnum.LOWER_LONG.getType().equals(type)) {
            if (value.length() > 0) {
                oo[0] = Long.valueOf(value);
            }
        }
        try {
            method.invoke(obj, oo);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static String getValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case NUMERIC:
            return NumberToTextConverter.toText(cell.getNumericCellValue());
        default:
            return String.valueOf(cell.getStringCellValue());
        }

    }

    /**
     * 获取object对象所有属性的Setter方法，并构建map对象，结构为Map<'field','method'>
     *
     * @param object
     *            object对象
     * @return
     * @autor:
     * @date2018年11月9日
     */
    @SuppressWarnings("rawtypes")
    private static Map<String, Method> getObjectSetterMethod(Class object) {
        // 获取object对象的所有属性
        Field[] fields = object.getDeclaredFields();
        // 获取object对象的所有方法
        Method[] methods = object.getDeclaredMethods();
        Map<String, Method> methodMap = new HashMap<String, Method>(100);
        for (Field field : fields) {
            String attri = field.getName();
            for (Method method : methods) {
                String meth = method.getName();
                // 匹配set方法
                if (meth != null && "set".equals(meth.substring(0, 3)) && Modifier.isPublic(method.getModifiers())
                        && ("set" + Character.toUpperCase(attri.charAt(0)) + attri.substring(1)).equals(meth)) {
                    // 将匹配的setter方法加入map对象中
                    methodMap.put(attri.toLowerCase(), method);
                    break;
                }
            }
        }

        return methodMap;
    }

    /**
     * 获取object对象的所有属性，并构建map对象，对象结果为Map<'field','field'>
     *
     * @param object
     *            object对象
     * @return
     * @autor:
     * @date2018年11月10日
     */
    @SuppressWarnings("rawtypes")
    private static Map<String, Field> getObjectField(Class object) {
        // 获取object对象的所有属性
        Field[] fields = object.getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<String, Field>(100);
        for (Field field : fields) {
            String attri = field.getName();
            fieldMap.put(attri.toLowerCase(), field);
        }
        return fieldMap;
    }
}
