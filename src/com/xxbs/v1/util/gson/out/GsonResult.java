/**
 * 
 */
package com.xxbs.v1.util.gson.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.annotations.Expose;
import com.xxbs.v1.util.gson.GsonCode;
import com.xxbs.v1.util.gson.GsonComm;

public class GsonResult {

	@Expose
	String resultMsg = "ok";

	@Expose
	Integer  resultCode ;
	
	@Expose
	long len;
	
	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public Object getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(Object resultInfo) {
		this.resultInfo = resultInfo;
	}

	@Expose
	Object resultInfo;
	
	public GsonResult(GsonResult r) {
		resultMsg = r.getResultMsg();
		resultCode = r.getResultCode();
		resultInfo = r.getResultInfo();
	}

	public GsonResult() {
	}
	/**
	 * 显示消息
	 * @param code
	 * @return
	 */
	public static String showMsg(Integer code){
		if(code != null && code > 0){
			GsonCode c = new GsonCode(); 
			Map<Integer, String> msgMap = c.getMsgMap();
			String msg = msgMap.get(code);
			if(!StringUtils.isBlank(msg))
				return msg;
		}
		return null;
	}
	public GsonResult( Integer code) {
		this.resultCode = code;
		if(code != null && code > 0){
			GsonCode c = new GsonCode(); 
			Map<Integer, String> msgMap = c.getMsgMap();
			String msg = msgMap.get(code);
			if(!StringUtils.isBlank(msg))
				this.resultMsg = msg;
		}
	}
	public GsonResult( Integer code, String msg) {
		this.resultCode = code;
		this.resultMsg = msg;
	}
	
	public GsonResult(Object data) {
		this.resultInfo = data instanceof ArrayList ? GsonComm.toJson(data, List.class, false) : data;
	}
	
	public GsonResult( Integer code, String msg, Object data) {
		this.resultCode = code;
		this.resultMsg = msg;
		this.resultInfo = data instanceof ArrayList ? GsonComm.toJson(data, List.class, false) : data;
	}

	public GsonResult( Integer code, String msg, Object data, long len) {
		this.resultCode = code;
		this.resultMsg = msg;
		this.resultInfo = data instanceof ArrayList ? GsonComm.toJson(data, List.class, false) : data;
		this.len = len;
	}
	
	public GsonResult( Object data, long len) {
		this.resultInfo = data instanceof ArrayList ? GsonComm.toJson(data, List.class, false) : data;
		this.len = len;
	}
	
	
	public void setData(Object data) {
		//String type=data.
		this.resultInfo = data instanceof ArrayList ? GsonComm.toJson(data, List.class, false) : data;
		
	}

	public long getLen() {
		return len;
	}

	public void setLen(long len) {
		this.len = len;
	}

	public boolean isExistsData() {
		if (this.resultInfo != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return
	 */
	public String objectToJsonStr() {
		return GsonComm.toJson(this, GsonResult.class, false);
	}

	/**
	 * @param s
	 * @return
	 */
	public static GsonResult jsonStrToObject(String s) {
		return GsonComm.fromJson(s, GsonResult.class);
	}
	
	

	public boolean isSuccess() {
		return GsonCode.SUCCESS == this.resultCode;
	}

	public long getDataLength() {
		if (this.resultInfo != null) {
			return this.len;
		} else {
			return 0;
		}
	}

	public static void main(String[] args) {
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map1=new HashMap<String,Object>();
//		map1.put("a", "aaa");
//		map1.put("b", "bbb");
//		
//		Map<String,Object> map2=new HashMap<String,Object>();
//		map2.put("x", "xxx");
//		map2.put("y", "yyy");
//		list.add(map1);
//		list.add(map2);
//		GsonResult r = new GsonResult("1", "success",list,6);
//		System.out.println(r.objectToJsonStr());
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("a", "aaa");
		map1.put("b", "bbb");
		
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("x", "<html>etwwsdfsdf4534534<style >a:{width:10px;}</stype></html>");
		map2.put("y", "yyy");
		list.add(map1);
		list.add(map2);
		GsonResult r = new GsonResult(1, "success",list,6);
		
		System.out.println(GsonComm.toJson(r, GsonResult.class, false));
		String s = r.objectToJsonStr();
		
		GsonResult gs = GsonComm.fromJson2(s, GsonResult.class);
		
		Object data = gs.getResultInfo();
		
		List<Map<String, Object>> lists = GsonComm.fromJsonArray(data, Map.class);
		
		System.out.println(lists);
	}
}


