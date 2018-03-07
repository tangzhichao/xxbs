package com.xxbs.v1.util.io;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;


/**
 * 解压zip工具类
 * 
 * @author tang
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class ZipUtil {

	public static void main(String[] args) {
		try {
			String[] paths={"D:/software/apache-tomcat-7.0.42/webapps/HUNANYXS/qrcode/user95/自有人员测试.png",
			"D:/software/apache-tomcat-7.0.42/webapps/HUNANYXS/qrcode/user50/测试五一.png",
			"D:\\software\\apache-tomcat-7.0.42\\webapps\\HUNANYXS\\qrcode/user92/一线.png",
			"D:/software/apache-tomcat-7.0.42/webapps/HUNANYXS/qrcode/user45/五月三.png",
			"D:/software/apache-tomcat-7.0.42/webapps/HUNANYXS/qrcode/user83/测试二.png",
			"D:/software/apache-tomcat-7.0.42/webapps/HUNANYXS/qrcode/user84/测试三.png",
			"D:/software/apache-tomcat-7.0.42/webapps/HUNANYXS/qrcode/user66/一线测试五一五.png"
			};
			filesToZip(paths, "D:\\software\\apache-tomcat-7.0.42\\webapps\\HUNANYXS\\qrcode/user43/天翼2.zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean filesToZip(String[] filePaths, String zipPath) throws IOException {
		System.out.println(Arrays.toString(filePaths));
		File targetFile = new File(zipPath);
		try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile))) {
			out.setEncoding("UTF-8");
			for (String filePath : filePaths) {
				File file = new File(filePath);
				if (file.isDirectory()) {
					dirToZip(file, out);
				} else {
					fileToZip(file, out);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void dirToZip(File dir, ZipOutputStream out) throws IOException {
		if (dir.isDirectory()) {
			ZipEntry entry = new ZipEntry(dir.getName());
			out.putNextEntry(entry);
			out.closeEntry();
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					dirToZip(file, out);
				} else {
					fileToZip(file, out);
				}
			}
		}
	}

	private static void fileToZip(File file, ZipOutputStream out) throws IOException {
		if (file.isFile()) {
			try (FileInputStream in = new FileInputStream(file)) {
				ZipEntry entry = new ZipEntry(file.getName());
				out.putNextEntry(entry);
				byte[] buffer = new byte[1024 * 4];
				for (int len = in.read(buffer); len > 0; len = in.read(buffer)) {
					out.write(buffer, 0, len);
				}
				out.flush();
				out.closeEntry();
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 *            目录绝对路径名
	 */
	public static void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 解压zip文件
	 * 
	 * @param zipPath
	 *            zip文件绝对路径
	 * @param targetPath
	 *            解压到目录路径
	 * @param propertyChangeListener
	 *            解压进度监听器，回调通知
	 */
	public static void unzip(String zipPath, String targetPath, PropertyChangeListener propertyChangeListener) {

		File file = new File(zipPath);

		if (!file.exists()) {// 文件不存在
			System.err.println("文件不存在:" + file.getAbsolutePath());
			return;
		}

		if (null == targetPath || "".equals(targetPath)) {// 如果为空,则将解压到当前文件夹
			targetPath = zipPath.substring(0, zipPath.lastIndexOf("."));
		}

		if (!targetPath.endsWith("\\") && !targetPath.endsWith("/")) {// 如果后面没接文件分割符则为其添加
			targetPath += "/";
		}

		File unPathFile = new File(targetPath);

		if (unPathFile.exists()) {// 目标存在则删除
			FileUtil.deleteFile(unPathFile);
		}

		if (!unPathFile.exists()) {// 不存在则创建
			unPathFile.mkdirs();
		}

		ZipFile zipFile = null;// zip文件对象
		try {
			zipFile = new ZipFile(file);// 构造这个zip文件对象

			long totalSize = 0;// 总大小
			long byteLengthRead = 0;

			// 先计算出总大小
			for (Enumeration zipEnum = zipFile.getEntries(); zipEnum.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) zipEnum.nextElement();
				if (!entry.isDirectory()) {
					totalSize += entry.getSize();
				}
			}

			for (Enumeration zipEnum = zipFile.getEntries(); zipEnum.hasMoreElements();) {
				// 得到当前条目
				ZipEntry entry = (ZipEntry) zipEnum.nextElement();

				String entryName = new String(entry.getName());

				// 用/分隔条目名称
				String names[] = entryName.split("\\/");// split使用正则，其实这个"\\/"等于"/","\\\\"其实等于"\"

				int length = names.length;

				String path = unPathFile.getAbsolutePath();

				for (int v = 0; v < length; v++) {

					if (v < length - 1) { // 最后一个目录之前的目录
						path += "/" + names[v] + "/";
						createDir(path);
					} else { // 最后一个

						if (entryName.endsWith("/")) {// 为目录,则创建文件夹

							createDir(unPathFile.getAbsolutePath() + "/" + entryName);

						} else { // 为文件,则输出到文件
							try (InputStream input = zipFile.getInputStream(entry);
									BufferedInputStream bis = new BufferedInputStream(input);
									OutputStream output = new FileOutputStream(new File(unPathFile.getAbsolutePath() + "/" + entryName));
									BufferedOutputStream bos = new BufferedOutputStream(output)) {

								byte[] buffer = new byte[1024 * 8];

								for (int readLen = bis.read(buffer); readLen >= 0; readLen = bis.read(buffer)) {// 读文件

									bos.write(buffer, 0, readLen);// 写文件
									bos.flush();// 刷出

									Integer oldValue = (int) ((byteLengthRead * 1.0 / totalSize) * 100);// 已解压的字节大小占总字节的大小的百分比

									byteLengthRead += readLen;// 累加字节长度

									Integer newValue = (int) ((byteLengthRead * 1.0 / totalSize) * 100);// 已解压的字节大小占总字节的大小的百分比

									if (propertyChangeListener != null) {// 通知调用者解压进度发生改变
										propertyChangeListener.propertyChange(new PropertyChangeEvent(zipFile, "progress", oldValue, newValue));
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();// 关文件
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
