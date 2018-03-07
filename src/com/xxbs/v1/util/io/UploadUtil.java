package com.xxbs.v1.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.xxbs.v1.util.Utils;

public class UploadUtil {
	
	public static final int fileSize1mb=1 * 1024 * 1024;
	public static final int fileSize10mb=10 * 1024 * 1024;
	
	public static final HashMap<String, String> extMap = new HashMap<String, String>();
	static{
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		extMap.put("xls", "xls,csv");
		extMap.put("html", "htm,html");
		extMap.put("doc", "doc,docx");
		extMap.put("zip", "zip,rar");
	}

	public static String makeNewFileName(String originalFileName) {
		return System.currentTimeMillis() + String.format("%03d", new Random().nextInt(1000)) + originalFileName.substring(originalFileName.indexOf("."));
	}

	public static String getPath(HttpServletRequest request, String module, String originalFilename,boolean useOldName) {

		String basePath = getBasePath(request);
		String dateStr = new SimpleDateFormat("yyyyMM").format(new Date());

		File temp = new File(basePath + module + "/" + dateStr + "/");
		if (!temp.exists()) {
			temp.mkdirs();
		}
		// 项目路径
		// 文件基于项目的相对路径
		String path = module + "/" + dateStr + "/" + (useOldName?originalFilename:makeNewFileName(originalFilename));

		return path;
	}
	public static String getPath(HttpServletRequest request, String module, String originalFilename) {
		return getPath(request, module, originalFilename, false);
	}

	public static boolean isAllowUploadFileExt(String fileName,String type){
		if (fileName.lastIndexOf(".")<=0) {
			return false;
		}
		if (type == null||!extMap.containsKey(type)) {
			type = "image";
		}
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(type).split(",")).contains(fileExt)){
			return false;//"上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(type) + "格式。")
		}
		return true;
	}
	
	public static boolean isEmptyFile(MultipartFile file){
		return file == null||file.isEmpty()||file.getSize()<=0||Utils.isEmptyTrim(file.getOriginalFilename());
	}
	
	public static boolean isEmptyFile(FileItem file){
		return file == null||file.getSize()<=0||Utils.isEmptyTrim(file.getName());
	}
	
	public static String uploadFile(HttpServletRequest request, MultipartFile file, String module) throws IllegalStateException, IOException {
		String basePath = getBasePath(request);
		String path = getPath(request, module, file.getOriginalFilename());
		String fullPath = basePath + path;
		File realFile = new File(fullPath);
		file.transferTo(realFile);
		return path;
	}

	public static String uploadFile(HttpServletRequest request, FileItem item, String module) throws Exception {
		String basePath = getBasePath(request);
		String path = getPath(request, module, item.getName());
		String fullPath = basePath + path;
		File realFile = new File(fullPath);
		item.write(realFile);
		return path;
	}

	public static String getBasePath(HttpServletRequest request) {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		String zhn = basePath + "upload/sjd/";
		return zhn;
	}
	public static String getBasePath(HttpSession session) {
		String basePath = session.getServletContext().getRealPath("/");
		String zhn = basePath + "upload/sjd/";
		return zhn;
	}
	public static String getBasePath(ServletContext servletContext) {
		String basePath = servletContext.getRealPath("/");
		String zhn = basePath + "upload/sjd/";
		return zhn;
	}

	public static String getBaseUrl(HttpServletRequest request) {
		String path = request.getContextPath();
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/upload/sjd/";
		return baseUrl;
	}
	public static String getUrl(HttpServletRequest request) {
		return request.getContextPath() + "/upload/sjd/";
	}

	/**
	 * 上传文件
	 * 
	 * @param urlStr
	 * @param file
	 */
	public static String upload(String urlStr, File file, String fileName) {
		String rv = "";
		try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String boundary = "----aryg4pBUG8gGY9qgAAs";

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			StringBuilder sb = new StringBuilder();
			sb.append("--" + boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			// 开始
			byte[] data = sb.toString().getBytes();
			out.write(data);

			// 将文件写入
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write("\r\n".getBytes());
			in.close();

			// 结束分割符
			byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();
			out.write(end_data);

			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sbf = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
			rv = sbf.toString();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rv;
	}
}
