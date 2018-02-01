package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.PageBaen2;
import com.itheima.store.domain.Pagebean;
import com.itheima.store.domain.Product;
import com.itheima.store.utils.DataSourceUtils;
import com.itheima.store.utils.DateComparetor;

/**
 * 一个dao层 商品的类
 * 
 * @author xt
 *
 */
public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> newPL() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from Product where  pflag=? order by pid limit ?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
		return list;
	}

	@Override
	public List<Product> hotPL() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from Product where  is_hot=? and pflag=? order by pid limit ?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
		return list;
	}

	@Override
	public List<Product> findfirtpage(Pagebean pagebean) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from Product where cid=? order by pdate desc limit ?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), pagebean.getCid(),
				pagebean.getPagesize());
		return list;
	}

	@Override
	public double totalcountp(Pagebean pagebean) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select count(*) from product where cid=?";
		Long count = (Long) runner.query(sql, new ScalarHandler(), pagebean.getCid());
		return count.doubleValue();
	}

	@Override
	public List<Product> lastpage(Pagebean pagebean) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from product where cid=? and pid>? order by pid desc";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), pagebean.getCid(),
				pagebean.getLastpid());
		return list;
	}

	@Override
	public List<Product> pagelist1(Pagebean pagebean) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		// 按时间倒序
		String sql = "select * from product where cid=? and pid>? order by pid  limit ?";
		Object[] params = { pagebean.getCid(), pagebean.getLastpid(),
				pagebean.getPagesize() * (pagebean.getCurrentpage() - pagebean.getSrcpage()) };
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), params);
		List<Product> list2 = list.subList(list.size() - pagebean.getPagesize(), list.size());
		return list2;
	}

	@Override
	public List<Product> pagelist2(Pagebean pagebean) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from product where cid=? and pid<? order by pid desc limit ?";
		Object[] params = { pagebean.getCid(), pagebean.getFirtpid(),
				pagebean.getPagesize() * (pagebean.getSrcpage() - pagebean.getCurrentpage()) };
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), params);
		list = list.subList(0, pagebean.getPagesize());
		Collections.sort(list, new DateComparetor());
		
		return list;
	}

	@Override
	public Product finbypid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="select * from product where pid=?";
		Product product = runner.query(sql, new BeanHandler<Product>(Product.class),pid);
		return product;
	}

	@Override
	public double gettotalcount() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="select count(pid) from Product where pflag=?";
		Long lon = (Long) runner.query(sql, new ScalarHandler(),0);
		return lon.doubleValue();
	}

	@Override
	public List<Product> findallProduct(PageBaen2 bean2) throws NumberFormatException, SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="select * from Product where pflag=? limit ?, 10";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class),0,(bean2.getCurrPage()-1)*bean2.getPageSize());
		return list;
	}

	@Override
	public void addProduct(Product product) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="insert into Product values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params={product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		runner.update(sql,params);
	}

	@Override
	public void deletebypid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="update product set pflag=1 where pid=?";
		runner.update(sql,pid);
		
	}

	@Override
	public double getxtotalcount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="select count(pid) from Product where pflag=?";
		Long lon = (Long) runner.query(sql, new ScalarHandler(),1);
		return lon.doubleValue();
	}

	@Override
	public List<Product> findxallProduct(PageBaen2 bean2) throws NumberFormatException, SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="select * from Product where pflag=? limit ?, 10";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class),1,(bean2.getCurrPage()-1)*bean2.getPageSize());
		return list;
	}

	@Override
	public void upbypid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql="update Product set pflag=0 where pid=?";
		runner.update(sql,pid);
	}

}
