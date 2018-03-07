package com.xxbs.v1.util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPDownloadUtil {

	private HttpURLConnection httpConn;
	private InputStream inputStream;
	private String sourceName;
	private String fileName;
	private String postfix;
	private int contentLength;
	private int size;
	private final String errorFrefix = "�ļ�����ʧ��: ";

	public void downloadFile(String urlPath, String fileName, String filePostfix, boolean isUserFileName) throws IOException {
		URL url = new URL(urlPath);
		System.out.println("download urlStr===" + urlPath);
		sourceName = url.getHost();

		// NetworkBean networkBean = NetworkSetting.getCacheNetworkBean();
		// if (NetworkSetting.isUserProxy(networkBean)) {
		// httpConn = (HttpURLConnection) url.openConnection(NetworkSetting.createProxy(networkBean));
		// } else {
		// httpConn = (HttpURLConnection) url.openConnection();
		// }
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setConnectTimeout(10000);
		httpConn.setReadTimeout(0);
		httpConn.setUseCaches(false);
		httpConn.setRequestProperty("Charset", "UTF-8");
		// NetworkSetting.setConnectionAuthorization(httpConn, networkBean);
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			if (disposition != null && (!isUserFileName || fileName == null)) {
				String[] split = disposition.split(";");
				boolean isAutoName = false;
				for (int i = 0; i < split.length; i++) {
					if (split[i].indexOf("filename") >= 0) {
						this.fileName = split[i].split("=")[1];
						isAutoName = true;
						break;
					}
				}
				if (!isAutoName) {
					this.fileName = fileName;
				}
			} else {
				this.fileName = fileName;
			}
			String contentType = httpConn.getContentType();
			contentLength = httpConn.getContentLength();
			size = contentLength / 1024;
			postfix = filePostfix;

			// output for debugging purpose only
			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);
			System.out.println("postfix = " + postfix);
			System.out.println("sourceName = " + sourceName);
			System.out.println(httpConn.getURL());

			if (disposition != null && !disposition.trim().isEmpty() && disposition.startsWith("attachment")) {
				inputStream = httpConn.getInputStream();
			} else {
				String responseText = "";
				try (InputStream responseStream = httpConn.getInputStream();
						InputStreamReader streamReader = new InputStreamReader(responseStream);
						BufferedReader br = new BufferedReader(streamReader)) {
					responseText = br.readLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
				String message;
				if (responseText == null) {
					message = "�ļ���ȡ����!";
				} else if (responseText.startsWith("3")) {
					message = "�ļ�û���ҵ�!";
				} else if (responseText.startsWith("4")) {
					message = "MD5У��ʧ��!";
				} else {
					message = "�ļ���ȡ����!";
				}
				throw new RuntimeException(errorFrefix + message);
			}
		} else {
			throw new IOException(errorFrefix + "���������쳣!");
		}
	}

	public void disconnect() throws IOException {
		try {
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
		} catch (IOException e) {
			// throw e;
		} finally {
			if (httpConn != null) {
				httpConn.disconnect();
				httpConn = null;
			}
		}
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getPostfix() {
		return postfix;
	}

	public int getContentLength() {
		return this.contentLength;
	}

	public int getSize() {
		return size;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}
}