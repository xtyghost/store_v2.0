package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.domain.Category;
import com.itheima.store.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {
	/**
	 * dao中从数据库查询商品分类的方法
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findmenu() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}

	@Override
	public void save(Category category) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "insert into Category value(?,?)";
		runner.update(sql, category.getCid(), category.getCname());
	}

	@Override
	public void delete(Category category) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="delete from Category where cid=?";
		runner.update(sql,category.getCid());
	}

	@Override
	public void reset(Category category) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="update Category set cname=? where cid =?";
		runner.update(sql,category.getCname(),category.getCid());
	}

}
