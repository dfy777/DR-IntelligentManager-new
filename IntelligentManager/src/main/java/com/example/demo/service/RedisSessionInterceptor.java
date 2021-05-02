package com.example.demo.service;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.pojo.DValueEnum;

public class RedisSessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
						Object handler) throws Exception {
		
		//先检验请求资源的类型
		String uri = request.getRequestURI();
		if (uri.endsWith("js")||uri.endsWith("css")||uri.endsWith("jpg")||
				uri.endsWith("svg")||uri.endsWith("jpg")||uri.endsWith("png")) {
			System.out.println(0);
			return true;
		}
		
		//System.out.println(1);
		//获得cookie
		Cookie cookie[] = request.getCookies();
		
		//判断cookie内容
		if (cookie == null) {
			response.sendRedirect(request.getContextPath());
			System.out.println(1);
			return false;
		}
		
		System.out.println(2);
		String cookie_userid = null;
		
		for (Cookie item : cookie) {
			if (item.getName().equals(DValueEnum.COOKIE_USER_ID.getValue())) {
				cookie_userid = item.getValue();
				break;
			}
		}
		
		System.out.println(3);
		//如果cookie中没有用户相关信息
		if (cookie_userid == null) {
			response.sendRedirect(request.getContextPath());
			//response.sendRedirect("localhost:8080/home");
			System.out.println(4);
			return false;
		}
		
		//无论访问地址是否正确，都先进行登录验证再分发
		HttpSession session = request.getSession();
		
		
		if (session.getAttribute(DValueEnum.LOGIN_USER_ID.getValue()) != null) {
			try {
				//在redis中检索
				String loginUserId = redisTemplate.opsForValue()
										.get(DValueEnum.LOGIN_USER.getValue() + cookie_userid);
				//System.out.println(loginUserId);
				//System.out.println(session.getId());
				if (loginUserId != null && loginUserId.replace("\"", "").equals(session.getId())) {
					return true;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(5);
		response401(response);
		return false;
	}
	
	private void response401(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		
		try {
			
			response.getWriter().print("错误页面");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
    			Object handler, ModelAndView modelAndView) throws Exception {
 
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
    			Object handler, Exception ex) throws Exception {
  
    }
}
