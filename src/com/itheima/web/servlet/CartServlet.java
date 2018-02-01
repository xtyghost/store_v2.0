package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Product;
import com.itheima.store.domain.User;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * 一个用于管理订单的servlet
 */
public class CartServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 将商品添加到购物车的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	public String addCartitem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String pid = req.getParameter("pid");
		// 查出product
		ProductService ps = (ProductService) new BeanFactory().getBean("ProductService");

		String quantity = req.getParameter("quantity");
		if (ps != null) {
			try {
				Product product = ps.findbypid(pid);
				// 或session中的cart数据
				HttpSession session = req.getSession();
				// cart信息weilinkedHashMap
				@SuppressWarnings("unchecked")
				Cart cart = (Cart) session.getAttribute("cart");
				if (cart == null) {
					cart = new Cart();
					cart.setMap(new LinkedHashMap<String, CartItem>());
				}
				// 计算订单项数量
				CartItem oldItem = cart.getMap().get(pid);
				if (oldItem != null) {
					int oldcount = oldItem.getCount();
					oldItem.setCount(oldcount + Integer.valueOf(quantity));
				} else {
					// 如果没有该商品的记录
					User user = (User) session.getAttribute("exsitUser");
					String name = null;
					if (user != null) {
						name = user.getUsername();
					}
					CartItem item = new CartItem(product, Integer.valueOf(quantity), name);
					cart.getMap().put(pid, item);
				}
				session.setAttribute("cart", cart);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		resp.sendRedirect("/store_v2.0/jsp/cart.jsp");
		return null;

	}

	/**
	 * 将商品从购物车中移除的方法
	 */
	public String deleteCartitem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获取参数
		String pid = req.getParameter("pid");
		// 从session中请出
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		cart.getMap().remove(pid);
		resp.sendRedirect("/store_v2.0/jsp/cart.jsp");
		return null;

	}

	/*
	 * 清空购物车的功能
	 */
	public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<String, CartItem> map = cart.getMap();
			if (map != null) {
				map.clear();
			}
		}
		return "/jsp/cart.jsp";

	}

	/**
	 * 处理用户在购物车中改变数据的方法
	 */
	public String changCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String pid = (String) req.getParameter("pid");
		String count = req.getParameter("count");
		//修改session中数据
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		CartItem cartItem = cart.getMap().get(pid);
		cartItem.setCount(Integer.parseInt(count));
		return "/jsp/cart.jsp";

	}
	
}
