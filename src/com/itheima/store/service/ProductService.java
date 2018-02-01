package com.itheima.store.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.PageBaen2;
import com.itheima.store.domain.Pagebean;
import com.itheima.store.domain.Product;

public interface ProductService {
	/**
	 * 查找最新商品类表
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<Product> newPL() throws SQLException;

	/**
	 * 查找最热商品列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<Product> hotPL() throws SQLException;

	/**
	 * 一个商品分页的方法
	 * 
	 * @param pagebean
	 * @return
	 * @throws SQLException
	 */
	Pagebean finbycid(Pagebean pagebean) throws SQLException;

	/**
	 * 一个查询商品详情用pid
	 * 
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	Product findbypid(String pid) throws SQLException;

	/**
	 * 查询未下架的商品内容
	 * 
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	PageBaen2 findall(String currPage) throws SQLException;

	/**
	 * 查询下架的商品内容
	 * 
	 * @param currPage
	 * @return
	 * @throws SQLException
	 */
	PageBaen2 findxall(String currPage) throws SQLException;

	/**
	 * 一个用于添加商品信息的方法
	 * 
	 * @param product
	 * @throws SQLException
	 */
	void addProduct(Product product) throws SQLException;

	/**
	 * 一个根据pid删除商品信息的方法
	 * 
	 * @param pid
	 * @throws SQLException
	 */
	void deletbypid(String pid) throws SQLException;

	/**
	 * 一个service层将商品信息上架到方法
	 * 
	 * @param pid
	 * @throws SQLException 
	 */
	void upbypid(String pid) throws SQLException;

}
