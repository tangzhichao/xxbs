package com.xxbs.v1.util.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.xxbs.v1.util.Utils;

/**
 * @author tang
 */
public class DateUtil {
	
	public final static int startYear = 1980;
	
	public final static String yyyy_MM1 = "yyyy-MM";
	public final static String yyyy_MM_dd1 = "yyyy-MM-dd";
	public final static String yyyy_MM_dd_HH1 = "yyyy-MM-dd HH";
	public final static String yyyy_MM_dd_HH_mm1 = "yyyy-MM-dd HH:mm";
	public final static String yyyy_MM_dd_HH_mm_ss1 = "yyyy-MM-dd HH:mm:ss";
	
	public final static String yyyy_MM2 = "yyyy/MM";
	public final static String yyyy_MM_dd2 = "yyyy/MM/dd";
	public final static String yyyy_MM_dd_HH2 = "yyyy/MM/dd HH";
	public final static String yyyy_MM_dd_HH_mm2 = "yyyy/MM/dd HH:mm";
	public final static String yyyy_MM_dd_HH_mm_ss2 = "yyyy/MM/dd HH:mm:ss";
	
	public final static String yyyy_MM3 = "yyyy年MM月";
	public final static String yyyy_MM_dd3 = "yyyy年MM月dd日";
	public final static String yyyy_MM_dd_HH3 = "yyyy年MM月dd日 HH时";
	public final static String yyyy_MM_dd_HH_mm3 = "yyyy年MM月dd日 HH时mm分";
	public final static String yyyy_MM_dd_HH_mm_ss3 = "yyyy年MM月dd日 HH时mm分ss秒";
	
	public final static String yyyyMM = "yyyyMM";
	public final static String yyyyMMdd = "yyyyMMdd";
	public final static String yyyyMMddHH = "yyyyMMddHH";
	public final static String yyyyMMddHHmm = "yyyyMMddHHmm";
	public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	public final static String dd_HH_mm1 = "dd HH:mm";
	public final static String dd_HH_mm_ss1 = "dd HH:mm:ss";
	
	public final static String dd_HH_mm3 = "dd日HH时mm分";
	public final static String dd_HH_mm_ss3 = "dd日HH时mm分ss秒";
	
	public final static String HH_mm1 = "HH:mm";
	public final static String HH_mm_ss1 = "HH:mm:ss";
	
	public final static String HH_mm3 = "HH时mm分";
	public final static String HH_mm_ss3 = "HH时mm分ss秒";
	
	public final static String HHmm = "HHmm";
	public final static String HHmmss = "HHmmss";
	public final static String ddHHmm = "ddHHmm";
	public final static String ddHHmmss = "ddHHmmss";

	public static Date parse() {
		return parse(null, null);
    }
	public static Date parseD(String dateStr) {
		return parse(dateStr, null);
	}
	public static Date parseP(String pattern) {
		return parse(null, pattern);
	}
	public static Date parse(String dateStr, String pattern) {
		if (Utils.isEmptyTrim(dateStr)) {
			return null;
		}
		if (Utils.isEmptyTrim(pattern)) {
			pattern = yyyy_MM_dd1;
		}
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static String format(){
		return format(new Date(), yyyy_MM_dd1);
	}
	
	public static String format(String pattern){
		return format(new Date(), pattern);
	}
	public static String format(Date date) {
		return format(date, yyyy_MM_dd1);
	}
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
        if (pattern == null) {
        	pattern = yyyy_MM_dd1;
        }
        try {
        	return new SimpleDateFormat(pattern).format(date);
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return null;
        }
    }
	public static Date trimDate(){
		return trimDate(new Date());
	}
	public static Date trimDate(Date date){
		return parseD(format(date));
	}
	
	public static Timestamp getTimestamp(){
		return new Timestamp(getMillis());
	}
	
	public static long getMillis(){
		return System.currentTimeMillis();
	}
	public static Calendar getCalendar(){
		return  Calendar.getInstance();
	}
	public static Calendar getCalendar(Date date){
		Calendar c=getCalendar();
		c.setTime(date);
		return c;
	}
	public static int getYear(){
		return getCalendar().get(Calendar.YEAR);
	}
	public static int getMonth(){
		return getCalendar().get(Calendar.MONTH)+ 1;
	}
	public static int getDayOfMonth(){
		return getCalendar().get(Calendar.DAY_OF_MONTH);
	}
	public static int getMaxDayOfMonth(){//获取当前月份最大天数
		return getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	public static int getDayOfWeek(){//星期1是2,星期6是7，星期日是1
		return getCalendar().get(Calendar.DAY_OF_WEEK);
	}
	public static int getDayOfWeek2(){//星期1是1,星期6是6，星期日是7
		int d=getCalendar().get(Calendar.DAY_OF_WEEK)-1;
		return d==0?7:d;
	}
	public static int getDayOfYear(){
		return getCalendar().get(Calendar.DAY_OF_YEAR);
	}
	public static int getWeekOfYear(){
		return getCalendar().get(Calendar.WEEK_OF_YEAR);
	}
	public static int getYear(Date date){
		return getCalendar(date).get(Calendar.YEAR);
	}
	public static int getMonth(Date date){
		return getCalendar(date).get(Calendar.MONTH)+ 1;
	}
	public static int getDayOfMonth(Date date){
		return getCalendar(date).get(Calendar.DAY_OF_MONTH);
	}
	public static int getMaxDayOfMonth(Date date){//获取当前月份最大天数
		return getCalendar(date).getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	public static int getDayOfWeek(Date date){//星期1是2,星期6是7，星期日是1
		return getCalendar(date).get(Calendar.DAY_OF_WEEK);
	}
	public static int getDayOfWeek2(Date date){//星期1是1,星期6是6，星期日是7
		int d=getCalendar(date).get(Calendar.DAY_OF_WEEK)-1;
		return d==0?7:d;
	}
	public static int getDayOfYear(Date date){
		return getCalendar(date).get(Calendar.DAY_OF_YEAR);
	}
	public static int getWeekOfYear(Date date){
		return getCalendar(date).get(Calendar.WEEK_OF_YEAR);
	}
}
