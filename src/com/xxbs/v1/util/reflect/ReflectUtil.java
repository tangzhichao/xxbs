package com.xxbs.v1.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class ReflectUtil {


	/**
	 * 通过反射调用这个方法得到返回值
	 */
	public static Object invoke(Object object, String methodName) {
		if (object != null && methodName != null) {
			try {
				Class clazz = object.getClass();
				Method method = clazz.getDeclaredMethod(methodName);
				method.setAccessible(true);
				Object result = method.invoke(object);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
     * 通过反射获取属性值
     */
    public static Object get(Object object, String propertyName) {
        if (object != null && propertyName != null) {
            return get(object, object.getClass(), propertyName);
        }
        return null;
    }
    public static Object get(Object object,Class clazz, String propertyName) {
        if (object != null&&clazz!=null && propertyName != null&&clazz!=Object.class) {
            try {
                Field field = clazz.getDeclaredField(propertyName);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException e) {
                return get(object, clazz.getSuperclass(), propertyName);
            }catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    /**
     * 通过反射设置属性值
     */
    public static boolean set(Object object, String propertyName,Object propertyValue) {
        if (object != null && propertyName != null) {
            return set(object, object.getClass(), propertyName,propertyValue);
        }
        return false;
    }
    public static boolean set(Object object,Class clazz, String propertyName,Object propertyValue) {
        if (object != null&&clazz!=null && propertyName != null&&clazz!=Object.class) {
            try {
                Field field = clazz.getDeclaredField(propertyName);
                field.setAccessible(true);
                field.set(object, propertyValue);
                return true;
            } catch (NoSuchFieldException e) {
                return set(object, clazz.getSuperclass(), propertyName,propertyValue);
            }catch (Exception e) {
                return false;
            }
        }
        return false;
    }
    
    
    public static void setFieldByParam(HttpServletRequest request,Object param){
		Map<String, String[]> map = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = map.entrySet();
//		logger.info("=--------------"+entrySet.toString());
		for (Entry<String, String[]> entry : entrySet) {
//			logger.info(entry.getKey()+":");
			Class clazz=param.getClass();
			try {
				String fieldName;
				int pointIndex=entry.getKey().indexOf(".");
				if (pointIndex>0) {
					fieldName=entry.getKey().substring(0, pointIndex);
					String ffName=entry.getKey().substring(pointIndex+1);
					
					Field field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					Class<?> type = field.getType();
					Object ffInstance = type.newInstance();
					field.set(param,ffInstance);
					
					Field ff = type.getDeclaredField(ffName);
					ff.setAccessible(true);
					Class<?> ffType = ff.getType();
					if (ffType!=String.class) {
						ff.set(ffInstance,Integer.parseInt(request.getParameter(entry.getKey())));
					}else{
						ff.set(ffInstance,request.getParameter(entry.getKey()));
					}
				}else{
					fieldName=entry.getKey();
					
					Field field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					Class<?> type = field.getType();
					if (type!=String.class) {
						field.set(param,Integer.parseInt(request.getParameter(entry.getKey())));
					}else{
						field.set(param,request.getParameter(entry.getKey()));
					}
					field.set(param,request.getParameter(entry.getKey()));
				}
				
			} catch (Exception e) {
//				e.printStackTrace();
				Class paraent=clazz.getSuperclass();
				try {
					Field field = paraent.getDeclaredField(entry.getKey());
					field.setAccessible(true);
					Class<?> type = field.getType();
					if (type!=String.class) {
						field.set(param,Integer.parseInt(request.getParameter(entry.getKey())));
					}else{
						field.set(param,request.getParameter(entry.getKey()));
					}
				}catch(Exception e2){
//					e2.printStackTrace();
				}
			}
		}
	}
}
