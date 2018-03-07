package com.xxbs.v1.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
	
	
	/**
	 * GET 方式获取url下面的数据
	 * @param urlStr
	 * @return
	 */
	public static String getData(String urlStr){
		String resultStr = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer data = new StringBuffer();
			String temp = "";
			while((temp=reader.readLine())!=null){
				data.append(URLDecoder.decode(temp, "UTF-8"));
			}
			reader.close();
			resultStr = data.toString();
		} catch (Exception e) {
		}
		return resultStr;		
	}
	
	/**
	 * 提交数据，获取数据
	 * @POST
	 * @return
	 */
	public static String postData(String urlStr, String postData){
		String jsonData = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);// 打开输入流
			connection.setDoOutput(true);// 打开输出流
			connection.setDoInput(true);// 打开输入流
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8")); 
			writer.write(postData);
			writer.flush();
			writer.close();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer xml = new StringBuffer();
			String temp = "";
			while((temp = reader.readLine())!=null){
	            xml.append(temp);
			}
			reader.close();
			jsonData = xml.toString();
			
		}catch (Exception e) {
		}
		return jsonData;
	}

	public static String rest(String serviceUrl, String parameter, String restMethod) {
		StringBuffer sb = new StringBuffer();

		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setConnectTimeout(60 * 1000);
			con.setRequestMethod(restMethod);

			if (!"GET".equals(restMethod)) {
				con.setDoOutput(true);
				if (!"DELETE".equals(restMethod)) {

					OutputStream os = con.getOutputStream();
					os.write(parameter.getBytes("utf-8"));
					os.close();
					InputStream in = con.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
					String line = "";
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
				}
			} else {
				InputStream in = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			}
			// System.out.println(con.getResponseCode() + ":"
			// + con.getResponseMessage());

			// return sb.toString();
			return con.getResponseMessage();

		} catch (Exception e) {
			System.out.println(e);
			return "-1";
		}

	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream() , "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
