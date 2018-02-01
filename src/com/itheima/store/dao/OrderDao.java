package com.itheima.store.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.User;

/**
 * 订单dao层接口
 * 
 * @author xt
 *
 */
public interface OrderDao {
	/**
	 * 提交订单的方法
	 * 
	 * @param order
	 * @param con
	 * @throws SQLException
	 */
	void submitOrder(Order order, Connection con) throws SQLException;

	/**
	 * 提交订单项的方法
	 * 
	 * @param orderItem
	 */
	void submitOrderItem(Connection con, OrderItem orderItem) throws SQLException;

	/**
	 * 一个存储为提交订单项的方法
	 * 
	 * @param cartItem
	 * @throws SQLException
	 */
	void saveCartItem(Connection con, CartItem cartItem) throws SQLException;

	/**
	 * 从数据库中取cart
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	LinkedHashMap<String, CartItem> takeCart(Connection con, User user) throws SQLException;

	/**
	 * 将该用户cart信息的方法
	 * 
	 * @param user
	 */

	void tuncateCart(Connection con, User user) throws SQLException;

	/**
	 * 从数据库查询中订单数的方法
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	double findtotalcountbyuid(String uid) throws SQLException;

	/**
	 * 从数据库查询所需订单的信息
	 * 
	 * @param currPage
	 * @param uid
	 * @return
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	java.util.List<Order> findorderbyuid(String currPage, User user)
			throws NumberFormatException, SQLException, IllegalAccessException, InvocationTargetException;

	/**
	 * dao层中查询orderby oid
	 * 
	 * @param parseInt
	 * @return
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	Order findbyoid(String parseInt) throws SQLException, IllegalAccessException, InvocationTargetException;

	/**
	 * 在dao层用于更新订单信息的方法
	 * 
	 * @param order2
	 * @throws SQLException
	 */
	void UpdateOrder(Order order2) throws SQLException;

	/**
	 * 查询所有的订单
	 * 
	 * @return
	 * @throws SQLException 
	 */
	List<Order> findallOrder() throws SQLException;

	/**
	 * 根据订单的状态查询订单的方法
	 * 
	 * @return
	 * @throws SQLException 
	 */

	List<Order> findallOrderbystate(String state) throws SQLException;

	void UpdateOrder(String oid) throws SQLException;

}
