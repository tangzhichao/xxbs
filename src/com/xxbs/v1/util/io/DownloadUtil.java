package com.xxbs.v1.util.io;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxbs.v1.util.web.PrintUtil;

public class DownloadUtil {

	public static void outFile(HttpServletRequest request, HttpServletResponse response, InputStream input, String filename) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Content-Type", "application/octet-stream");
		try {
			filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		System.out.println("attachment;filename=" + filename);

		try (InputStream in = input; ServletOutputStream out = response.getOutputStream()) {
			byte[] b = new byte[1024 * 4];
			for (int i = in.read(b); i > 0; i = in.read(b)) {
				out.write(b, 0, i);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			PrintUtil.outScript(response, "文件错误");
		}
	}

	public static void showImage(HttpServletRequest request, HttpServletResponse response, InputStream input, String filename) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Content-Type", "image/png;image/jpeg;image/gif");
		// try {
		// filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
		// } catch (UnsupportedEncodingException e1) {
		// e1.printStackTrace();
		// }
		// response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		// System.out.println("attachment;filename=" + filename);

		try (InputStream in = input; ServletOutputStream out = response.getOutputStream()) {
			byte[] b = new byte[1024 * 4];
			for (int i = in.read(b); i > 0; i = in.read(b)) {
				out.write(b, 0, i);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			PrintUtil.outScript(response, "文件错误");
		}
	}

}
