package com.xxbs.v1.tag;

import java.lang.reflect.Method;

public class StaticInvokeUtil {

	public static Object invoke(String className, String methodName, Class[] types, Object[] params) {
		try {
			Class<?> clazz = Class.forName(className);
			Method method = clazz.getMethod(methodName, types);
			return method.invoke(null, params);
		} catch (Exception e) {
			return null;
		}
	}
	public static Object invoke(String className, String methodName,Object param) {
		try {
			Class<?> clazz = Class.forName(className);
			Method method = clazz.getMethod(methodName, param.getClass());
			return method.invoke(null, param);
		} catch (Exception e) {
			return null;
		}
	}
}
