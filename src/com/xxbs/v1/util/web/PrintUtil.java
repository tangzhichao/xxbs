package com.xxbs.v1.util.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.xxbs.v1.util.Utils;

public class PrintUtil {

	public static void outScript(HttpServletResponse response, String msg) {
		PrintWriter out;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			out.print("<script>");
			out.print("if(top&&top.Dialog){top.Dialog.alert('" + msg + "');}else{alert('" + msg + "');}");
			out.print("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void outScript(HttpServletResponse response, String msg,String href) {
		PrintWriter out;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			out.print("<script>");
			out.print("if(top&&top.Dialog){top.Dialog.alert('" + msg + "');}else{alert('" + msg + "');}");
			if (Utils.isEmpty(href)) {
//				href="window.location.href";
				href="document.referrer";
			}else{
				href="'"+href+"'";
			}
			out.print("window.location.href=" + href + ";");
			out.print("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
