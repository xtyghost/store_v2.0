package com.itheima.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * 界面的servelt
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String index(HttpServletRequest req, HttpServletResponse resp) {
		// 创建对象
		ProductService productService = (ProductService) new BeanFactory().getBean("ProductService");
		// 查询新门商品
		List<Product> nlist;
		try {
			nlist = productService.newPL();
			if (nlist != null) {
				req.setAttribute("nlist", nlist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 查询最热商品
		List<Product> hlist;
		try {
			hlist = productService.hotPL();
			if (hlist != null) {
				req.setAttribute("hlist", hlist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 向页面转发
		return "/jsp/index.jsp";
	}

}
