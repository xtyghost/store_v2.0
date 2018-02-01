package com.itheima.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.Category;

public interface CategoryDao {
	/**
	 * dao中从数据库查询商品分类的方法
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findmenu() throws SQLException;

	public void save(Category category) throws SQLException;
   /**
    * dao从数据库中删除商品分类的方法
    * @param category
 * @throws SQLException 
    */
	public void delete(Category category) throws SQLException;

	public void reset(Category category) throws SQLException;
}
