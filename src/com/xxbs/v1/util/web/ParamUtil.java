package com.xxbs.v1.util.web;

import javax.servlet.http.HttpServletRequest;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.str.ValidUtil;

public class ParamUtil {
	/*
	 def getOrderByStr(request,s="",ds=""):
    t="t."
    r=request.REQUEST
    if r.has_key("iSortCol_0") and r["iSortCol_0"] and r.has_key("mDataProp_"+r["iSortCol_0"]) and r["mDataProp_"+r["iSortCol_0"]] and not r["mDataProp_"+r["iSortCol_0"]].isdigit():
        if r.has_key("sSortDir_0") and r["sSortDir_0"] and r["sSortDir_0"]=="asc":
            s=s+" "+t+r["mDataProp_"+r["iSortCol_0"]]+" asc"
        else:
            s=s+" "+t+r["mDataProp_"+r["iSortCol_0"]]+" desc"
    else:
        if ds:
            s=ds
        else:    
            s=""
    print "order by sql:"+s
    return s
	 */
	public static String getOrderByStr(HttpServletRequest request,String t,String s,String ds) {
		t=Utils.isEmptyTrim(t)?"":(t+".");
		s=Utils.isEmptyTrim(s)?s:(s+",");
		String iSortCol_0 = Utils.toStringTrim(request.getParameter("iSortCol_0"));
		String mDataProp_ = Utils.toStringTrim(request.getParameter("mDataProp_"+iSortCol_0));
		String sSortDir_0 = Utils.toStringTrim(request.getParameter("sSortDir_0"));
		if(!iSortCol_0.isEmpty()&&!mDataProp_.isEmpty()&&!ValidUtil.isNumber(mDataProp_)){
			if(!sSortDir_0.isEmpty()&&"asc".equalsIgnoreCase(sSortDir_0)){
				 s=s+" "+t+mDataProp_+" asc ";
			}else{
				s=s+" "+t+mDataProp_+" desc ";
			}
		}else{
			if(!Utils.isEmpty(ds)){
				s=ds;
			}else{
				s="";
			}
		}
		return s;
	}
	
	public static String getSortColumn(HttpServletRequest request,String defaultColumn) {
		String iSortCol_0 = Utils.toStringTrim(request.getParameter("iSortCol_0"));
		String mDataProp_ = Utils.toStringTrim(request.getParameter("mDataProp_"+iSortCol_0));
		if(!iSortCol_0.isEmpty()&&!mDataProp_.isEmpty()&&!ValidUtil.isNumber(mDataProp_)){
			return mDataProp_;
		}else{
			return Utils.isEmptyTrim(defaultColumn)?"id":defaultColumn;
		}
	}
	public static String getSortColumn(HttpServletRequest request) {
			return getSortColumn(request,"");
	}
	public static String getSortType(HttpServletRequest request,String defaultType) {
		String sSortDir_0 = Utils.toStringTrim(request.getParameter("sSortDir_0"));
		return ValidUtil.getSortType(sSortDir_0, defaultType);
	}
	public static String getSortType(HttpServletRequest request) {
		String sSortDir_0 = Utils.toStringTrim(request.getParameter("sSortDir_0"));
		return ValidUtil.getSortType(sSortDir_0, "asc");
	}
	public static Integer getId(HttpServletRequest request) {
		return ValidUtil.getInteger(request.getParameter("id"), -1);
	}
	public static Integer getPageSize(HttpServletRequest request) {
		return ValidUtil.getInteger(request.getParameter("iDisplayLength"), 50);
	}
	public static Integer getPageIndex(HttpServletRequest request) {
		Integer iDisplayStart = ValidUtil.getInteger(request.getParameter("iDisplayStart"), 0);
		return iDisplayStart/getPageSize(request)+1;
	}

	

	
}