/*
 * 作用:文件公共操作类
 * 版本:V1.0
 * 日期:2011-12-25 Sat.
 */

package com.xxbs.v1.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;

public class FileTypeUtil {

	
	
	public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();
	static {
		
		mFileTypes.put("FFD8FF", "jpg"); //JPEG (jpg)
		mFileTypes.put("89504E47", "png"); //PNG (png)
		mFileTypes.put("47494638", "gif"); //GIF (gif)
		mFileTypes.put("424d", "bmp"); //BMP (bmp)
		mFileTypes.put("D0CF11E0", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样 
		mFileTypes.put("504B0304", "docx");//docx文件
		
		mFileTypes.put("504B0304", "zip");
		mFileTypes.put("52617221", "rar");
		mFileTypes.put("255044462D312E", "pdf"); //Adobe Acrobat (pdf)
		mFileTypes.put("3C3F786D6C", "xml");//xml文件
		
		mFileTypes.put("6D6F6F76", "mov"); //Quicktime (mov)
		mFileTypes.put("FF575043", "wpd"); //WordPerfect (wpd) 
		mFileTypes.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx) 
		mFileTypes.put("2142444E", "pst"); //Outlook (pst)
		mFileTypes.put("AC9EBD8F", "qdf"); //Quicken (qdf) 
		mFileTypes.put("E3828596", "pwl"); //Windows Password (pwl)
		
		mFileTypes.put("49492a00227105008037", "tif"); //TIFF (tif)
		mFileTypes.put("41433130313500000000", "dwg"); //CAD (dwg) 
		mFileTypes.put("3c21444f435459504520", "html"); //HTML (html)
		mFileTypes.put("3c21646f637479706520", "htm"); //HTM (htm)
		mFileTypes.put("48544d4c207b0d0a0942", "css"); //css
		mFileTypes.put("696b2e71623d696b2e71", "js"); //js
		mFileTypes.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf) 
		mFileTypes.put("38425053000100000000", "psd"); //Photoshop (psd) 
		mFileTypes.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml) 
		mFileTypes.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图 
		mFileTypes.put("5374616E64617264204A", "mdb"); //MS Access (mdb)
		mFileTypes.put("252150532D41646F6265", "ps"); 
		mFileTypes.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同
		mFileTypes.put("464c5601050000000900", "flv"); //flv与f4v相同
		mFileTypes.put("00000020667479706d70", "mp4"); 
		mFileTypes.put("49443303000000002176", "mp3"); 
		mFileTypes.put("000001ba210001000180", "mpg"); // 
		mFileTypes.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同
		mFileTypes.put("52494646e27807005741", "wav"); //Wave (wav)
		mFileTypes.put("52494646d07d60074156", "avi");
		mFileTypes.put("4d546864000000060001", "mid"); //MIDI (mid) 
		mFileTypes.put("235468697320636f6e66", "ini"); 
		mFileTypes.put("504b03040a0000000000", "jar"); 
		mFileTypes.put("4d5a9000030000000400", "exe");//可执行文件
		mFileTypes.put("3c25402070616765206c", "jsp");//jsp文件
		mFileTypes.put("4d616e69666573742d56", "mf");//MF文件
		mFileTypes.put("494e5345525420494e54", "sql");//xml文件
		mFileTypes.put("7061636b616765207765", "java");//java文件
		mFileTypes.put("406563686f206f66660d", "bat");//bat文件
		mFileTypes.put("1f8b0800000000000000", "gz");//gz文件
		mFileTypes.put("6c6f67346a2e726f6f74", "properties");//bat文件
		mFileTypes.put("cafebabe0000002e0041", "class");//bat文件
		mFileTypes.put("49545346030000006000", "chm");//bat文件
		mFileTypes.put("04000000010000001300", "mxp");//bat文件
		mFileTypes.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的
		mFileTypes.put("6431303a637265617465", "torrent");
		
	}
	
	/**
	 * 输出从数据库拼接好的实体对象到指定的硬盘目录
	 * @author mafeiyue 2011-12-25 Sat
	 * @param outputPath 输出的硬盘目录
	 * @param outputFileName 保存到硬盘的文件名称
	 * @param fileContent 文件输出的内容
	 * @return 
	 */
	public static void entityToDisk(String outputPath, String outputFileName, String fileContent){
		try {
			File file1 = new File(outputPath);
			if(!file1.exists()){
				file1.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(outputPath + "/" + outputFileName);
			OutputStreamWriter writer = new OutputStreamWriter(fos,"UTF-8");
			writer.write(fileContent);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将byte数组转换成String
	 * @param b
	 * @return
	 */
	public static String bytesToHexString(byte[] src){
		StringBuilder strBuilder = new StringBuilder();  
		if (src == null || src.length <= 0) {  
		    return null;  
		}  
		for (int i = 0; i < src.length; i++) {
		    int v = src[i] & 0xFF;
		    String hv = Integer.toHexString(v);
		    if (hv.length() < 2) {
		    	strBuilder.append(0);
		    }
		    strBuilder.append(hv);
		}
		return strBuilder.toString();
	     
	}
	
	/**
	 * 获取文件类型
	 * @param filepath
	 * @return
	 */
	public static String getFileType(String filepath){
		String ext = null;
		try {
			FileInputStream fis = new FileInputStream(filepath);
            byte[] b = new byte[10];
            fis.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);
            
            //这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
            if(mFileTypes.get(fileCode)!=null && !"".equals(mFileTypes.get(fileCode))){
            	ext = mFileTypes.get(fileCode);
            }else{
            	Iterator<String> keyIter = mFileTypes.keySet().iterator();
            	while(keyIter.hasNext()){
            		String key = keyIter.next();
            		if(key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())){
            			ext = mFileTypes.get(key);
            			break;
            		}
            	}
            }
		} catch (Exception e){
			
		}
		return ext;
	}

	/**
	 * 得到文件类型
	 * @param filepath
	 * @return
	 */
	public static String getFileType(File file){
		String ext = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[10];
			fis.read(b, 0, b.length);
			String fileCode = bytesToHexString(b);
			
			//这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
			if(mFileTypes.get(fileCode)!=null && !"".equals(mFileTypes.get(fileCode))){
				ext = mFileTypes.get(fileCode);
			}else{
				Iterator<String> keyIter = mFileTypes.keySet().iterator();
				while(keyIter.hasNext()){
					String key = keyIter.next();
					if(key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())){
						ext = mFileTypes.get(key);
						break;
					}
				}
			}
		} catch (Exception e){
			
		}
		return ext;
	}
}
