package com.itheima.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 一个模板类用于简化书写,减少servlet数量,将serveice方法抽取了出来
 * 模板设计模式
 * @author xt
 *
 */
public class BaseServlet extends HttpServlet {
   
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取方法名
		String method = req.getParameter("method");
		resp.setContentType("text/html;charset=utf-8");
		if (method == null || "".equalsIgnoreCase(method)) {
			return;
		}
		// 获取类的字节码文件
		try {
			Method method2 = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);

			String path = (String) method2.invoke(this, req, resp);
			// 转发
			if(path!=null){
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
