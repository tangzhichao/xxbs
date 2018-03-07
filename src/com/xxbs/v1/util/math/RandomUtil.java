package com.xxbs.v1.util.math;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	public static String getRandomChar(int charCount) {
		String charValue = "";
	    for (int i = 0; i < charCount; i++){
		    char c = (char) (randomInt(0,26)+'a');
		    charValue += String.valueOf(c);
	    }
		return charValue;
	}

	public static String getRandomNum(int charCount) {
		String charValue = "";
	    for (int i = 0; i < charCount; i++){
		    char c = (char) (randomInt(0,10)+'0');
		    charValue += String.valueOf(c);
	    }
		return charValue;
	}
	
	public static int randomInt(int from, int to) {
		  Random r = new Random();
		  return from + r.nextInt(to - from);
	}

	public static  String getOrderNo(int length){//生成随机数字和字母
		
        String val = "";  
        Random random = new Random();  
        for(int i = 0; i < length; i++) {  //参数length，表示生成几位随机数  
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            if( "char".equalsIgnoreCase(charOrNum) ) {  //输出字母还是数字  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
	}
	
	
	/**
	 * getPK,获得数据库使用的一个long型唯一主键 16位，同一微秒内3000个不会重复
	 * 
	 * @return long
	 */
	private static long[] ls = new long[3000];
	private static int li = 0;

	private static long getpk() {
		String a = (String.valueOf(System.currentTimeMillis())).substring(3, 13);
		String d = (String.valueOf(Math.random())).substring(2, 8);
		return Long.parseLong(a + d);
	}
	
	public synchronized static long getLongKey() {
		long lo = getpk();
		for (int i = 0; i < 3000; i++) {
			long lt = ls[i];
			if (lt == lo) {
				lo = getLongKey();
				break;
			}
		}
		ls[li] = lo;
		li++;
		if (li == 3000) {
			li = 0;
		}
		return lo;
	}

	public static String randomNumberAndFormat(int bound) {
		Random random = new Random();
		int len=((bound-1)+"").length();
		return String.format("%0"+len+"d", random.nextInt(bound));
	}
	
	public static String randomNumberByLength(int length) {
		Random random = new Random();
		String str = "";
		for (int i = 0; i < length; i++) {
			str = str + random.nextInt(10);
		}
		return str;
	}
}