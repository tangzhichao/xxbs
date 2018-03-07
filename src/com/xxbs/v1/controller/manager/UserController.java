package com.xxbs.v1.controller.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxbs.v1.db.User;
import com.xxbs.v1.db.UserDAO;
import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.math.MathUtil;
import com.xxbs.v1.util.orm.BasicDAO;
import com.xxbs.v1.util.orm.OrmUtil;
import com.xxbs.v1.util.page.PageUtil;
import com.xxbs.v1.util.web.ParamUtil;
import com.xxbs.v1.util.web.QueryUtil;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger	logger	= Logger.getLogger(UserController.class);

	@Autowired
	private UserDAO				userDAO;
	@Autowired
	private BasicDAO			basicDAO;

	/**
	 * 列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return "user/list";
	}

	/**
	 * 列表数据查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listQuery.do")
	public String listQuery(HttpServletRequest request, HttpServletResponse response) {
		String fields = " t1.* ";
		String from = " from user t1 ";
		String where = " where 1=1 ";
		String order = " t1.valid ";
		return QueryUtil.query(request, response, "user", fields, from, where, order);
	}

	/**
	 * 详情页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detail.do")
	public String detail(HttpServletRequest request, HttpServletResponse response) {
		User user = userDAO.findById(ParamUtil.getId(request));
		request.setAttribute("user", user);
		return "user/detail";
	}
	
	/**
	 * id数据查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/idQuery.do")
	public String idQuery(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			ParamUtil.getId(request);
			json.put("msg", "添加成功！");
			json.put("code", "200");
			return json.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("msg", "删除失败！" + e.getMessage());
			json.put("code", "400");
			return json.toString();
		}
	}

	/**
	 * 添加页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add.do")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		return "user/add";
	}

	/**
	 * 添加操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAction.do")
	public String addAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			json.put("msg", "添加成功！");
			json.put("code", "200");
			return json.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("msg", "删除失败！" + e.getMessage());
			json.put("code", "400");
			return json.toString();
		}
	}

	/**
	 * 删除操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAction.do")
	public String deleteAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			json.put("code", "200");
			json.put("msg", "删除成功！");
			return json.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("code", "400");
			json.put("msg", "删除失败！" + e.getMessage());
			return json.toString();
		}
	}

	/**
	 * 修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update.do")
	public String update(HttpServletRequest request, HttpServletResponse response) {
		return "user/update";
	}

	/**
	 * 修改操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAction.do")
	public String updateAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			json.put("code", "200");
			json.put("msg", "修改成功！");
			return json.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("code", "400");
			json.put("msg", "修改失败！" + e.getMessage());
			return json.toString();
		}
	}

	/**
	 * 导入界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/import.do")
	public String _import(HttpServletRequest request, HttpServletResponse response) {
		return "user/import";
	}

	/**
	 * 导入操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importAction.do")
	public String importAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			json.put("code", "200");
			json.put("msg", "导入成功！");
			return json.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("code", "400");
			json.put("msg", "导入失败！" + e.getMessage());
			return json.toString();
		}
	}

	/**
	 * 导出Excel
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportExcel.do")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response) {
		return "";
	}
}
