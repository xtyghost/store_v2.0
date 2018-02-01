package com.itheima.store.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.Category;

/**
 * 关于菜单栏显示的接口
 * 
 * @author xt
 *
 */
public interface CategoryService {
	public List<Category> findmenu() throws SQLException;

	/**
	 * admin用于add商品列表的方法
	 * 
	 * @param cname
	 * @throws SQLException
	 */
	public void save(String cname) throws SQLException;

	/**
	 * admin用于删除商品信息的方法
	 * 
	 * @param cname
	 */
	public void delete(String cname) throws SQLException;
	/**
	 * admin用于修改商品列表信息的方法
	 * 
	 * @param cname
	 * @throws SQLException 
	 */

	public void changbycid(Category category) throws SQLException;
	
}
