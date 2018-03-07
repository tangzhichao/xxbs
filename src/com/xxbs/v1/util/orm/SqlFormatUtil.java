package com.xxbs.v1.util.orm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;


public class SqlFormatUtil {
	
	
	public static void main(String[] args) {
		//SqlFormatUtil.isDebug=true;
		//SqlFormatUtil.isErrPrint=false;
		test1();
		test2();
		test3();
		
		String sql="executeProc:["+"select * from A"+"]["+"sss"+"]["+"99"+"]";
		SqlFormatUtil.printFormatSql(sql);
	}
	
	private static void test1(){
		String sqlWhereInner=" where 1=1 ";
		String year="2017";
		String backfields  = " m.* ";
		String fromSql = " from (select int_gysid,txt_gysmc,gysattribute,"+
		" wm_concat(distinct typename)  as wztypename,wm_concat(distinct sydw_username)  as sydw_username,"+
		" wm_concat(distinct cgdw_username)  as cgdw_username,wm_concat(distinct zgcs_username)  as zgcs_username,"+
		" wm_concat(distinct sydw_username_ykp)  as sydw_username_ykp,wm_concat(distinct cgdw_username_ykp)  as cgdw_username_ykp,"+
		" wm_concat(distinct zgcs_username_ykp)  as zgcs_username_ykp "+
		" from("+
			" select s.id int_gysid,s.supplierfullname txt_gysmc,"+
			" decode(s.gysattribute,0,'框架协议',1,'单次合同供应商',s.gysattribute) as gysattribute,"+
			" t.typename,u1.lastname as sydw_username,u2.lastname as cgdw_username,u3.lastname as zgcs_username,"+
			"  uu1.lastname as sydw_username_ykp,uu2.lastname as cgdw_username_ykp,uu3.lastname as zgcs_username_ykp"+
			" from  uf_suppliers_info s"+
			" inner join uf_materials_type t on  ','||to_char(s.wztypeid)||',' like '%,'||t.id||',%' "+
			" inner join hrmresource u1 on ','||to_char(s.sydwid)||',' like '%,'||u1.id||',%' "+
			" inner join hrmresource u2 on ','||to_char(s.cgpjrid)||',' like '%,'||u2.id||',%' "+
			" inner join hrmresource u3 on ','||to_char(s.zzcskprid)||',' like '%,'||u3.id||',%' "+
			" left join formtable_main_208 f1 on f1.int_gysid=s.id "+(year.isEmpty()?"":" and substr(f1.date_cjsj,0,4)='"+year+"' ")+
				" and (select w1.currentnodetype from workflow_requestbase w1 where w1.requestid=f1.requestid "+
		        	" and ((select wlog1.endtime from ww_gyskh_workflow_log wlog1 where wlog1.year=substr(f1.date_cjsj,0,4)) is null "+
		            	" or to_date(w1.lastoperatedate||' '||w1.lastoperatetime,'yyyy-mm-dd,hh24:mi:ss') "+ 
		                	" &lt;=to_date((select wlog1.endtime from ww_gyskh_workflow_log wlog1 where wlog1.year=substr(f1.date_cjsj,0,4)),'yyyy-mm-dd,hh24:mi:ss')) "+
		        " )='3' "+//已考评只统计已完结和未超出截止时间的流程*/
			" left join hrmresource uu1 on uu1.id=f1.hr_ry "+
			" left join formtable_main_209 f2 on f2.int_gysid=s.id "+(year.isEmpty()?"":" and substr(f2.date_cjsj,0,4)='"+year+"' ")+
				" and (select w2.currentnodetype from workflow_requestbase w2 where w2.requestid=f2.requestid "+
		            " and ((select wlog2.endtime from ww_gyskh_workflow_log wlog2 where wlog2.year=substr(f2.date_cjsj,0,4)) is null "+ 
		            	" or to_date(w2.lastoperatedate||' '||w2.lastoperatetime,'yyyy-mm-dd,hh24:mi:ss') "+ 
		                	" &lt;=to_date((select wlog2.endtime from ww_gyskh_workflow_log wlog2 where wlog2.year=substr(f2.date_cjsj,0,4)),'yyyy-mm-dd,hh24:mi:ss')) "+
		         " )='3' "+//已考评只统计已完结和未超出截止时间的流程*/
			" left join hrmresource uu2 on uu2.id=f2.hr_ry "+
			" left join formtable_main_210 f3 on f3.int_gysid=s.id "+(year.isEmpty()?"":" and substr(f3.date_cjsj,0,4)='"+year+"' ")+
				" and (select w3.currentnodetype from workflow_requestbase w3 where w3.requestid=f3.requestid "+
		    		" and ((select wlog3.endtime from ww_gyskh_workflow_log wlog3 where wlog3.year=substr(f3.date_cjsj,0,4)) is null "+ 
		            	" or to_date(w3.lastoperatedate||' '||w3.lastoperatetime,'yyyy-mm-dd,hh24:mi:ss') "+
		                	" &lt;=to_date((select wlog3.endtime from ww_gyskh_workflow_log wlog3 where wlog3.year=substr(f3.date_cjsj,0,4)),'yyyy-mm-dd,hh24:mi:ss')) "+
		    	" )='3' "+//已考评只统计已完结和未超出截止时间的流程*/
			" left join hrmresource uu3 on uu3.id=f3.hr_ry "+
			sqlWhereInner+
		" ) "+
		" group by int_gysid,txt_gysmc,gysattribute) m ";
		String orderby = " m.int_gysid ";
		String primary = " m.int_gysid ";
		String sql="select "+backfields+fromSql+" order by "+orderby;
		
		SqlFormatUtil.printFormatSql(sql);
	}
	
