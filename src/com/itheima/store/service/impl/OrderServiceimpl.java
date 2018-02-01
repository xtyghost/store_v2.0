package com.itheima.store.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.taglibs.standard.lang.jstl.test.Bean2;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBaen2;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.DataSourceUtils;

/**
 * 管理订单的service层实现类
 * 
 * @author xt
 *
 */
public class OrderServiceimpl implements OrderService {

	/**
	 * 一个实现订单提交的方法
	 * 
	 * @throws Exception
	 * @throws SQLException
	 */
	@Override
	public void subCart(Order order) throws Exception {
		// TODO Auto-generated method stub
		// 获取执行体
		Connection con = DataSourceUtils.getConnection();
		try {
			con.setAutoCommit(false);
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			// 提交order
			od.submitOrder(order, con);

			// 提交order向
			Map<String, OrderItem> map = order.getMap();
			Collection<OrderItem> collection = map.values();
			for (OrderItem orderItem : collection) {
				od.submitOrderItem(con, orderItem);
			}

			DbUtils.commitAndCloseQuietly(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(con);
			throw new Exception("订单提交失败");
		}
	}

	@Override
	public void saveCart(Cart cart) {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection();
			con.setAutoCommit(false);
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			// 提交cartitem
			Map<String, CartItem> map = cart.getMap();
			Collection<CartItem> values = map.values();
			for (CartItem cartItem : values) {
				od.saveCartItem(con, cartItem);
			}
			DbUtils.commitAndCloseQuietly(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(con);
			throw new RuntimeException("订单存入失败");
		}

	}

	@Override
	public Cart takeCart(User user) {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		// 从数据库中取出caritem信息并重建表
		Cart cart = null;
		Connection con = DataSourceUtils.getConnection();
		LinkedHashMap<String, CartItem> map;
		try {
			cart = new Cart();
			map = od.takeCart(con, user);
			cart.setMap(map);
			od.tuncateCart(con, user);
			DbUtils.commitAndCloseQuietly(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(con);
			throw new RuntimeException("获取从数据库cart失败");
		}
		return cart;
	}

	@Override
	public PageBaen2 findMyOrders(String currPage, User user)
			throws NumberFormatException, SQLException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		PageBaen2 baen2 = new PageBaen2();
		ArrayList<Order> List = new ArrayList<>();
		// 当前页数
		baen2.setCurrPage(Integer.parseInt(currPage));
		// pagesize
		baen2.setPageSize(5);
		// 总order数
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		double totalCount = od.findtotalcountbyuid(user.getUid());
		baen2.setTotalCount(totalCount);
		// 从页数
		double d = Math.ceil(totalCount / baen2.getPageSize());
		baen2.setTotalPage((int) d);
		// list
		List<Order> list = od.findorderbyuid(currPage, user);
		baen2.setList(list);
		return baen2;
	}

	@Override
	public Order findbyoid(String parseInt) throws IllegalAccessException, InvocationTargetException, SQLException {
		// TODO Auto-generated method stub
		// 查询order
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		Order order = od.findbyoid(parseInt);
		return order;
	}

	@Override
	public void UpdateOrder(Order order2) throws SQLException {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.UpdateOrder(order2);
	}

	@Override
	public List<Order> findallOrder() throws SQLException {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		List<Order> list = od.findallOrder();
		return list;
	}

	@Override
	public List<Order> findallOrderbystate(String state) throws SQLException {
		// TODO Auto-generated method stub
				OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
				List<Order> list = od.findallOrderbystate(state);
				return list;
	}

	@Override
	public void UpdateOrder(String oid) throws SQLException {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.UpdateOrder(oid);
	}

	
	

	

}
