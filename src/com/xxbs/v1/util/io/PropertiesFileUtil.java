package com.xxbs.v1.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * ���������ļ�(*.properties)����
 * 
 * @ע ����һ������Ҫ��˵����������������һ��properties�ļ�����ʹ����setContentValue����wirteContentValue������ ���ļ�ԭ�����������ݻᱻ��� ���ԣ�������һ��properties�ļ���Ų���صĶ��key��value
 * 
 * @ע ��һ�������в���new���PropertiesFileToolȴָ��ͬһ���ļ�������ᵼ�º�����������ǰ�����еĲ�������Ҳ��һ��tool��Ӧһ���ļ���ԭ��
 * 
 * @ע ����ļ������ڣ����Զ�����
 * 
 * @author tang
 */
public class PropertiesFileUtil {

	private Properties properties = new Properties();

	public final String appPathKey = "user.dir";

	public String encoding;

	/**
	 * ��Գ�����ļ������֣��确data/properties��Ŀ¼,�ַ���ǰ�������涼����Ҫ�ļ��ָ���
	 */
	private String folderName;

	/**
	 * �ļ������֣���Ҫ�����ļ���׺���确configfile.properties��
	 */
	private String fileName;

	private String key;

	private String value;

	/**
	 * ���������key������ȡ��value����û�и��key��ֵΪtrue��Ϊtrue�Ͳ�ȥ��ȡ��
	 */
	private boolean isRead;

	/**
	 * �˹��췽����Ĭ���ҵ������µġ�data/properties��Ŀ¼�еġ�configfile.properties���ļ�����Ĭ��Ҫ������keyΪ��previoufilepath��
	 */
	public PropertiesFileUtil() {
		this("previoufilepath");
	}

	/**
	 * �˹��췽����Ĭ���ҵ������µġ�data/properties��Ŀ¼�еġ�configfile.properties���ļ�
	 * 
	 * @param contentKey
	 */
	public PropertiesFileUtil(String contentKey) {
		this("configfile.properties", contentKey);
	}

	/**
	 * �˹��췽����Ĭ���ҵ������µġ�data/properties��Ŀ¼
	 * 
	 * @param fileName
	 * @param contentKey
	 */
	public PropertiesFileUtil(String fileName, String contentKey) {
		this("data" + File.separator + "properties", fileName, contentKey);
	}

	public PropertiesFileUtil(String folderName, String fileName, String contentKey) {
		this(folderName, fileName, contentKey, null);
	}

	public PropertiesFileUtil(String folderName, String fileName, String contentKey, String encoding) {
		this.folderName = folderName;
		this.fileName = fileName;
		this.key = contentKey;
		this.encoding = encoding;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		isRead = false;
		this.key = key;
	}

	/**
	 * ��ȡ��Ӧkey�����ֵ
	 * 
	 * @return
	 */
	public String getValue() {
		if (!isRead) {
			value = getValue(key);
		}
		return value;
	}

	/**
	 * ��¼��Ӧkey�����ֵ
	 */
	public void setValue(String value) {
		setValue(key, value);
		this.value = value;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * ����ļ��������ָ��·�������ڴ��ļ������Դ�������ļ���������null
	 */
	public File getFile() {
		// if success appPath=D:\eclipseworkspace\bairui
		String appPath = System.getProperty(appPathKey);
		// File.separator���ļ�Ŀ¼�ָ��� windows="\" unix="/"
		File file = new File(appPath + File.separator + folderName + File.separator + fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return file;
		}
		return file;
	}

	/**
	 * ��ȡkey��ֵ
	 * 
	 * @param key
	 */
	public String getValue(String key) {
		File file = getFile();
		if (file == null) {
			return null;
		}
		String value = null;
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			properties.load(fileInputStream);
			value = uncoding(properties.getProperty(key));
		} catch (Exception e) {
			System.err.println("��ȡ" + key + "ʧ�ܣ�");
			e.printStackTrace();
		}

		isRead = true;

		return value;
	}

	/**
	 * ��ȡ���key��ֵ
	 * 
	 * @param keys
	 * @return
	 */
	public String[] getValues(String[] keys) {
		String[] values = new String[keys.length];
		File file = getFile();
		if (file == null) {
			return values;
		}
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			properties.load(fileInputStream);
			for (int i = 0; i < values.length; i++) {
				values[i] = uncoding(properties.getProperty(keys[i]));
			}
		} catch (Exception e) {
			System.err.println("��ȡ" + Arrays.toString(keys) + "ʧ�ܣ�");
			e.printStackTrace();
		}
		isRead = true;
		return values;
	}

	/**
	 * ��¼һ��ֵ���ļ�
	 * 
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value) { 
		File file = getFile();
		if (file == null) {
			return;
		}
		try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {

			properties.setProperty(key, encoding(value));
			properties.store(fileOutputStream, "Update '" + key + "' value");

		} catch (Exception e) {
			System.err.println("д��" + key + "=" + value + "ʧ�ܣ�");
			e.printStackTrace();
		}

	}

	/**
	 * д����key��value��
	 * 
	 * @param keys
	 * @param values
	 */
	public void setValues(String[] keys, String[] values) {

		File file = getFile();

		if (file == null) {
			return;
		}
		try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {

			for (int i = 0; i < keys.length; i++) {
				properties.setProperty(keys[i], encoding(values[i]));
			}

			properties.store(fileOutputStream, "Update '" + Arrays.toString(keys) + "' value");

		} catch (Exception e) {
			System.err.println("д��" + Arrays.toString(keys) + "=" + Arrays.toString(values) + "ʧ�ܣ�");
			e.printStackTrace();
		}
	}

	private String uncoding(String str) {
		if (encoding == null) {
			return str;
		}
		try {
			return new String(str.getBytes("ISO8859-1"), encoding);
		} catch (Exception e) {
			System.err.println("����" + str + "ʧ�ܣ�" + ",������Ϣ���£�");
			e.printStackTrace();
		}
		return null;
	}

	private String encoding(String str) {
		if (encoding == null) {
			return str;
		}
		try {
			return new String(str.getBytes(encoding), "ISO8859-1");
		} catch (Exception e) {
			System.err.println("����" + str + "ʧ�ܣ�" + ",������Ϣ���£�");
			e.printStackTrace();
		}
		return null;
	}

}
