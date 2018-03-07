package com.xxbs.v1.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ���л�������
 * 
 * @author tang
 * 
 */
public class SerializableUtil {

	public static Object readObject(File file) {
		Object[] os = new Object[1];
		return readObject(file, os) != null ? os[0] : null;
	}

	public static Object[] readObject(File file, Object[] results) {
		if (!file.exists()) {
			return null;
		}
		try (FileInputStream fileInputStream = new FileInputStream(file); ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
			for (int i = 0; i < results.length; i++) {
				results[i] = objectInputStream.readObject();
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean saveObject(File file, Object object) {
		return saveObject(file, object, false);
	}

	public static boolean saveObject(File file, Object object, boolean append) {
		return saveObject(file, new Object[] { object }, append);
	}

	public static boolean saveObject(File file, Object[] objects, boolean append) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try (FileOutputStream fileOutputStream = new FileOutputStream(file, append);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			for (int i = 0; i < objects.length; i++) {
				objectOutputStream.writeObject(objects[i]);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
