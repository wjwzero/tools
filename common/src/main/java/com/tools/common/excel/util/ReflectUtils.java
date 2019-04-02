package com.tools.common.excel.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName ReflectUtils.java
 * @Description:
 *
 * @Date 2017年12月27日 下午4:32:34
 * @author zhangduanfeng
 * @version 1.0
 */
public class ReflectUtils {
	public static Set<Field> getFields(Class<?> cls) {
		Set<Field> results = new HashSet<>();
		Field[] fields = cls.getDeclaredFields();
		results.addAll(Arrays.asList(fields));
		if (cls.getSuperclass() != Object.class) {
			results.addAll(getFields(cls.getSuperclass()));
		}
		return results;
	}

	/**
	 * 从类Class中获取指定名称的属性
	 * 
	 * @Title: getField
	 * @Description:
	 * @param cls
	 * @param fieldName
	 * @return
	 * @author zhangduanfeng
	 * @date 2017年12月27日 下午4:31:46
	 */
	public static Field getField(Class<?> cls, String fieldName) {
		Field field = null;
		try {
			field = cls.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		if (field == null && cls.getSuperclass() != Object.class) {
			field = getField(cls.getSuperclass(), fieldName);
		}
		return field;
	}

	public static Object getValue(Object obj, String fieldName) {
		Field field = getField(obj.getClass(), fieldName);
		if (field != null) {
			try {
				PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, obj.getClass());
				return descriptor.getReadMethod().invoke(obj);
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static <T> T newInstance(Class<T> tClass) {
		if (tClass == null || Modifier.isAbstract(tClass.getModifiers()) || Modifier.isInterface(tClass.getModifiers())) {
			return null;
		}
		try {
			return tClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isFinal(Field field) {
		return Modifier.isFinal(field.getModifiers());
	}

	public static boolean isStatic(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

	public static boolean isTransient(Field field) {
		return Modifier.isTransient(field.getModifiers());
	}
}