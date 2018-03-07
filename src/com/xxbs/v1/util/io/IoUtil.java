package com.xxbs.v1.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IoUtil {

	public static Object copyOfDeep(Object obj) throws IOException, ClassNotFoundException{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(baos);
	    oos.writeObject(obj);
	    oos.close();

	    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	    ObjectInputStream ois = new ObjectInputStream(bais);
	    Object obj2 = ois.readObject();
	    ois.close();
	    return obj2;
	}
	
	public static byte[] readAllBytes(InputStream in, int initialSize) throws IOException {
		try (InputStream _in = in) {
			int capacity = initialSize;
			byte[] buf = new byte[capacity];
			int nread = 0;
			int rem = buf.length;
			for (int n = _in.read(buf, nread, rem); n > 0; n = _in.read(buf, nread, rem)) {
				nread += n;
				rem -= n;
				assert rem >= 0;
				if (rem == 0) {
					int newCapacity = capacity << 1;
					if (newCapacity < 0) {
						if (capacity == Integer.MAX_VALUE)
							throw new OutOfMemoryError("Required array size too large");
						newCapacity = Integer.MAX_VALUE;
					}
					rem = newCapacity - capacity;
					buf = Arrays.copyOf(buf, newCapacity);
					capacity = newCapacity;
				}
			}
			return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
		} catch (IOException e) {
			throw e;
		}
	}

	public static String readAllString(InputStream in, int initialSize) throws IOException {
		return readAllString(in, initialSize, null);
	}

	public static String readAllString(InputStream in, int initialSize, String code) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(in)) {
			StringBuffer sb = new StringBuffer();
			byte[] b = new byte[initialSize];
			for (int i = bis.read(b); i > 0; i = bis.read(b)) {
				sb.append(code == null ? new String(b, 0, i) : new String(b, 0, i, code));
			}
			return sb.toString();
		}
	}

	public static String readAllString(Reader r, int initialSize) throws IOException {
		try (BufferedReader br = new BufferedReader(r)) {
			StringBuffer sb = new StringBuffer();
			char[] b = new char[initialSize];
			for (int i = br.read(b); i > 0; i = br.read(b)) {
				sb.append(new String(b, 0, i));
			}
			return sb.toString();
		}
	}

	public static List<String> readAllLines(InputStream in, String code) throws IOException {
		try (Reader r = code == null ? new InputStreamReader(in) : new InputStreamReader(in, code)) {
			return readAllLines(r);
		}
	}

	public static List<String> readAllLines(Reader r) throws IOException {
		try (BufferedReader br = new BufferedReader(r)) {
			List<String> result = new ArrayList<>();
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				result.add(line);
			}
			return result;
		}
	}
}
