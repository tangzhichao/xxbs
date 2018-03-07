package com.xxbs.v1.util;

import java.util.Collection;
import java.util.Map;

/**
 * 通用工具类
 * @author tang
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class Utils {
	
	public static <K,V> V get(Map<K, V> map,String key){
		if (map.containsKey(key)) {
			return map.get(key);
		}
		return null;
	}

	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	public static String toStringTrim(Object obj) {
		return obj == null ? "" : obj.toString().trim();
	}

	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isEmpty(Map map) {
		return map == null || map.isEmpty();
	}
	public static  <K,V>  boolean isEmpty(Map<K, V> map,String key) {
		return map == null || map.isEmpty()||!map.containsKey(key)||isEmptyTrim(map.get(key));
	}

	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}
	public static boolean isEmptyTrim(String string) {
		return string == null || string.trim().isEmpty();
	}

	public static boolean isEmpty(Object obj) {
		return obj == null || toString(obj).isEmpty();
	}
	public static boolean isEmptyTrim(Object obj) {
		return obj == null || toString(obj).trim().isEmpty();
	}

	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}
	
	public static String getWebRootPath(){
		return System.getProperty("xxbs.root");
	}
}
