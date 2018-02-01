package com.itheima.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.CookieUtils;

/**
 * 一个自动登录的filter
 */
public class SelfLoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public SelfLoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("exsitUser");
		// 当session不存在用户时自动登录
		if (user == null) {
			Cookie[] cookies = req.getCookies();
			Cookie autoLogin = CookieUtils.findCookie("autoLogin", cookies);
			if (autoLogin != null) {
				// 当可以从cookei获得用户信息时
				String value = autoLogin.getValue();
				value = URLDecoder.decode(value, "utf-8");
				String[] split = value.split("#");
				// 条用service层方法完成自动登录
				UserService us = new UserServiceImpl();
				try {
					User login = us.login(split[0], split[1]);
					if (login != null) {
						// 用户信息存入session中
						session.setAttribute("exsitUser", login);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
