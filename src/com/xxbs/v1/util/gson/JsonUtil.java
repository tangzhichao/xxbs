package com.xxbs.v1.util.gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.xxbs.v1.util.date.DateUtil;

public class JsonUtil {

	public static JSONArray toArray(Object obj){
		return toArray(obj, DateUtil.yyyy_MM_dd1);
	}
	public static JSONObject toObject(Object obj){
		return toObject(obj, DateUtil.yyyy_MM_dd1);
	}
	public static JSONArray toArray(Object obj, String pattern){
		return JSONArray.fromObject(obj, JsonUtil.getJsonConfig(pattern));
	}
	public static JSONObject toObject(Object obj, String pattern){
		return JSONObject.fromObject(obj, JsonUtil.getJsonConfig(pattern));
	}
    public static JsonConfig getJsonConfig(final String pattern){
    	JsonConfig jc = new JsonConfig();
    	JsonValueProcessor jsonValueProcessor= new JsonValueProcessor() {
     		public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
     			if(arg1 instanceof java.util.Date){
     				return DateUtil.format((java.util.Date)arg1,pattern);
     			}
     			return null;
     		}
     		public Object processArrayValue(Object arg0, JsonConfig arg1) {
     			if(arg0 instanceof java.util.Date){
     				return DateUtil.format((java.util.Date)arg0,pattern);
     			}
     			return null;
     		}
     	};
    	jc.registerJsonValueProcessor(java.util.Date.class,jsonValueProcessor);
    	jc.registerJsonValueProcessor(java.sql.Date.class,jsonValueProcessor);
    	jc.registerJsonValueProcessor(java.sql.Timestamp.class,jsonValueProcessor);
    	return jc;
    }
}
