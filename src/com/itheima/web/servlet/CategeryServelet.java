package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategeryServelet 一个关于菜单栏的类
 */
public class CategeryServelet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException 
	 * 查询所有菜单栏的分类的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws  
	 */
	public String findmenu(HttpServletRequest req, HttpServletResponse resp) throws IOException  {
		List<Category> clist;
		try {
			CategoryService cs = (CategoryService) new BeanFactory().getBean("CategoryService");
			clist = cs.findmenu();
		} catch (SQLException e) {
			// TODO Auto-generated cat
			e.printStackTrace();
			throw new RuntimeException();
		}
		JSONArray jsonArray = JSONArray.fromObject(clist);
		//设置文件接收类型为document
		resp.setContentType("text/html;charset=Utf-8");
		resp.getWriter().println(jsonArray.toString());
		return null;
	}
}
