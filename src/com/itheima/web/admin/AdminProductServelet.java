package com.itheima.web.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Category;
import com.itheima.store.domain.PageBaen2;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * Servlet implementation class AdminProductServelet
 */
public class AdminProductServelet extends BaseServlet {
	/**
	 * 未下架商品的分页显示方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String chekproduct(HttpServletRequest request, HttpServletResponse response) {
		// 接收当前分页
		String currPage = request.getParameter("currPage");
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		try {
			PageBaen2 bean = ps.findall(currPage);
			request.setAttribute("pageBean", bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";

	}
	/**
	 * 下架商品的分页显示方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String xchekproduct(HttpServletRequest request, HttpServletResponse response) {
		// 接收当前分页
		String currPage = request.getParameter("currPage");
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		try {
			PageBaen2 bean = ps.findxall(currPage);
			request.setAttribute("pageBean", bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/product/pushDownlist.jsp";
		
	}

	/**
	 * 转发到添加页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String addProductUI(HttpServletRequest request, HttpServletResponse response) {
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		try {
			List<Category> list = cs.findmenu();
			request.setAttribute("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 商品分类的信息

		return "/admin/product/add.jsp";

	}

	/**
	 * 增加商品信息的方
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String addProduct(HttpServletRequest request, HttpServletResponse response) {

		
		return "/admin/product/list.jsp";

	}

	/**
	 * 编辑商品信息的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String editProduct(HttpServletRequest request, HttpServletResponse response) {
		// 接收当前分页
		String currPage = request.getParameter("currPage");
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		try {
			PageBaen2 bean = ps.findall(currPage);
			request.setAttribute("pageBean", bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";

	}

	/**
	 * 删除商品信息的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String deletProduct(HttpServletRequest request, HttpServletResponse response) {
		// 接收当前分页
		String pid = request.getParameter("pid");
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
        try {
        	ps.deletbypid(pid);
			response.sendRedirect("/store_v2.0/AdminProductServelet?method=chekproduct&currPage=1");
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	/**
	 * 上架商品信息的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String upProduct(HttpServletRequest request, HttpServletResponse response) {
		// 接收当前分页
		String pid = request.getParameter("pid");
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		try {
			ps.upbypid(pid);
			response.sendRedirect("/store_v2.0/AdminProductServelet?method=xchekproduct&currPage=1");
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
