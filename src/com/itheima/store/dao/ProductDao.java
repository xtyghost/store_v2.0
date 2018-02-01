package com.itheima.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.PageBaen2;
import com.itheima.store.domain.Pagebean;
import com.itheima.store.domain.Product;

public interface ProductDao {

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
	 * 查寻列表第一页
	 * 
	 * @param pagebean
	 * @return
	 * @throws SQLException 
	 */
	List<Product> findfirtpage(Pagebean pagebean) throws SQLException;
/**一个于查询分类商品总个数的方法
 * 
 * @return
 * @throws SQLException 
 */
	double totalcountp(Pagebean pagebean) throws SQLException;
/**
 * 一个用于查询尾页的方法
 * @param pagebean
 * @return
 */
List<Product> lastpage(Pagebean pagebean) throws SQLException;
/**
 * 用于向后查询的方法
 * @return
 */
List<Product> pagelist1(Pagebean pagebean) throws SQLException;
/**
 * 用于向前查找的方法
 * @return
 */
List<Product> pagelist2(Pagebean pagebean) throws SQLException;
/**
 * 使用pid查找商品详情的方法
 * @param pid
 * @return
 * @throws SQLException
 */
Product finbypid(String pid) throws SQLException;
/**
 * 查询所有为下架的商品数量
 * @return
 * @throws SQLException 
 */
double gettotalcount() throws SQLException;
/**
 * 查询当前页的查询信息
 * @param <Baen2>
 * @param currPage
 * @return
 * @throws SQLException 
 * @throws NumberFormatException 
 */
 List<Product> findallProduct(PageBaen2 currPage) throws NumberFormatException, SQLException;
 /**
  * 查询所有下架的商品数量
  * @return
  * @throws SQLException 
  */
 double getxtotalcount() throws SQLException;
 /**
  * 查询下架的当前页的查询信息
  * @param <Baen2>
  * @param currPage
  * @return
  * @throws SQLException 
  * @throws NumberFormatException 
  */
 List<Product> findxallProduct(PageBaen2 currPage) throws NumberFormatException, SQLException;
/**
 * 一个在service层用于添加商品信息的方法
 * @param product
 * @throws SQLException 
 */
void addProduct(Product product) throws SQLException;
/**
 * 一个在dao层删除商品的方法
 * @param pid
 * @throws SQLException 
 */
void deletebypid(String pid) throws SQLException;
/**
 * 一个在dao层将商品信息上架的方法
 * @param pid
 * @throws SQLException 
 */
void upbypid(String pid) throws SQLException;

}
