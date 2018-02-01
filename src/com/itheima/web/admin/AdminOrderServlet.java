package com.itheima.web.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Order;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * Servlet implementation class AdminOrderServlet
 * 
 * @param <Stirng>
 */
public class AdminOrderServlet<Stirng> extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 查看所有订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String allOrder(HttpServletRequest request, HttpServletResponse response) {
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		// 根据参数进行分类
		String state = request.getParameter("state");
		List<Order> list;
		try {
			if (state == null) {
				list = os.findallOrder();
			} else {
				// 查询订单信息根据state

				list = os.findallOrderbystate(state);

			}
			request.setAttribute("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/order/list.jsp";

	}

	/**
	 * 修改订单状态为发货
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String sendgoods(HttpServletRequest request, HttpServletResponse response) {
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		// 根据参数进行分类
		String oid = request.getParameter("oid");
        //调用底层方法实现修改
		try {
			os.UpdateOrder(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将页面转法到付款页面
		request.setAttribute("stata", 2);
		return "AdminOrderServlet?method=allOrder";

	}

}
