package com.xxbs.v1.util.gson;

import java.util.HashMap;
import java.util.Map;

public class GsonCode {
	/**
	 * 操作成功
	 */
	public static final int SUCCESS = 200;
	/**
	 * 普通错误
	 */
	public static final int BAD_REQUEST = 400;
	/**
	 * 系统异常
	 */
	public static final int SERVER_ERROR = 500;
	
	/**
	 * 未通过用户认证
	 */
	public static final int UNAUTHORIZED = 401;
	/**
	 * 账号或密码重复
	 */
	public static final int ACCOUNT_REPEAT = 402;
	/**
	 * 两次密码输入不一致
	 */
	public static final int TWO_PWDS_DIFFER = 403;
	/**
	 * 未找到资源
	 */
	public static final int NOT_FOUND = 404;
	/**
	 * 帐号或密码不存在
	 */
	public static final int ACCOUNT_NO_EXIT = 405;
	/**
	 * 原密码错误
	 */
	public static final int OLD_PWDS_ERROR = 406;
	/**
	 * 账号已被注册
	 */
	public static final int ACCOUNT_REGISTERED = 407;
	/**
	 * 您还未登录，请先登录
	 */
	public static final int ACCOUNT_NOT_LOGIN = 408;
	/**
	 * 请先选择需要筛选的内容
	 */
	public static final int NO_SCREEN_CONT = 409;
	/**
	 * 参数丢失
	 */
	public static final int PARAM_LOSS = 410;
	
	/**
	 * 签名错误
	 */
	public static final int SIGN_ERROR = 411;
	/**
	 * 没有权限
	 */
	public static final int NOT_PERMISSION = 412;
	/**
	 * 该账号未注册
	 */
	public static final int ACCOUNT_NOT_REG = 413;
	
	/**
	 * 验证码错误
	 */
	public static final int VERIFICATION_ERROR = 414;
	
	/**
	 * 账号或密码重复
	 */
	//public static final String ACCOUNT_REPEAT = "账号或密码重复";
	/**
	 * 两次密码输入不一致
	 */
	//public static final String TWO_PWDS_DIFFER = "两次密码输入不一致";
	/**
	 * 账号或密码不存在
	 */
	//public static final String ACCOUNT_NO_EXIT = "帐号或密码不存在";
	/**
	 * 您还未登录，请先登录
	 */
	//public static final String ACCOUNT_NOT_LOGIN = "您还未登录，请先登录";
	/**
	 * 请先选择需要筛选的内容
	 */
	//public static final String NO_SCREEN_CONT = "请先选择需要筛选的内容";
	/**
	 * 个人资料保存失败
	 */
	//public static final String ACCOUNT_SAVE_NO_SUCCESS = "个人资料保存失败";
	/**
	 * 参数丢失
	 */
	//public static final String PARAM_LOSS = "参数丢失";
	/**
	 * 系统异常
	 */
	public static final String SYSTEM_ERROR = "系统异常";
	
	private Map<Integer, String> msgMap = new HashMap<>();
	
	
	public Map<Integer, String> getMsgMap() {
		msgMap.put(200, "OK");
		msgMap.put(400, "操作失败");
		msgMap.put(401, "未通过用户认证");
		msgMap.put(402, "账号或密码重复");
		msgMap.put(403, "两次密码输入不一致");
		msgMap.put(404, "未找到资源");
		msgMap.put(405, "帐号或密码不能为空");
		msgMap.put(406, "原密码错误");
		msgMap.put(407, "账号已被注册");
		msgMap.put(408, "您还未登录，请先登录");
		msgMap.put(409, "请先选择需要筛选的内容");
		msgMap.put(410, "参数丢失");
		msgMap.put(411, "签名错误");
		msgMap.put(412, "没有权限");
		msgMap.put(413, "该账号未注册");
		msgMap.put(414, "验证码错误");
		msgMap.put(500, "系统异常");
		return msgMap;
	}
}
