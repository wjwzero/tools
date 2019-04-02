/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
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
 * 2019/1/8    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util.http;

import com.tools.base.basic.model.IBaseModel;
import com.tools.base.util.ClassUtils;
import com.tools.base.util.StringUtils;
import com.tools.base.util.date.DateUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 对象转Http请求Map，非JSON格式的Request Body。性能未优化
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/1/8 20:49
 */
public class RequestParameterUtils {
    /**
     * 适配器
     */
    private static final Map<Class<?>, Function<Object, String>> ADAPTERS = new HashMap<>();
    /**
     * 适配器
     */
    private static final Map<Class<?>, BiFunction<String, Object, Map<String, String>>> MULTI_ADAPTERS =
            new HashMap<>();

    static {
        addAdapter(Long.class, StringUtils::toString);
        addAdapter(Boolean.class, StringUtils::toString);
        addAdapter(Byte.class, StringUtils::toString);
        addAdapter(Character.class, StringUtils::toString);
        addAdapter(Double.class, StringUtils::toString);
        addAdapter(Integer.class, StringUtils::toString);
        addAdapter(Float.class, StringUtils::toString);
        addAdapter(Short.class, StringUtils::toString);
        addAdapter(String.class, StringUtils::toString);
        addAdapter(BigDecimal.class, StringUtils::toString);
        addAdapter(LocalDateTime.class, obj -> DateUtils.format((LocalDateTime) obj));
        addAdapter(LocalDate.class, obj -> DateUtils.format((LocalDate) obj));
        addAdapter(LocalTime.class, obj -> DateUtils.format((LocalTime) obj));
        addAdapter(Date.class, obj -> DateUtils.format((Date) obj));
        addMultiAdapter(IBaseModel.class, (prefix, obj) -> RequestParameterUtils.parseModel(prefix, (IBaseModel) obj));
    }

    /**
     * 添加适配器
     *
     * @param tClass   Class类
     * @param function 函数式接口
     * @author zhangduanfeng
     * @date 2019/1/6 2:01
     */
    static void addAdapter(Class<?> tClass, Function<Object, String> function) {
        ADAPTERS.put(tClass, function);
    }

    /**
     * 添加适配器
     *
     * @param tClass   Class类
     * @param function 函数式接口
     * @author zhangduanfeng
     * @date 2019/1/6 2:01
     */
    static void addMultiAdapter(Class<?> tClass, BiFunction<String, Object, Map<String, String>> function) {
        MULTI_ADAPTERS.put(tClass, function);
    }

