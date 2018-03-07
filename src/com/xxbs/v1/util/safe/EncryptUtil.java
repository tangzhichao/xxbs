package com.xxbs.v1.util.safe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * 加密
 */
public class EncryptUtil {
	
	/**
	 * SHA1 加密
	 * @param t
	 * @return
	 */
	public static String SHA1(String t) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(t.getBytes("UTF-8"));
			byte messageDigest[] = digest.digest();
			Formatter formatter = new Formatter();
	        for (byte b : messageDigest){
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * SHA 加密
	 * @param t
	 * @return
	 */
	public static String SHA(String t) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA");
			digest.update(t.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * MD5 加密
	 */
	public static String md5(String t) {
		String hexStr = "";
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(t.getBytes("UTF-8"));
			byte[] md = mdInst.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				String shaHex = Integer.toHexString(md[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			hexStr = hexString.toString();
		} catch (Exception e) {
		}
		return hexStr;
	}
	
	/**
	 * md5加密
	 */
	public static String MD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return result;
	}
	
	/**
	 * BASE64 
	 * @param t
	 * @return
	 */
	public static String base64(String t){
		try {
			return new BASE64Encoder().encode(t.getBytes("utf-8")); 
		} catch (Exception e) {
		}
		return "";
	}
	
	
	public static String des(String t, String k){
		
		return t;
	}
	
	
	/**
	 * 3DES 加密
	 * @param t 明文
	 * @param k 密钥
	 * @return
	 */
	public static String des3(String t, String k){
		try{
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			SecretKey key = new SecretKeySpec(k.getBytes(), "DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherByte = cipher.doFinal(t.getBytes("UTF-8"));
			BASE64Encoder base64Encoder = new BASE64Encoder();
			return base64Encoder.encodeBuffer(cipherByte).replaceAll("\r", "").replaceAll("\n", "");
		}catch (Exception e) {
		}
		return "";
	}
}
