package com.xxbs.v1.util.page;

/**
 * 
 */
public class Page {
	private Integer iDisplayStart;//datatables框架分页字段，开始行数，从0开始
	private Integer iDisplayLength;//datatables框架分页字段，每页行数
	
	public Integer getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public Integer getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
}
