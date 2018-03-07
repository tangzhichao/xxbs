package com.xxbs.v1.util.gson;

import java.lang.reflect.Type;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonComm {
    //private static final Logger LOGGER = LoggerFactory.getLogger(GsonComm.class);
    /** 空的 {@code JSON} 数据 - <code>"{}"</code>。 */
    public static final String EMPTY_JSON = "{}";
    /** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
    public static final String EMPTY_JSON_ARRAY = "[]";

    /** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static <T> T fromJson(String json, Type typeOfT) {
    	return fromJson(json, typeOfT, DEFAULT_DATE_PATTERN);
    }
    /**
   	 * 将json字符串转换成对应实体
   	 * @param data 需要转换的数据
   	 * @param beanClass 需要转换的class
   	 * @return
   	 */
   	@SuppressWarnings("unchecked")
   	public static <T> T fromJson2(Object data,Class<?> beanClass){
   		
   		try {
   			JSONObject jsonObject = JSONObject.fromObject(data);
   			return (T)JSONObject.toBean(jsonObject, beanClass);
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		
   		return null;
   	}
    public static <T> T fromJson(String json, Type typeOfT, String datePattern) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, typeOfT);
        } catch (Exception ex) {
            return null;
        }
    }
    /**
   	 * 将json字符串转换成list<实体> 
   	 * @param data 需要转换的数据
   	 * @param beanClass	需要转换的class
   	 * @return
   	 */
   	@SuppressWarnings("unchecked")
	public static <T> T fromJsonArray(Object data,Class<?> beanClass){
   		try {
   			JSONArray jsonArray = JSONArray.fromObject(data);
   			return (T)JSONArray.toCollection(jsonArray, beanClass);
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		return null;
   	 }
    public static String toJsonExpose(Object src, Type typeOfSrc) {
    	return toJson(src, typeOfSrc, true);
    }
    
    public static String toJson(Object src, Type typeOfSrc, boolean bExpose) {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.setDateFormat(DEFAULT_DATE_PATTERN);
        Gson gson = null;
        
        if (!bExpose) {
        	gson = builder.create();
        } else {
            gson = builder.excludeFieldsWithoutExposeAnnotation().create();        	
        }
        return gson.toJson(src, typeOfSrc);
    }
//	public static void main(String[] args) {
//		String head[] = new String[]{"http://www.baidu.com?id=01"};
//		Object data[] = new Object[]{"http://www.baidu.com?id=02"};
//		GsonEbRst rst = GsonEbUtils.toGsonEbRst(head, data);
//		String ss = rst.objectToJsonStr();
//		System.out.println(ss);
//	}

}
