package com.xxbs.v1.util.safe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 解密解密工具类
 */
public class DESSecurityUtil {

	/**
	 * 如果系统中存在旧版本的数据，则此值不能修改，否则在进行密码解析的时候出错
	 */
	private static final String PASSWORD_CRYPT_KEY = "__jD*lo%g_";
	private final static String DES = "DES";

	// ///////////////////////////////////////////////////////////////
	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	// ////////////////////////////////////////////////////////////

	/**
	 * 数据解密
	 * 
	 * @param data
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public final static String decrypt(String data, String key) {
		if (data != null)
			try {
				return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 数据加密
	 * 
	 * @param data
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public final static String encrypt(String data, String key) {
		if (data != null)
			try {
				return byte2hex(encrypt(data.getBytes(), key.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	// //////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 密码解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decryptPassword(String data) {
		if (data != null)
			try {
				return new String(decrypt(hex2byte(data.getBytes()), PASSWORD_CRYPT_KEY.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static byte[] decryptPasswordToByte(String data) {
		if (data != null)
			try {
				return decrypt(hex2byte(data.getBytes()), PASSWORD_CRYPT_KEY.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final static String encryptPassword(String password) {
		if (password != null)
			try {
				return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			System.err.println("++++++++++ 加密文本：" + (new String(b)).toString());
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	// ////////////////////////////////////////////////////////////////////////////////

	public static String PwdByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// // 确定计算方法
		// MessageDigest md5 = MessageDigest.getInstance("MD5");
		// BASE64Encoder base64en = new BASE64Encoder();
		// // 加密后的字符串
		// String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		// return newstr;

		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(str.getBytes());
		return new BigInteger(1, digest.digest()).toString(16);

	}

	/**
	 * 附加下载时根据文件路径生成动态密码
	 * 
	 * @param txt
	 *            文件路径
	 * @param key
	 *            key=abcdefgh
	 * @return
	 * @throws Exception
	 */
	public static String encryptForFile(String txt, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "DES"));
		return Base64.encode(cipher.doFinal(txt.getBytes("UTF-8")));
	}

	/**
	 * 附加下载时根据文件路径生成动态密码(取前16位)
	 * 
	 * @param txt
	 *            文件路径
	 * @param key
	 *            key=abcdefgh
	 * @param len
	 *            长度取前16位
	 * @return
	 * @throws Exception
	 */
	public static String encryptImperfectForFile(String txt, String key, int len) throws Exception {
		String enc = encryptForFile(txt, key);
		if (null != enc && enc.length() > len)
			return enc.substring(0, len);
		return enc;
	}

	// 增加解压方法
	public static String uncompress(String data) throws IOException {
		if (data == null || data.length() == 0) {
			return "";
		}

		byte[] dataByte = DESSecurityUtil.decryptPasswordToByte(data);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(dataByte);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		try {
			byte[] buffer = new byte[1024];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
		} finally {
			gunzip.close();
			in.close();
			out.close();
		}

		return out.toString("UTF-8");
	}

	// public static void main(String[] args) {
	//
	// String date = "abcefghijklmnopqrstuvwxyz汉字的萨芬的阿萨德发0123456789";
	// System.out.println("加密之前的数据是：  " + date);
	// // 加密
	// String jiamidate = DESSecurityUtil.encryptPassword(date);
	// System.out.println(jiamidate);
	//
	// // 解密
	// String jiemidate = DESSecurityUtil.decryptPassword(jiamidate);
	// System.out.println("解密还原后数据是：  " + jiemidate);
	// }

}