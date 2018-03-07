package com.xxbs.v1.util.orm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;

public class MyResultTransformer implements ResultTransformer{
	   @Override
	   public Object transformTuple(Object[] values, String[] columns) {
	      Map<String, Object> map = new LinkedHashMap<>();
	      int i = 0;
	      for(String column : columns){
	         map.put(column, values[i++]);
	      }
	      return map;
	   }
	   @Override
	   public List transformList(List list) {
	      return list;
	   }
	}
