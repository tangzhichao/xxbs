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
import com.xxbs.v1.util.web.QueryUtil;

@SuppressWarnings("unused")
@Controller
public class MainController {

	private static final Logger	logger	= Logger.getLogger(MainController.class);
	
	@Autowired
	private UserDAO			userDAO;
	@Autowired
	private BasicDAO			basicDAO;
	
	@RequestMapping(value = "/login.do")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		return "login";
	}
	@RequestMapping(value = "/doLogin.do")
	public String doLogin(HttpServletRequest request,HttpServletResponse response) {
		return "home";
	}
	@RequestMapping(value = "/home.do")
	public String home(HttpServletRequest request,HttpServletResponse response) {
		return "home";
	}

}
