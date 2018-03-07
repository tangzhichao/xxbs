package com.xxbs.v1.util.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import sun.net.www.protocol.http.AuthCache;
import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

import com.xxbs.v1.util.str.StringUtil;

@SuppressWarnings("restriction")
public class NetworkUtil {
	
	/**
	 * 获取当前访问客户的真实IP地址
	 * @param
	 * 		HttpServletRequest request
	 */
	public static String getClientTrueIp(HttpServletRequest request){
		String rv = "";
		
		String headerInfo = request.getHeader("x-forwarded-for");
		if(headerInfo==null || headerInfo.length()==0 || "unknown".equalsIgnoreCase(headerInfo)) {   
			rv = request.getHeader("Proxy-Client-IP");
		}
		if(headerInfo==null || headerInfo.length()==0 || "unknown".equalsIgnoreCase(headerInfo)) {   
			rv = request.getHeader("WL-Proxy-Client-IP");
		}
		if(headerInfo==null || headerInfo.length()==0 || "unknown".equalsIgnoreCase(headerInfo)) {   
			rv = request.getRemoteAddr();
		}
		
		return rv;
	}
	
	/***
	 * 获取物理地址
	 */
	public static String getMac() {
		return "94-DE-80-E2-28-A5";
//		StringBuilder macStr = new StringBuilder();
//		try {
//			Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
//			while (el.hasMoreElements()) {
//				byte[] mac = el.nextElement().getHardwareAddress();
//				if (mac == null || mac.length != 6)
//					continue;
//
//				for (int i = 0; i < mac.length; i++) {
//					macStr.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
//				}
//				macStr.append(";");
//			}
//		} catch (Exception exception) {
//			exception.printStackTrace();
//		}
//
//		if (macStr.toString().length() > 0) {
//			return macStr.toString().substring(0, macStr.toString().length() - 1);
//		} else {
//			return null;
//		}
	}

	public static void setNetworkProxyBySystem(NetworkBean networkBean) {
		if (isUserProxy(networkBean)) {
			if (networkBean.getType() == Proxy.Type.SOCKS) {
				System.getProperties().remove("http.proxyHost");
				System.getProperties().remove("http.proxyPort");

				System.getProperties().setProperty("socksProxyHost", networkBean.getAddress());
				System.getProperties().setProperty("socksProxyPort", networkBean.getPort());
			} else {
				System.getProperties().setProperty("http.proxyHost", networkBean.getAddress());
				System.getProperties().setProperty("http.proxyPort", networkBean.getPort());
			}

			Authenticator.setDefault(new BairuiAuthenticator(networkBean.getDomainAndUsername(), networkBean.getPassword()));
		} else if (networkBean != null) {
			System.getProperties().remove("proxySet");
			System.getProperties().remove("socksProxySet");

			System.getProperties().remove("http.proxyHost");
			System.getProperties().remove("http.proxyPort");

			System.getProperties().remove("socksProxyHost");
			System.getProperties().remove("socksProxyPort");
		}
	}

	public static void setKeepAliveAndAuthCache(boolean keepAlive) {
		System.setProperty("http.keepAlive", keepAlive + "");
		if (keepAlive) {
			AuthCacheValue.setAuthCache(new AuthCacheImpl());
		} else {
			AuthCacheValue.setAuthCache(new AuthCache() {
				public void remove(String pkey, AuthCacheValue entry) {
				}

				public void put(String pkey, AuthCacheValue value) {
				}

				public AuthCacheValue get(String pkey, String skey) {
					return null;
				}
			});
		}
	}


	//public static void setUseSystemProxies() {
		//System.setProperty("java.net.useSystemProxies", "true");
	//}

	public static boolean isUserProxy(NetworkBean networkBean) {
		return networkBean != null && networkBean.getType() != null && networkBean.getType() != Proxy.Type.DIRECT;
	}
	
	
	
	private static Map<String,String> json2Map(String json){
		json = json.substring(1,json.length()-1);
		json = json.replace("\"", "");
		String [] temps = json.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for(String temp : temps){
			String [] key = temp.split(":");
			if(key.length!=2){
				map.put(key[0],"" );
			}else{
				map.put(key[0],key[1] );	
			}
		}
		return map;
	}
	/**
	 * 根据ip获取城市名称
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getIpMap(String ip) throws Exception {
		// 新浪http://counter.sina.com.cn/ip?ip="+ip
		// http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=219.217.56.221
		// 搜狐IP地址查询接口（默认GBK）：http://pv.sohu.com/cityjson
		// 搜狐IP地址查询接口（可设置编码）：http://pv.sohu.com/cityjson?ie=utf-8
		// 搜狐另外的IP地址查询接口：http://txt.go.sohu.com/ip/soip
		// 有道http://www.youdao.com/smartresult-xml/search.s?type=ip&q="+ip
		URL url = new URL("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip="+ ip);
		URLConnection urlConnection = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "gbk"));
		String ips = "";
		String line = null;
		while ((line = br.readLine()) != null) {
			ips = ips + line;
		}
		ips = ips.replace("var remote_ip_info = ", "");
		ips = ips.replace(";", "");
		//System.out.println(ips);
		Map<String,String> map = json2Map(ips);
		
		if(map.get("country")!=null && !"".equals(map.get("country"))){
			map.put("country", StringUtil.decodeUnicode(map.get("country")));
		}
		if(map.get("province")!=null && !"".equals(map.get("province"))){
			map.put("province", StringUtil.decodeUnicode(map.get("province")));
		}
		if(map.get("city")!=null && !"".equals(map.get("city"))){
			map.put("city", StringUtil.decodeUnicode(map.get("city")));
		}else{
			map.put("city", "郑州");
		}
		return map;
	}
	
}
