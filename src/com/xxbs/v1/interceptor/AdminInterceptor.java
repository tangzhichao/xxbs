package com.xxbs.v1.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xxbs.v1.db.User;
import com.xxbs.v1.util.web.PrintUtil;

/**
 * 
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

	private final Logger	log	= Logger.getLogger(AdminInterceptor.class);

	private List<String>	excludedUrls;

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(request!=null){
			return true;
		}
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();

//		log.info("requestUri:" + requestUri);
//		log.info("contextPath:" + contextPath);
		
		// 根据拦截器设定，过滤不需要拦截的访问
		if (excludedUrls != null && excludedUrls.size() > 0) {
			for (String url : excludedUrls) {
				if (requestUri.endsWith(url)) {
					return true;
				}
			}
		}
		
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			log.info("Interceptor：跳转到login页面！");
			response.sendRedirect(contextPath + "/login.do");
			return false;
		} else{
			
			if (user.getRoleId()==1) {
				return true;
			}
			PrintUtil.outScript(response, "您没有权限访问该页面");
			return false;
		}

	}

}