    /**
     * 获取适配器
     *
     * @param fieldClass 与map中key相同或者是子类的
     * @return java.util.function.Function<java.lang.Object                                                                                                                               ,                                                                                                                               java.lang.String>
     * @author zhangduanfeng
     * @date 2019/1/6 2:02
     */
    private static Function<Object, String> getAdapter(Class<?> fieldClass) {
        Assert.notNull(fieldClass);
        Class<?> key;
        for (Map.Entry<Class<?>, Function<Object, String>> entry : ADAPTERS.entrySet()) {
            key = entry.getKey();
            if (key == fieldClass || key.isAssignableFrom(fieldClass)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 获取适配器
     *
     * @param fieldClass 与map中key相同或者是子类的
     * @return java.util.function.BiFunction<java.lang.String                                                                                                                               ,                                                                                                                               java.lang.Object                                                                                                                               ,                                                                                                                               java.util.Map                                                                                                                               <                                                                                                                               java.lang.String                                                                                                                               ,                                                                                                                               java.lang.String>>
     * @author zhangduanfeng
     * @date 2019/1/6 2:01
     */
    private static BiFunction<String, Object, Map<String, String>> getMultiAdapter(Class<?> fieldClass) {
        Assert.notNull(fieldClass);
        Class<?> key;
        for (Map.Entry<Class<?>, BiFunction<String, Object, Map<String, String>>> entry : MULTI_ADAPTERS.entrySet()) {
            key = entry.getKey();
            if (key == fieldClass || key.isAssignableFrom(fieldClass)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 解析Object对象为请求参数Map
     *
     * @param object 任意对象
     * @return java.util.Map&lt;java.lang.String,java.lang.String&gt;
     * @author zhangduanfeng
     * @date 2019/1/18 13:52
     */
    public static Map<String, String> parse(Object object) {
        return parse(StringUtils.EMPTY, object);
    }

    /**
     * 解析Object对象为请求参数Map
     *
     * @param prefix 加前缀，如果有前缀，则会生成prefix.xxx
     * @param object 任意对象
     * @return java.util.Map&lt;java.lang.String,java.lang.String&gt;
     * @author zhangduanfeng
     * @date 2019/1/18 13:52
     */
    public static Map<String, String> parse(String prefix, Object object) {
        Map<String, String> parameters = new LinkedHashMap<>();
        parse(parameters, prefix, object);
        return parameters;
    }

    /**
     * 解析Object对象为请求参数Map parameters
     *
     * @param parameters 传入的Map
     * @param prefix     加前缀，如果有前缀，则会生成prefix.xxx
     * @param object     任意对象
     * @author zhangduanfeng
     * @date 2019/1/18 13:53
     */
    public static void parse(Map<String, String> parameters, String prefix, Object object) {
        Class<?> objClass = object.getClass();
        // 如果已有处理器，使用处理器
        Function<Object, String> function = getAdapter(objClass);
        if (function != null) {
            parameters.put(prefix, function.apply(object));
            return;
        }
        // 如果已有处理器，使用处理器
        BiFunction<String, Object, Map<String, String>> biFunction = getMultiAdapter(objClass);
        if (biFunction != null) {
            parameters.putAll(biFunction.apply(prefix, object));
            return;
        }
        // 如果是数组
        if (objClass.isArray()) {
            parameters.putAll(parseArray(prefix, object));
            return;
        }

        //如果是集合
        if (Collection.class.isAssignableFrom(objClass)) {
            parameters.putAll(parseCollection(prefix, (Collection) object));
            return;
        }

        //如果是map
        if (Map.class.isAssignableFrom(objClass)) {
            parameters.putAll(parseMap(prefix, (Map) object));
            return;
        }
        parameters.put(prefix, StringUtils.toString(object));
    }

    /**
     * 将{@link IBaseModel}转换为请求需要的Map
     *
     * @param prefix 加前缀，如果有前缀，则会生成prefix.xxx
     * @param model  {@link IBaseModel}
     * @return java.util.Map&lt;java.lang.String,java.lang.String&gt;
     * @author zhangduanfeng
     * @date 2019/1/17 19:28
     */
    public static Map<String, String> parseModel(String prefix, IBaseModel model) {
        Map<String, String> parameters = new HashMap<>(0);
        String name = buildPrefix(prefix);
        List<Field> fields = ClassUtils.listFiled(model.getClass());
        try {
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(model);

                if (value == null) {
                    continue;
                }
                parse(parameters, name + field.getName(), value);
            }
        } catch (IllegalAccessException ignored) {
        }
        return parameters;
    }


    /**
     * 将数组转换为Map，如果是基本类型、字符串类型或无适配器的类型，就会转成一个字符串
     *
     * @param prefix 加前缀，如果有前缀，则会生成prefix.xxx
     * @param array  源数组
     * @return java.util.Map&lt;java.lang.String,java.lang.String&gt;
     * @author zhangduanfeng
     * @date 2019/1/18 13:53
     */
    public static Map<String, String> parseArray(String prefix, Object array) {
        Map<String, String> parameters = new HashMap<>(0);
        if (array == null) {
            return parameters;
        }
        Class<?> arrayClass = array.getClass();
        if (!arrayClass.isArray()) {
            return parameters;
        }
        Class<?> arrayComponentType = arrayClass.getComponentType();
        Function<Object, String> function = getAdapter(arrayComponentType);
        BiFunction<String, Object, Map<String, String>> biFunction = getMultiAdapter(arrayComponentType);
        String name = prefix;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Array.getLength(array); i++) {

            if (biFunction != null) {
                if (StringUtils.isNotEmpty(prefix)) {
                    name = prefix + "[" + i + "]";
                }
                parameters.putAll(biFunction.apply(name, Array.get(array, i)));
                continue;
            }


            if (i > 0) {
                sb.append(",");
            }
            if (function != null) {
                sb.append(function.apply(Array.get(array, i)));
            } else {
                sb.append(StringUtils.toString(Array.get(array, i)));
            }
        }
        if (sb.length() > 0) {
            parameters.put(prefix, sb.toString());
        }
        return parameters;
    }

    /**
     * 将数组转换为Map，如果是基本类型、字符串类型或无适配器的类型，就会转成一个字符串
     *
     * @param prefix     加前缀，如果有前缀，则会生成prefix.xxx
     * @param collection 源集合
     * @return java.util.Map&lt;java.lang.String,java.lang.String&gt;
     * @author zhangduanfeng
     * @date 2019/1/18 13:57
     */
    public static Map<String, String> parseCollection(String prefix, Collection collection) {
        Map<String, String> parameters = new HashMap<>(0);
        if (collection.size() < 1) {
            return parameters;
        }
        Function<Object, String> function;
        BiFunction<String, Object, Map<String, String>> biFunction;

        StringBuilder sb = null;
        int i = 0;
        for (Object obj : collection) {
            biFunction = getMultiAdapter(obj.getClass());

            if (biFunction != null) {
                String name = prefix;
                if (StringUtils.isNotEmpty(prefix)) {
                    name = prefix + "[" + i + "]";
                }
                parameters.putAll(biFunction.apply(name, obj));
            } else {
                if (sb == null) {
                    sb = new StringBuilder();
                }

                if (i > 0) {
                    sb.append(",");
                }

                function = getAdapter(obj.getClass());
                if (function != null) {
                    sb.append(function.apply(obj));
                } else {
                    sb.append(StringUtils.toString(obj));
                }
            }
            i++;
        }
        if (sb != null && sb.length() > 0) {
            parameters.put(prefix, sb.toString());
        }
        return parameters;
    }

    /**
     * @param prefix 加前缀，如果有前缀，则会生成prefix.xxx
     * @param map    源Map
     * @return java.util.Map&lt;java.lang.String,java.lang.String&gt;
     * @author zhangduanfeng
     * @date 2019/1/18 13:57
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseMap(String prefix, Map map) {
        Map<String, String> parameters = new HashMap<>(0);
        if (map.size() < 1) {
            return parameters;
        }
        String name = buildPrefix(prefix);
        map.forEach((k, v) -> parameters.putAll(parse(name + k, v)));
        return parameters;
    }

    /**
     * 生成前缀，带.
     *
     * @param prefix 加前缀，如果有前缀，则会生成prefix.xxx
     * @return java.lang.String
     * @author zhangduanfeng
     * @date 2019/1/18 13:57
     */
    private static String buildPrefix(String prefix) {
        if (StringUtils.isBlank(prefix)) {
            prefix = StringUtils.EMPTY;
        } else if (!prefix.endsWith(StringUtils.PERIOD)) {
            prefix = prefix.trim() + StringUtils.PERIOD;
        } else {
            prefix = prefix.trim();
        }
        return prefix;
    }
}
