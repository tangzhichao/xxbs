package com.xxbs.v1.util.safe;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DexUtil3 {
	
	private static String Algorithm = "DESede";

	/**
	 * 3DES 加密 ECB模式
	 * @param plaintext 明文
	 * @param strKey 密钥（24位）
	 * @return
	 * @throws Exception
	 */
	public static String Des3EncryptECB(String plaintext, String strKey){
		try{
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			SecretKey key = new SecretKeySpec(strKey.getBytes(), Algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherByte = cipher.doFinal(plaintext.getBytes("UTF-8"));
			BASE64Encoder base64Encoder = new BASE64Encoder();
			return base64Encoder.encodeBuffer(cipherByte).replaceAll("\r", "").replaceAll("\n", "");
		}catch (Exception e) {
			
		}
		return "";
	}
	
	/**
	 * 3DES 解密 ECB模式
	 * @param ciphertext 密文
	 * @param strKey 密钥（24位）
	 * @return
	 * @throws Exception
	 */
	public static String Des3DecryptECB(String ciphertext, String strKey){
		try{
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			SecretKey key = new SecretKeySpec(strKey.getBytes(), Algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] plainByte = cipher.doFinal(base64Decoder.decodeBuffer(ciphertext));
			return new String(plainByte, "UTF-8");
		}catch (Exception e) {
		}
		return "";
	}
	
	
	
	/**
	 * 3DES 加密 CBC模式
	 * @param plaintext 明文
	 * @param strKey 密钥（24位）
	 * @param strIv 向量（8位）
	 * @return
	 * @throws Exception
	 */
	public static String Des3EncryptCBC(String plaintext, String strKey, String strIv){
		try{
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKey key = new SecretKeySpec(strKey.getBytes(), Algorithm);
			IvParameterSpec iv = new IvParameterSpec(strIv.getBytes("UTF-8"));
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] cipherByte = cipher.doFinal(plaintext.getBytes("UTF-8"));
			BASE64Encoder base64Encoder = new BASE64Encoder();
			return base64Encoder.encodeBuffer(cipherByte).replaceAll("\r", "").replaceAll("\n", "");
		}catch (Exception e) {
		}
		return "";		
	}
	
	/**
	 * 3DES 解密 CBC模式
	 * @param ciphertext 密文
	 * @param strKey 密钥（24位）
	 * @param strIv 向量（8位）
	 * @return
	 * @throws Exception
	 */
	public static String Des3DecryptCBC(String ciphertext, String strKey, String strIv) {
		try{
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKey key = new SecretKeySpec(strKey.getBytes(), Algorithm);
			IvParameterSpec iv = new IvParameterSpec(strIv.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] plainByte = cipher.doFinal(base64Decoder.decodeBuffer(ciphertext));
			return new String(plainByte, "UTF-8");
		}catch (Exception e) {
		}
		return "";
	}
	
	
}