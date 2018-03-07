package com.xxbs.v1.util.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.io.IoUtil;
import com.xxbs.v1.util.orm.BasicDAO;
import com.xxbs.v1.util.orm.OrmUtil;
import com.xxbs.v1.util.str.ValidUtil;

public class AutoGenerater {

	private static final Map<String,List<Map>> ColumnConfigs=new HashMap<>();
	public static List<Map> getColumnConfig(String module) {
		if(Utils.isEmpty(ColumnConfigs, module)){
			ColumnConfigs.put(module, readColumnConfig(module));
		}
		return ColumnConfigs.get(module);
	}
	
	public static boolean isSearchCondition(String field,String searchType){
		return !field.isEmpty()&&!field.equalsIgnoreCase("none")&&!"id".equalsIgnoreCase(field)&&!searchType.isEmpty()&&!"none".equalsIgnoreCase(searchType);
	}
	
	
	private static List<Map> copyColumnConfig(String module){
		List<Map> columnConfig = getColumnConfig(module);
		ArrayList<Map> arrayList = new ArrayList<>();
		for (Map map : columnConfig) {
			arrayList.add(map);
		}
		return arrayList;
	}
	
	private static List<Map> readColumnConfig(String module){
		try {
			InputStream  inputStream = new FileInputStream(Utils.getWebRootPath()+"static/"+module+"/js/columnConfig.js");
			String json = IoUtil.readAllString(inputStream, 2048,"UTF-8");
			json=json.substring(json.indexOf("["), json.lastIndexOf("]")+1);
			JSONArray jsonArray = JSONArray.fromObject(json);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject=(JSONObject)jsonArray.get(i);
				jsonObject.put("show_order", i+"");
			}
			//Collections.sort(jsonArray, getSortBySearch());
			String defaultTable="";
			for (Object object : jsonArray) {
				JSONObject jsonObject=(JSONObject)object;
				String table=Utils.toString(jsonObject.get("table"));
				String field=Utils.toString(jsonObject.get("column"));
				String column=OrmUtil.toColumnName(field);
				String showName=Utils.toString(jsonObject.get("showName"));
				String style=Utils.toString(jsonObject.get("style"));
				String align=Utils.toString(jsonObject.get("align"));
				String searchType=Utils.toString(jsonObject.get("searchType"));
				Integer searchRowIndex=ValidUtil.getInteger(jsonObject.get("searchRowIndex"),1);
				Integer searchColumnCount=ValidUtil.getInteger(jsonObject.get("searchColumnCount"),1);
				String searchWhereKey=Utils.toString(jsonObject.get("searchWhereKey"));
				String refTable=Utils.toString(jsonObject.get("refTable"));
				String refId=Utils.toString(jsonObject.get("refId"));
				String refName=Utils.toString(jsonObject.get("refName"));
				String refWhere=Utils.toString(jsonObject.get("refWhere"));
				if(align.isEmpty()){
					align="center";
					jsonObject.put("align", align);
				}
				if(field.isEmpty()){
					field=Utils.toString(jsonObject.get("data"));
					jsonObject.put("column", field);
				}
				if(field.equalsIgnoreCase("id")){
					defaultTable=table;
				}
				if(table.isEmpty()){
					table=defaultTable;
					jsonObject.put("table", table);
				}
				if(refTable.isEmpty()){
					refTable="status_data";
					jsonObject.put("refTable", refTable);
				}
				if(refId.isEmpty()){
					refId="data_value";
					jsonObject.put("refId", refId);
				}
				if(refName.isEmpty()){
					refName="show_name";
					jsonObject.put("refName", refName);
				}
			}
			return jsonArray;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getColumnHeaderHtml(String module){
		StringBuilder columnHeaderHtml=new StringBuilder();
		List<Map> jsonArray = copyColumnConfig(module);
		Collections.sort(jsonArray, getSortByColumnHeader());
		for (Object object : jsonArray) {
			JSONObject jsonObject=(JSONObject)object;
			String showName=Utils.toString(jsonObject.get("showName"));
			String style=Utils.toString(jsonObject.get("style"));
			String align=Utils.toString(jsonObject.get("align"));
			if (!showName.isEmpty()) {
				String columnHtml="<th  style=\""+style+"\" align=\""+align+"\">"+showName+"</th>\r\n";
				columnHeaderHtml.append(columnHtml);
			}
		}
		return columnHeaderHtml.toString();
	}
	
	private static <T> Comparator<T> getSortBySearch() {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				Map map1=(Map)o1;
				Map map2=(Map)o2;
				Integer obj1 = ValidUtil.getInteger(map1.get("searchRowIndex"), 1);
				Integer obj2 = ValidUtil.getInteger(map2.get("searchRowIndex"), 1);
				if(obj1.equals(obj2)){
					Integer obj3 = ValidUtil.getInteger(map1.get("searchColumnIndex"), 1);
					Integer obj4 = ValidUtil.getInteger(map2.get("searchColumnIndex"), 1);
					return obj3-obj4;
				}
				return obj1-obj2;
			}
		};
	}
	private static <T> Comparator<T> getSortByAdd() {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				Map map1=(Map)o1;
				Map map2=(Map)o2;
				Integer obj1 = ValidUtil.getInteger(map1.get("addRowIndex"), 1);
				Integer obj2 = ValidUtil.getInteger(map2.get("addRowIndex"), 1);
				if(obj1.equals(obj2)){
					Integer obj3 = ValidUtil.getInteger(map1.get("addColumnIndex"), 1);
					Integer obj4 = ValidUtil.getInteger(map2.get("addColumnIndex"), 1);
					return obj3-obj4;
				}
				return obj1-obj2;
			}
		};
	}
	private static <T> Comparator<T> getSortByEdit() {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				Map map1=(Map)o1;
				Map map2=(Map)o2;
				Integer obj1 = ValidUtil.getInteger(map1.get("editRowIndex"), 1);
				Integer obj2 = ValidUtil.getInteger(map2.get("editRowIndex"), 1);
				if(obj1.equals(obj2)){
					Integer obj3 = ValidUtil.getInteger(map1.get("editColumnIndex"), 1);
					Integer obj4 = ValidUtil.getInteger(map2.get("editColumnIndex"), 1);
					return obj3-obj4;
				}
				return obj1-obj2;
			}
		};
	}
	private static <T> Comparator<T> getSortByColumnHeader() {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				Map map1=(Map)o1;
				Map map2=(Map)o2;
				Integer obj1 = ValidUtil.getInteger(map1.get("show_order"), 1);
				Integer obj2 = ValidUtil.getInteger(map2.get("show_order"), 1);
				return obj1-obj2;
			}
		};
	}
	
	public static String getSearchConditionHtml(ServletContext servletContext,String module){
		ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
		StringBuilder htmls=new StringBuilder("<div class=\"row\">");
		List<Map> jsonArray = copyColumnConfig(module);
		Collections.sort(jsonArray, getSortBySearch());
		int rowIndex=1;
		for (Object object : jsonArray) {
			JSONObject jsonObject=(JSONObject)object;
			String table=Utils.toString(jsonObject.get("table"));
			String field=Utils.toString(jsonObject.get("column"));
			String column=OrmUtil.toColumnName(field);
			String showName=Utils.toString(jsonObject.get("showName"));
			String searchType=Utils.toString(jsonObject.get("searchType"));
			Integer searchRowIndex=ValidUtil.getInteger(jsonObject.get("searchRowIndex"),1);
			Integer searchColumnCount=ValidUtil.getInteger(jsonObject.get("searchColumnCount"),1);
			String searchWhereKey=Utils.toString(jsonObject.get("searchWhereKey"));
			String refTable=Utils.toString(jsonObject.get("refTable"));
			String refId=Utils.toString(jsonObject.get("refId"));
			String refName=Utils.toString(jsonObject.get("refName"));
			String refWhere=Utils.toString(jsonObject.get("refWhere"));
			if (isSearchCondition(field, searchType)) {
				if(searchRowIndex>rowIndex){
					htmls.append("</div>");
					htmls.append("<div class=\"row\">");
					rowIndex=searchRowIndex;
				}
				String p1=
						"<div class=\"col-md-"+searchColumnCount+"\">"+
						"    <div class=\"form-group\">"+
						"        <label>"+showName+"</label>"+
						"        <div class=\"input-group\">";
				String p2=
						"<div class=\"col-md-"+searchColumnCount+"\">"+
								"    <div class=\"form-group\">"+
								"        <label>&nbsp;至</label>"+
								"        <div class=\"input-group\">";
				String e1=
						"        </div>"+
						"    </div>"+
						"</div>";
				String html="";
				if("text".equalsIgnoreCase(searchType)||"number".equalsIgnoreCase(searchType)){
					String numberStr="";
					if("number".equalsIgnoreCase(searchType)){
						numberStr=" number='true' ";
					}
					if("between".equalsIgnoreCase(searchWhereKey)){
						html+=p1;
						html+=
								"        	<input id=\""+field+"__begin\" name=\""+field+"__begin\" class=\"form-control form-filter\" placeholder=\"大于等于\" "+numberStr+"/>";
						html+=e1;
						html+=p2;
						html+=
								"        	<input id=\""+field+"__end\" name=\""+field+"__end\" class=\"form-control form-filter\" placeholder=\"小于等于\" "+numberStr+"/>";
					}else{
						html+=p1;
						html+=
								"        	<input id=\""+field+"\" name=\""+field+"\" class=\"form-control form-filter\" placeholder=\"请输入"+showName+"\" "+numberStr+"/>";
					}
					html+=e1;
				}else if("option".equalsIgnoreCase(searchType)){
					String sql;
					if(refTable.isEmpty()||refTable.equalsIgnoreCase("none")||refTable.equalsIgnoreCase("status_data")){
						sql="select data_value,show_name from status_data where module='"+module+"' and table_name='"+table+"' and column_name='"+column+"'";
					}else{
						sql="select "+refId+" as data_value,"+refName+" as show_name from `"+refTable+"` where 1=1 and "+refWhere+" ";
					}
					BasicDAO basicDAO = springContext.getBean(BasicDAO.class);
					List<Map<String,Object>> datas=basicDAO.findBySQL(sql, new ArrayList<Object>(0), null, null);
					html+=p1;
					html+=
							"        	<select id=\""+field+"\" name=\""+field+"\"  class=\"form-control form-filter\">"+
							"        		<option value=\"\">全部</option>";
					for (Map<String, Object> d : datas) {
						html+="<option value=\""+d.get("data_value")+"\">"+d.get("show_name")+"</option>";
					}
					html+=
							"        	</select>";
					html+=e1;
				}else if("browse".equalsIgnoreCase(searchType)){
					String sql="select "+refId+" as data_value,"+refName+" as show_name from `"+refTable+"` where 1=1 and "+refWhere+" ";
					BasicDAO basicDAO = springContext.getBean(BasicDAO.class);
					List<Map<String,Object>> datas=basicDAO.findBySQL(sql, new ArrayList<Object>(0), null, null);
					html+=p1;
					html+=
							"        	<select id=\""+field+"\" name=\""+field+"\"  class=\"form-control form-filter\">"+
							"        		<option value=\"\">全部</option>";
					for (Map<String, Object> d : datas) {
						html+="<option value=\""+d.get("data_value")+"\">"+d.get("show_name")+"</option>";
					}
					html+=
							"        	</select>";
					html+=e1;
				}else if("date".equalsIgnoreCase(searchType)){
					if("between".equalsIgnoreCase(searchWhereKey)){
						html+=p1;
						html+=
								"        	<input id=\""+field+"__begin\" name=\""+field+"__begin\" class=\"form-control form-filter form_date\" placeholder=\"大于等于\" date=\"date\"/>";
						html+=e1;
						html+=p2;
						html+=
								"        	<input id=\""+field+"__end\" name=\""+field+"__end\" class=\"form-control form-filter form_date\" placeholder=\"小于等于\" date=\"date\"/>";
					}else{
						html+=p1;
						html+=
								"        	<input id=\""+field+"\" name=\""+field+"\" class=\"form-control form-filter form_date\" placeholder=\"请输入"+showName+"\" date=\"date\"/>";
					}
					html+=e1;
				}
				htmls.append(html);
			}
		}
		htmls.append("</div>");
		return htmls.toString();
	}
}
