package com.itheima.store.utils;

import javax.servlet.http.Cookie;

/**
 * 一个关于cookie的工具类
 * 
 * @author xt
 *
 */
public class CookieUtils {
	private CookieUtils() {

	}

	/**
	 * 从cookie数组中查询指定name的cookie
	 * 
	 * @param string
	 * @param cookies
	 * @return
	 */
	public static Cookie findCookie(String string, Cookie[] cookies) {
		// TODO Auto-generated method stub
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if (string.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

}
