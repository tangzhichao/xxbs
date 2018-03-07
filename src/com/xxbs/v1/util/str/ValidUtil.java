package com.xxbs.v1.util.str;

import java.util.Date;
import java.util.regex.Pattern;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.date.DateUtil;

public class ValidUtil {
	
	public static boolean isPassword(String str) {
		return str != null ? str.matches("^\\s*[^\\s\u4e00-\u9fa5]{6,16}\\s*$") : false;
	}
	
	public static boolean isChinese(String str){
		return str != null ? str.matches("[\\u4e00-\\u9fa5]+") : false;
	}
	
	public static boolean isChineseOrCharOrNumber(String str){
		return str != null ? str.matches("([\\u4e00-\\u9fa5]*)|([a-zA-Z]*)|([0-9]*)") : false;
	}
	
	public static boolean isPostCode(String str){
		return str != null ? str.matches("[1-9]\\d{5}(?!\\d)") : false;
	}

	public static boolean isQQ(String str){
		return str != null ? str.matches("[1-9](\\d){4,10}") : false;
	}
	
	public static boolean isIp(String str) {
		return str != null ? str.matches("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
				"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$") : false;
	}
	
	public static boolean isEmail(String str) {
		return str != null ? str.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$") : false;
	}
	public static boolean isMobile(String str) {
		return str != null ? str.matches("^[1][0-9][0-9]{9}$") : false;
	}
	public static boolean isTel(String str) {
		return str != null ? (str.length() > 9?(str.matches("^[0][1-9]{2,3}-[0-9]{5,10}$")):(str.matches("^[1-9]{1}[0-9]{5,8}$"))) : false;
	}
	public static boolean isPercent(String str) {//%形式的百分比
		return str != null ? str.endsWith("%") && isNumber(str.substring(0, str.length() - 1)) : false;
	}

	public static boolean isNumber(String str) {//数字
		return str != null ? str.matches("^[-+]?(([0-9]+)((([.]{0})([0-9]*))|(([.]{1})([0-9]+))))$") : false;
	}

	public static boolean isInteger(String str) {//整数
		return str != null ? str.matches("^[-+]?[0-9]+$") : false;
	}

	public static boolean isPositiveInteger(String str) {//正整数
		return str != null ? str.matches("^[+]?[0-9]+$") : false;
	}

	public static Double getDouble(Object obj, Double defaultValue) {
		return ValidUtil.isNumber(Utils.toStringTrim(obj)) ? Double.parseDouble(Utils.toStringTrim(obj)) : defaultValue;
	}
	public static Integer getInteger(Object obj, Integer defaultValue) {
		return ValidUtil.isInteger(Utils.toStringTrim(obj)) ? Integer.parseInt(Utils.toStringTrim(obj)) : defaultValue;
	}
	public static Integer getPositiveInteger(Object obj, Integer defaultValue) {
		return ValidUtil.isPositiveInteger(Utils.toStringTrim(obj)) ? Integer.parseInt(Utils.toStringTrim(obj)) : defaultValue;
	}
	public static Boolean getBoolean(Object obj, Boolean defaultValue) {
		return ValidUtil.isInteger(Utils.toStringTrim(obj)) ? (Integer.parseInt(Utils.toStringTrim(obj)) == 0 ? Boolean.FALSE : Boolean.TRUE) : (obj == null ? defaultValue : Boolean.parseBoolean(Utils.toStringTrim(obj)));
	}
	public static Date getDate(Object obj, Date defaultValue) {
		Date date = DateUtil.parseD(Utils.toStringTrim(obj));
		return date != null ? date : defaultValue;
	}
	public static Date getDatetime(Object obj, Date defaultValue) {
		Date date = DateUtil.parse(Utils.toStringTrim(obj),DateUtil.yyyy_MM_dd_HH_mm_ss1);
		return date != null ? date : defaultValue;
	}
	public static String getSortType(String str, String defaultValue) {
		return "asc".equalsIgnoreCase(str) || "desc".equalsIgnoreCase(str) ? str : defaultValue;
	}
}