	private static void test2(){
		Map mp=new HashMap();
		String sql="update interface_projectinfo_result t set t.c_org_id=:c_org_id,t.edit_time=sysdate,t.modifier=:modifier where t.record_id=:record_id";
		mp.put("record_id","1x0");
		mp.put("c_org_id","007_1");
		mp.put("modifier","20");
		SqlFormatUtil.printFormatSql(sql,mp);
	}
	private static void test3(){
		Object[] mp=new Object[3];
		String sql="update interface_projectinfo_result t set t.c_org_id=?,t.edit_time=sysdate,t.modifier=? where t.record_id=?";
		mp[0]="1x0";
		mp[1]="007_1";
		mp[2]="20";
		SqlFormatUtil.printFormatSql(sql,mp);
	}
	
	public static boolean isDebug=true;//是否输出打印
	public static boolean isErrPrint=false;//是否使用System.err打印
	
	/**
	 * 格式化无参的sql
	 */
	public static void printFormatSql(String sql){
		if(isDebug){
			String format = SqlFormatter.format(sql);
			print(format);
		}
	}
	/**
	 * 格式化无参的sql
	 * @throws IOException 
	 */
	public static void pagePrintFormatSql(String sql,JspWriter out) throws IOException{
			String format = SqlFormatter.format(sql);
			pagePrint(format,out);
	}
	
	/**
	 * 格式化:key格式入参的sql
	 */
	public static void printFormatSql(String sql,Map mp){
		if(isDebug){
			String format = SqlFormatter.format(sql,mp);
			print(format);
		}
	}
	/**
	 * 格式化:key格式入参的sql
	 * @throws IOException 
	 */
	public static void pagePrintFormatSql(String sql,Map mp,JspWriter out) throws IOException{
			String format = SqlFormatter.format(sql,mp);
			pagePrint(format,out);
	}
	
	/**
	 * 格式化?格式入参的sql
	 */
	public static void printFormatSql(String sql,Object[] mp){
		if(isDebug){
			String format = SqlFormatter.format(sql,mp);
			print(format);
		}
	}
	/**
	 * 格式化?格式入参的sql
	 * @throws IOException 
	 */
	public static void pagePrintFormatSql(String sql,Object[] mp,JspWriter out) throws IOException{
			String format = SqlFormatter.format(sql,mp);
			pagePrint(format,out);
	}
	
	private static void print(String format){
		if(isErrPrint){
			System.err.println("*****************************<<<<<<--Start--格式化SQL-->>>>>>*****************************");
			System.err.println(format);
			System.err.println("*****************************<<<<<<--End----格式化SQL-->>>>>>*****************************");
		}else{
			System.out.println("*****************************<<<<<<--Start--格式化SQL-->>>>>>*****************************");
			System.out.println(format);
			System.out.println("*****************************<<<<<<--End----格式化SQL-->>>>>>*****************************");
		}
	}
	private static void pagePrint(String format,JspWriter out ) throws IOException{
		out.println("<script>if(console){console.log(\"SQL>>>>>>>>>>>>>>>>:"+format+"\");}</script>");
	}
}
