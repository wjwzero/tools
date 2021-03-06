package com.tools.common.excel.anno;


import com.tools.common.excel.convert.ValueConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类属性，用做excel表头标记
 *
 * @author zhengjunwei
 * @date 2018/3/13 10:23
 **/
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

    String name() default "";

    int sort() default 0;

    String method() default "";
    /**
     * 值转换器，输入当前对象字段值，输出转换后的值
     * 
     * @return
     * @author zhengjunwei
     * @date 2019年1月17日 下午3:33:16
     */
    Class<? extends ValueConverter<?>> covert() default ValueConverter.NONE.class;

}
