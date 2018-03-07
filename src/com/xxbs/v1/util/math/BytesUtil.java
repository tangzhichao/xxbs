package com.xxbs.v1.util.math;

import java.io.UnsupportedEncodingException;

/**
 * 1.最右边的字节是最低位字节,最左边的字节是最高位字节<br>
 * 2.先存低位字节的叫小端模式（先存左边），大端模式则先保存高位字节（先存右边）<br>
 * 
 * @author god
 * 
 */
public class BytesUtil {

	/**
	 * 将"0XA123B"这样的16进制字符串所表示的数字值（long）转成byte数组
	 * @param hex 数字值的16进制字符串
	 * @param len 转成多个少byte（byte数组长度）
	 * @param isBigEndian 是否使用大端模式
	 * @return 数字值的byte数组
	 */
	public static byte[] hexStringToBytes(String hex, int len, boolean isBigEndian) {
		return numberToBytes(Long.parseLong(hex, 16), len, isBigEndian);
	}

	/**
	 * 将byte数组所表示的数字值转成"0XA123B"这样的16进制字符串
	 * @param bytes 数字值的byte数组
	 * @param isBigEndian 是否使用大端模式
	 * @return 数字值的16进制字符串
	 */
	public static String bytesToHexString(byte[] bytes, boolean isBigEndian) {
		return Long.toHexString(bytesToNumber(bytes, isBigEndian));
	}

	public static byte[] shortToBytes(int number, boolean isBigEndian) {
		return numberToBytes(number, 2, isBigEndian);
	}

	public static byte[] intToBytes(int number, boolean isBigEndian) {
		return numberToBytes(number, 4, isBigEndian);
	}
	
	public static byte[] longToBytes(long number, boolean isBigEndian) {
		return numberToBytes(number, 8, isBigEndian);
	}

	/**
	 * 
	 * @param number 数字值
	 * @param len 转成多个少byte（byte数组长度）
	 * @param isBigEndian 是否使用大端模式
	 * 1.最右边的字节是最低位字节,最左边的字节是最高位字节<br>
	 * 2.先存低位字节的叫小端模式（先存左边），大端模式则先保存高位字节（先存右边）<br>
	 * @return 数字值的byte数组
	 */
	public static byte[] numberToBytes(long number, int len, boolean isBigEndian) {
		if (len < 1 || len > 8) {
			throw new RuntimeException("len<1||len>8");
		}

		byte[] bytes = new byte[len];
		if (isBigEndian) {
			for (int i = 0; i < bytes.length; i++)// 从低位到高位
			{
				bytes[i] = (byte) ((number >> (i * 8)) & 0xff);
			}
		} else {
			for (int i = bytes.length - 1; i >= 0; i--)// 从高位到低位
			{
				bytes[i] = (byte) ((number >> ((bytes.length - i - 1) * 8)) & 0xff);
			}
		}
		return bytes;
	}

	/**
	 * @param bytes 数字值的byte数组
	 * @param isBigEndian 是否使用大端模式
	 * 1.最右边的字节是最低位字节,最左边的字节是最高位字节<br>
	 * 2.先存低位字节的叫小端模式（先存左边），大端模式则先保存高位字节（先存右边）<br>
	 * @return byte数组所表示的数字值
	 */
	public static long bytesToNumber(byte[] bytes, boolean isBigEndian) {
		if (bytes.length < 1 || bytes.length > 8) {
			throw new RuntimeException("bytes.length<1||bytes.length>8");
		}

		long temp = 0;

		if (isBigEndian) {
			for (int i = 0; i < bytes.length; i++)// 从低位到高位
			{
				if (i == 0) {
					temp = (bytes[i] & 0xFFL) << (i * 8);
				} else {
					temp = temp | ((bytes[i] & 0xFFL) << (i * 8));
				}
			}
		} else {
			for (int i = bytes.length - 1; i >= 0; i--)// 从高位到低位
			{
				if (i == (bytes.length - 1)) {
					temp = (bytes[i] & 0xFFL) << ((bytes.length - i - 1) * 8);
				} else {
					temp = temp | ((bytes[i] & 0xFFL) << ((bytes.length - i - 1) * 8));
				}
			}
		}
		return temp;
	}
	
	
	
	/**
	 * 将普通字符串的bytes转字符串
	 * @param hexDeviceCode
	 * @param encoding
	 * @return
	 */
	public static String hexBytesToHexStr(byte[] hexDeviceCode,String encoding){
		try
		{
			return new String(hexDeviceCode, encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将"0XA123B"这样的16进制字符串的byte数组转成long
	 * @param hexDeviceCode byte[] hexDeviceCode="0XA123B".getBytes(encoding)
	 * @param encoding
	 * @return
	 */
	public static long hexBytesToLong(byte[] hexDeviceCode,String encoding){
		return Long.parseLong(hexBytesToHexStr(hexDeviceCode,encoding), 16);
	}
	
	public static String toFormatHexStr(Long deviceCode,int len) {
		if (deviceCode == null) {
			return null;
		}
		String hexCode = Long.toHexString(deviceCode).toLowerCase();
		if (hexCode.length() < len) {
			int s = len - hexCode.length();
			for (int i = 0; i < s; i++) {
				hexCode = "0" + hexCode;
			}
		}
		return hexCode;
	}
	
}
