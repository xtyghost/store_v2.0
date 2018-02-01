package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBaen2;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.PaymentUtil;
import com.itheima.store.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	/*
	 * 一个提交购物的的方法
	 */
	public String subCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 从session取出cart
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		// 条用底层方法实现提交
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = new Order();
		order.setState(1);// 为付款
		order.setOid(UUIDUtils.getUuid());
		order.setTotal(cart.getTotalcount());
		order.setOrdertime(new Date());
		order.setUser((User) session.getAttribute("exsitUser"));
		Map<String, CartItem> map = cart.getMap();
		Collection<CartItem> collection = map.values();
		Map<String, OrderItem> map2 = new LinkedHashMap<String, OrderItem>();
		// 封装订单项
		for (CartItem cartItem : collection) {
			OrderItem item = new OrderItem();
			item.setCount(cartItem.getCount());
			item.setOid(order.getOid());
			item.setSubtotal(cartItem.getTotal());
			item.setPid(cartItem.getProduct().getPid());
			item.setItemid(UUIDUtils.getUuid());
			item.setProduct(cartItem.getProduct());
			map2.put(cartItem.getProduct().getPid(), item);
		}
		order.setMap(map2);
		try {
			os.subCart(order);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		// 清空购物车
		session.removeAttribute("cart");
		session.setAttribute("order", order);

		// 从定向的到订单页
		resp.sendRedirect("/store_v2.0/jsp/order_info.jsp");
		return null;

	}

	/**
	 * 查询我的订单的方法
	 */
	public String findMyOrders(HttpServletRequest req, HttpServletResponse resp) {
		String srcPage = req.getParameter("scrPage");
		String currPage = req.getParameter("currPage");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("exsitUser");

		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		PageBaen2 page;
		try {
			page = os.findMyOrders(currPage, user);
		} catch (NumberFormatException | SQLException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		req.setAttribute("orders", page);
		return "/jsp/order_list.jsp";

	}

	/**
	 * 这是一个根据oid查询指定order的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findbyoid(HttpServletRequest req, HttpServletResponse resp) {
		String oid = req.getParameter("oid");
		// 调用底层方法实现
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order;
		try {
			order = os.findbyoid(oid);
			req.setAttribute("order", order);
		} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";

	}

	/**
	 * 这是一个用于处理epay处理信息的反复的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response) {
		// 验证请求来源和数据有效性
		// 阅读支付结果参数说明
		// System.out.println("==============================================");
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		// hmac
		String hmac = request.getParameter("hmac");
		// 利用本地密钥和加密算法 加密数据
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				response.setContentType("text/html;charset=utf-8");
				try {
					response.getWriter().println("支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (r9_BType.equals("2")) {
				// 修改订单状态:
				OrderService os = (OrderService) BeanFactory.getBean("OrderService");
				try {
					Order order = os.findbyoid(r6_Order);
					order.setState(2);// 已付款
					os.UpdateOrder(order);
				} catch (IllegalAccessException | InvocationTargetException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 服务器点对点，来自于易宝的通知
				System.out.println("收到易宝通知，修改订单状态！");//
				// 回复给易宝success，如果不回复，易宝会一直通知
				try {
					response.getWriter().print("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			throw new RuntimeException("数据被篡改！");
		}
		// 跳转到等待页面
		return "/jsp/waiting.jsp";

	}

	/**
	 * 提交订单的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String submitOrder(HttpServletRequest req, HttpServletResponse resp) {
		String oid = req.getParameter("oid");
		String name = req.getParameter("name");
		String addressee = req.getParameter("addressee");
		String pd_FrpId1 = req.getParameter("pd_FrpId");
		System.out.println(pd_FrpId1);
		String telephone = req.getParameter("telephone");
		// 修改订单xinx
		// 调用底层方法实现
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order;
		Order order2 = new Order();
		order2.setAddressee(addressee);
		order2.setTelephone(telephone);
		order2.setOid(oid);
		order2.setBank(pd_FrpId1);
		User user = (User) req.getSession().getAttribute("exsitUser");
		order2.setUser(user);
		order2.setState(2);
		System.out.println(order2.toString());
		try {
			os.UpdateOrder(order2);
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";// 真实
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost/store_v2.0/OrderServlet?method=callback";
			String p9_SAF = "";
			String pa_MP = "";
			String pd_FrpId = pd_FrpId1;
			String pr_NeedResponse = "";

			String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
					p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

			// 将所有参数 发送给 易宝指定URL
			req.setAttribute("pd_FrpId", pd_FrpId);
			req.setAttribute("p0_Cmd", p0_Cmd);
			req.setAttribute("p1_MerId", p1_MerId);
			req.setAttribute("p2_Order", p2_Order);
			req.setAttribute("p3_Amt", p3_Amt);
			req.setAttribute("p4_Cur", p4_Cur);
			req.setAttribute("p5_Pid", p5_Pid);
			req.setAttribute("p6_Pcat", p6_Pcat);
			req.setAttribute("p7_Pdesc", p7_Pdesc);
			req.setAttribute("p8_Url", p8_Url);
			req.setAttribute("p9_SAF", p9_SAF);
			req.setAttribute("pa_MP", pa_MP);
			req.setAttribute("pr_NeedResponse", pr_NeedResponse);
			req.setAttribute("hmac", hmac);

			req.getRequestDispatcher("/jsp/comfirm.jsp").forward(req, resp);
		} catch (SQLException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/// 交钱跳转银行
		return null;

	}

}
