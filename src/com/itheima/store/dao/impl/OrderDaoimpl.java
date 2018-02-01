package com.itheima.store.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.Product;
import com.itheima.store.domain.User;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.DataSourceUtils;

/**
 * 订单的dao层实现类
 * 
 * @author xt
 *
 */
public class OrderDaoimpl implements OrderDao {

	@Override
	public void submitOrder(Order order, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders (oid,ordertime,total,state,uid) values(?,?,?,?,?)";
		Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getUser().getUid() };
		runner.update(con, sql, params);
	}

	@Override
	public void submitOrderItem(Connection con, OrderItem orderItem) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitem (itemid,count,subtotal,pid,oid) values (?,?,?,?,?)";
		Object[] params = { orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(), orderItem.getPid(),
				orderItem.getOid() };
		runner.update(con, sql, params);

	}

	@Override
	public void saveCartItem(Connection con, CartItem cartItem) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner();
		String sql = "insert into cartItem (uname,pid,total,count) values (?,?,?,?)";
		Product product = cartItem.getProduct();
		Object[] params = { cartItem.getUname(), product.getPid(), cartItem.getTotal(), cartItem.getCount() };
		runner.update(con, sql, params);
	}

	@Override
	public LinkedHashMap<String, CartItem> takeCart(Connection con, User user) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner();
		String sql = "select pid,count,uname from CartItem where uname=? ";
		String uname = user.getUsername();
		List<Object[]> list = runner.query(con, sql, new ArrayListHandler(), uname);
		LinkedHashMap<String, CartItem> map = new LinkedHashMap<>();
		for (Object[] objects : list) {
			CartItem item = new CartItem();
			// 查询produt
			ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
			item.setProduct(pd.finbypid(objects[0].toString()));
			item.setCount((int) objects[1]);
			item.setUname(uname);
			map.put((String) objects[0], item);
		}
		return map;
	}

	@Override
	public void tuncateCart(Connection con, User user) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner();
		String sql = "delete from CartItem where uname=?";
		runner.update(con, sql, user.getUsername());
	}

	@Override
	public double findtotalcountbyuid(String uid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select count(*) from orders where uid=?";
		Long lon = (Long) runner.query(sql, new ScalarHandler(), uid);
		return lon.doubleValue();
	}

	@Override
	public List<Order> findorderbyuid(String currPage, User user)
			throws NumberFormatException, SQLException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from  orders where uid=? order by ordertime desc  limit ?,5";
		List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(),
				(Integer.parseInt(currPage) - 1) * 5);
		for (Order order : list) {
			String sql2 = "select * from OrderItem o inner join Product p where o.oid=? and o.pid=p.pid order by subtotal desc";
			List<Map<String, Object>> list2 = runner.query(sql2, new MapListHandler(), order.getOid());
			LinkedHashMap<String, OrderItem> map2 = new LinkedHashMap<String, OrderItem>();
			for (Map<String, Object> map : list2) {
				OrderItem item = new OrderItem();
				BeanUtils.populate(item, map);
				Product product = new Product();
				BeanUtils.populate(product, map);
				item.setProduct(product);
				map2.put(item.getItemid(), item);
			}
			order.setMap(map2);
		}
		return list;
	}

	@Override
	public Order findbyoid(String oid) throws SQLException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from Orders where oid=?";
		Order order = runner.query(sql, new BeanHandler<Order>(Order.class), oid);
		// 查询order中订单项
		String sql2 = "select * from OrderItem o inner join product p on o.pid=p.pid and o.oid=? order by subtotal desc";
		List<Map<String, Object>> list = runner.query(sql2, new MapListHandler(), oid);
		LinkedHashMap<String, OrderItem> map2 = new LinkedHashMap<String, OrderItem>();
		for (Map<String, Object> map : list) {
			OrderItem item = new OrderItem();
			BeanUtils.populate(item, map);
			Product product = new Product();
			BeanUtils.populate(product, map);
			item.setProduct(product);
			map2.put(item.getItemid(), item);
		}
		order.setMap(map2);
		return order;
	}

	@Override
	public void UpdateOrder(Order order2) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "update Orders set name=?,addressee=?,telephone=?,state=?,bank=? where oid=? ";
		runner.update(sql, order2.getUser().getName(), order2.getAddressee(), order2.getTelephone(), order2.getState(),
				order2.getBank(), order2.getOid());
	}

	@Override
	public List<Order> findallOrder() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from Orders order by ordertime";
		List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class));
		return list;
	}

	@Override
	public List<Order> findallOrderbystate(String state) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from Orders  where state=? order  by ordertime";
		List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class), Integer.parseInt(state));
		return list;
	}

	@Override
	public void UpdateOrder(String oid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="update orders set state=3 where oid=?";
		runner.update(sql,oid);
	}

}
