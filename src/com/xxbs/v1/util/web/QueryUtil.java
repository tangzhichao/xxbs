package com.xxbs.v1.util.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.gson.JsonUtil;
import com.xxbs.v1.util.orm.BasicDAO;
import com.xxbs.v1.util.orm.OrmUtil;
import com.xxbs.v1.util.str.ValidUtil;

public class QueryUtil {
	

	public static String datatablesResponse(HttpServletRequest request,HttpServletResponse response,List<?> tableData,Integer tableCount){
		response.setCharacterEncoding("utf-8");
		String result="{\"sEcho\":"+request.getParameter("sEcho")+","+
	    		"\"iTotalRecords\":"+(tableCount)+","+
	    		"\"iTotalDisplayRecords\":"+(tableCount)+","+
	    		"\"data\":"+JsonUtil.toArray(tableData)+","+
	    		"\"iDisplayStart\":"+(ValidUtil.getInteger(request.getParameter("iDisplayStart"), 0))+","+
	    		"\"iDisplayLength\":"+(ValidUtil.getInteger(request.getParameter("iDisplayLength"), 50))+
	    		"}";
		System.out.println(result);
	    return result;
	}

	public static String query(HttpServletRequest request,HttpServletResponse response,
			String module,String fields,String from,String where,String order){
		
		ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()); 
		BasicDAO basicDAO = springContext.getBean(BasicDAO.class);
		
		String sortColumn = ParamUtil.getSortColumn(request);
		//String sortType = ParamUtil.getSortType(request);
		String sortTableAs = "t1";
		
		List<Object> values=new ArrayList<>();
		List<Map> columnConfig = AutoGenerater.getColumnConfig(module);
		for (Map map : columnConfig) {
			String table=Utils.toString(map.get("table"));
			String field=Utils.toString(map.get("column"));
			String column=OrmUtil.toColumnName(field);
			String searchType=Utils.toString(map.get("searchType"));
			String searchWhereKey=Utils.toString(map.get("searchWhereKey"));
			String refTable=Utils.toString(map.get("refTable"));
			String refName=Utils.toString(map.get("refName"));
			if("option".equalsIgnoreCase(searchType)||"browse".equalsIgnoreCase(searchType)){//需要自动左连接查询
				String table_as=refTable+"_"+column;
				fields+=(" ,"+table_as+"."+refName+" as "+column+"_name ");
				if("option".equalsIgnoreCase(searchType)){
					from+=(" left join "+refTable+" "+table_as+" on "+table_as+".table_name='"+table+
							"' and "+table_as+".column_name='"+column+"' and "+table_as+".module='"+module+
							"' and "+table_as+".data_value=t1."+column+" ");
				}else{
					from+=(" left join "+refTable+" "+table_as+" on "+table_as+".id=t1."+column+" ");
				}
				
				if(!Utils.isEmpty(sortColumn)&&sortColumn.equalsIgnoreCase(field)){
					sortTableAs=table_as;
				}
			}
			
			if(AutoGenerater.isSearchCondition(field, searchType)){
				if("between".equalsIgnoreCase(searchWhereKey)){
					String value__begin = request.getParameter(column+"__begin");
					if (!Utils.isEmpty(value__begin)) {
						if ("date".equalsIgnoreCase(searchType)) {
							where+=" and date_format("+column+",'%Y-%m-%d')>=? ";
						}else{
							where+=" and "+column+">=? ";
						}
						values.add(value__begin);
					}
					String value__end = request.getParameter(column+"__end");
					if (!Utils.isEmpty(value__end)) {
						if ("date".equalsIgnoreCase(searchType)) {
							where+=" and date_format("+column+",'%Y-%m-%d')<=? ";
						}else{
							where+=" and "+column+"<=? ";
						}
						values.add(value__end);
					}
					
				}else{
					String value = request.getParameter(column);
					if (!Utils.isEmpty(value)) {
						if("like".equalsIgnoreCase(searchWhereKey)){
							where+=" and "+column+" like concat('%',?,'%') ";
						}else{
							where+=" and "+column+"=? ";
						}
						values.add(value);
					}
				}
			}
			
			
		}
		System.out.println("PageSize============"+ParamUtil.getPageSize(request));
		System.out.println("PageIndex============"+ParamUtil.getPageIndex(request));
		
		order=ParamUtil.getOrderByStr(request, sortTableAs, order, "id desc");
		
		String dataSql="select " + fields+from+" "+where+" order by "+order;
		String countSql="select count(*) "+from+" "+where;
		List<Map<String,Object>> list =basicDAO.findBySQL(dataSql, values,ParamUtil.getPageSize(request) , ParamUtil.getPageIndex(request));
		Integer count =basicDAO.findCountBySQL(countSql, values);
		return QueryUtil.datatablesResponse(request,response, list, count);
	}
	public static JSONObject queryById(HttpServletRequest request,HttpServletResponse response,
			String module,String fields,String from,String id){
		
		String where=" where t1.id=? ";
		
		ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()); 
		BasicDAO basicDAO = springContext.getBean(BasicDAO.class);
		
		List<Object> values=new ArrayList<>();
		values.add(id);
		List<Map> columnConfig = AutoGenerater.getColumnConfig(module);
		for (Map map : columnConfig) {
			String table=Utils.toString(map.get("table"));
			String field=Utils.toString(map.get("column"));
			String column=OrmUtil.toColumnName(field);
			String searchType=Utils.toString(map.get("searchType"));
			String refTable=Utils.toString(map.get("refTable"));
			String refName=Utils.toString(map.get("refName"));
			if("option".equalsIgnoreCase(searchType)||"browse".equalsIgnoreCase(searchType)){//需要自动左连接查询
				String table_as=refTable+"_"+column;
				fields+=(" ,"+table_as+"."+refName+" as "+column+"_name ");
				if("option".equalsIgnoreCase(searchType)){
					from+=(" left join "+refTable+" "+table_as+" on "+table_as+".table_name='"+table+
							"' and "+table_as+".column_name='"+column+"' and "+table_as+".module='"+module+
							"' and "+table_as+".data_value=t1."+column+" ");
				}else{
					from+=(" left join "+refTable+" "+table_as+" on "+table_as+".id=t1."+column+" ");
				}
				
			}
			
			
			
		}
		String dataSql="select " + fields+from+" "+where;
		List<Map<String,Object>> list =basicDAO.findBySQL(dataSql, values,null,null);
		return Utils.isEmpty(list)?null:JsonUtil.toObject(list.get(0));
	}
	
}
