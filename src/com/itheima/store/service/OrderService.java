package com.itheima.store.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBaen2;
import com.itheima.store.domain.User;

/**
 * 一个订单的接口
 * @author xt
 *
 */
public interface OrderService {

	void subCart(Order order) throws Exception;
    /**
     * 一个用于将未提交cart存入数据库的方法
     * @param cart
     */
	void saveCart(Cart cart);
	/**
	 * 从数据库中取出cart的方法
	 * @param user
	 * @return
	 */
	Cart takeCart(User user);
	/**
	 * 一个用户分页插座order信息的方法
	 * @param srcPage
	 * @param currPage
	 * @return
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	PageBaen2 findMyOrders(String currPage, User user) throws NumberFormatException, SQLException, IllegalAccessException, InvocationTargetException;
	/**
	 * 根据oid查出order的方法
	 * @param parseInt
	 * @return
	 * @throws SQLException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	
	Order findbyoid(String oid) throws IllegalAccessException, InvocationTargetException, SQLException;
	//订单付款时修改订单参数的方法
	void UpdateOrder(Order order2) throws SQLException;
	/**
	 * 在service层查询订单的信息的方法
	 * @return
	 * @throws SQLException 
	 */
	List<Order> findallOrder() throws SQLException;
	/**
	 * 根据订单的状态查询订单
	 * @return
	 * @throws SQLException 
	 */

	List<Order> findallOrderbystate(String state) throws SQLException;
	//修改订单状态为发货
	void UpdateOrder(String oid) throws SQLException;

	


}
