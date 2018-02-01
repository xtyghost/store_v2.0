package com.itheima.web.servlet;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.store.domain.Pagebean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.CookieUtils;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品分页的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findbycid(HttpServletRequest req, HttpServletResponse resp) {
		Pagebean pagebean = new Pagebean();
		Map<String, String[]> map = req.getParameterMap();
		try {
			// 封装pagebean
			BeanUtils.populate(pagebean, map);
			// 调用底层
			ProductService ps = (ProductService) new BeanFactory().getBean("ProductService");
			Pagebean bean = ps.finbycid(pagebean);
			req.setAttribute("pagebean", bean);
			// 处理浏览记录
			Cookie[] cookies = req.getCookies();
			Cookie cookie = CookieUtils.findCookie("history", cookies);
			if (cookie != null) {
				String history = cookie.getValue();
				String[] split = history.split("#");
				// 遍历历史
				ArrayList<Product> list = new ArrayList<Product>();
				for (String string : split) {
					Product product = ps.findbypid(string);
					list.add(product);
				}
				// 将历史记录存入req
				req.setAttribute("history", list);
			}
			// 将查询到的信息传回jsp
			return "/jsp/product_list.jsp";
		} catch (IllegalAccessException | InvocationTargetException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 查找商品详情的方法并可以完成历史记录
	 * 
	 */
	public String findbypid(HttpServletRequest req, HttpServletResponse resp) {
		String pid = req.getParameter("pid");
		ProductService ps = (ProductService) new BeanFactory().getBean("ProductService");
		try {
			Product product = ps.findbypid(pid);
			// 将商品详情存入req
			req.setAttribute("product", product);
			// 查询历史记录
			Cookie[] cookies = req.getCookies();
			Cookie history = CookieUtils.findCookie("history", cookies);
			if (history == null) {
				// 没有历史记录时
				Cookie cookie = new Cookie("history", product.getPid());
				cookie.setPath("/store_v2.0");
				cookie.setMaxAge(60 * 60 * 24 * 356);
				resp.addCookie(cookie);
			} else {
				String value = history.getValue();
				String[] split = value.split("#");
				// 将切割后的商品信息转成列表
				List<String> list1 = Arrays.asList(split);
				LinkedList<String> list = new LinkedList<>(list1);
				if (list.contains(product.getPid())) {
					list.remove(product.getPid());
				}
				if (list.size() > 6) {
					// 当列表长度大于6时将
					list.removeLast();
				}
				list.addFirst(product.getPid());
				StringBuilder builder = new StringBuilder();
				for (String string : list) {
					builder.append(string).append("#");
				}
				// 将多余#删除
				String substring = builder.toString().substring(0, builder.length());
				Cookie cookie = new Cookie("history", substring);
				cookie.setPath("/store_v2.0");
				cookie.setMaxAge(60 * 60 * 24 * 356);
				resp.addCookie(cookie);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 转发
		return "/jsp/product_info.jsp";

	}

	/**
	 * 一个用于清除浏览记录的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String removehistory(HttpServletRequest req, HttpServletResponse resp) {
		// 获取cookie
		Cookie[] cookies = req.getCookies();
		Cookie cookie = CookieUtils.findCookie("history", cookies);
		if (cookie != null) {
			String value = cookie.getValue();
			// 转成linkedlist
			String[] split = value.split("#");
			List<String> list = Arrays.asList(split);
			LinkedList<String> list2 = new LinkedList<>(list);
			String pid = req.getParameter("pid");
			System.out.println(pid);
			if (list2.contains(pid)) {
				list2.remove(pid);
			}
			StringBuilder builder = new StringBuilder();
			// 清除冲组weiStirng
			for (String string : list2) {
				builder.append(string).append("#");
			}
			// 如果list不为空
			String history = builder.toString();
			Cookie cookie2;
			if (list2.size() > 0) {
				history = builder.toString().substring(0, builder.length() - 1);
			}
			cookie2 = new Cookie("history", history);

			// 加回去
			cookie2.setPath("/store_v2.0");
			if (list2.size() == 0) {
				// 如果为空清空cookie
				cookie2.setMaxAge(0);
			} else {

				cookie2.setMaxAge(60 * 60 * 24 * 356);
			}
			resp.addCookie(cookie2);
		}
		return "jsp/product_jsp";

	}

}
