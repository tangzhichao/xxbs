package com.xxbs.v1.util.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * �����쳣��ص�����ķ�������д���ļ� <br>
 * ����ʱ������쳣���ڴ����쳣����ô����д���ļ�����
 * 
 * @author tang
 * 
 */
public interface WriteOutFileCallback {
	FileOutputStream writeOutFile(Object obj, String path) throws FileNotFoundException, IOException, Exception;

	FileOutputStream writeOutFile(Object obj, FileOutputStream out) throws FileNotFoundException, IOException, Exception;
}
