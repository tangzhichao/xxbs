package com.xxbs.v1.util.orm;

public class OrmUtil {

	public static String toColumnName(String fieldName){
		StringBuffer sb = new StringBuffer(fieldName);
		char[] charArray = fieldName.toCharArray();
		int increment=0;
		boolean previousIsUpper=false;
		for (int i = 0; i < charArray.length; i++) {
			boolean isUpper = Character.isUpperCase(charArray[i]);
			if(i!=0&&isUpper&&!previousIsUpper&&i!=charArray.length-1){
				sb.deleteCharAt(i+increment);
				sb.insert(i+increment, "_"+Character.toLowerCase(charArray[i]));
				increment+=1;
			}
			previousIsUpper=isUpper;
		}
		return sb.toString();
	}

}
