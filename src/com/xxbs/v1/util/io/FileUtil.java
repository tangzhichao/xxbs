package com.xxbs.v1.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author tang
 * 
 */
public class FileUtil {

	public static boolean checkFileSize(File file, int size_MB) {
		long size = size_MB * 1024 * 1024;
		return file.length() > size ? false : true;
	}

	public static String getFilePostfix(File file) {
		String absolutePath = file.getAbsolutePath();
		int lastIndexOf = absolutePath.lastIndexOf(".");
		if (lastIndexOf >= 0) {
			return absolutePath.substring(lastIndexOf + 1);
		}
		return "";
	}
	
	public static String getFilePostfix(String path) {
		int lastIndexOf = path.lastIndexOf(".");
		if (lastIndexOf >= 0) {
			return path.substring(lastIndexOf + 1);
		}
		return "";
	}

	public static String getFileNameNotPostfix(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf >= 0) {
			return name.substring(0, lastIndexOf);
		}
		return "";
	}

	public static boolean exists(File file) {
		return file != null && file.exists();
	}

	public static boolean isCanWrite(File targetFile) {
		if (targetFile == null) {
			return false;
		}
		if (targetFile.isDirectory()) {
			return false;
		}
		if (!targetFile.exists()) {
			return true;
		}
		try (FileOutputStream fos = new FileOutputStream(targetFile, true)) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String readTextFile(File file) {
		return readTextFile(file, "UTF-8");
	}

	public static String readTextFile(File file, String code) {
		try {
			return new String(Files.readAllBytes(file.toPath()), code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File createFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return file;
		}
		if (file.isDirectory()) {
			file.mkdirs();
			return file;
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void saveTextFile(String filePath, String str) {
		saveTextFile(createFile(filePath), str);
	}

	public static void saveTextFile(File file, String str) {
		saveTextFile(file, str, "UTF-8");
	}

	public static void saveTextFile(File file, String str, String code) {
		try {
			Files.write(file.toPath(), str.getBytes(code), StandardOpenOption.CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(File file) {
		if (!exists(file)) {
			return;
		}
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				deleteFile(listFiles[i]);
			}
			// Files.delete(file.toPath());
			file.delete();// ɾ���Ŀ¼
		} else {
			file.delete();// ɾ���ļ�
		}
	}

	public static void moveFile(File sourceFile, File targetFile) {
		if (!exists(sourceFile)) {
			return;
		}
		if (sourceFile.isDirectory()) {
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			File[] listFiles = sourceFile.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File childTargetFile = new File(targetFile.getPath() + File.separator + listFiles[i].getName());
				moveFile(listFiles[i], childTargetFile);
			}
			sourceFile.delete();// ɾ���Ŀ¼
		} else {
			try {
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				// Files.move������֧���ƶ�����ļ��еģ��������Ŀ���ļ����Ѿ������򱨴�
				Files.move(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyFile(File sourceFile, File targetFile) {
		if (!exists(sourceFile)) {
			return;
		}
		if (sourceFile.isDirectory()) {
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			File[] listFiles = sourceFile.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File childTargetFile = new File(targetFile.getPath() + File.separator + listFiles[i].getName());
				copyFile(listFiles[i], childTargetFile);
			}
		} else {
			try {
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				// Files.copy������֧�ָ�������ļ���
				Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String md5Hex(File file) {
		if (exists(file)) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				return DigestUtils.md5Hex(fileInputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
