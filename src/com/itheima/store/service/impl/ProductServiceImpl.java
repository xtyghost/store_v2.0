package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.PageBaen2;
import com.itheima.store.domain.Pagebean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtils;

/**
 * 一个在service层管理商品信息的类
 * 
 * @author xt
 *
 */
public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> newPL() throws SQLException {
		// TODO Auto-generated method stub
		ProductDao pd = (ProductDao) new BeanFactory().getBean("ProductDao");
		return pd.newPL();
	}

	@Override
	public List<Product> hotPL() throws SQLException {
		// TODO Auto-generated method stub
		ProductDao pd = (ProductDao) new BeanFactory().getBean("ProductDao");
		return pd.hotPL();
	}

	@Override
	public Pagebean finbycid(Pagebean pagebean) throws SQLException {
		// TODO Auto-generated method stub
		ProductDao pd = (ProductDao) new BeanFactory().getBean("ProductDao");
		// 统计总页数
		double tatalsize = pd.totalcountp(pagebean);

		// 查询总页数和记录数
		pagebean.setTotalcount(tatalsize);
		int totalpage = (int) Math.ceil(tatalsize / pagebean.getPagesize());
		pagebean.setTotalpage(totalpage);
		if (pagebean.getSrcpage() == 0) {
			List<Product> list = pd.findfirtpage(pagebean);
			pagebean.setPlist(list);
			pagebean.setSrcpage(pagebean.getSrcpage());
			pagebean.setFirtpid(list.get(0).getPid());
			pagebean.setLastpid(list.get(pagebean.getPagesize() - 1).getPid());
			return pagebean;
		} else if (pagebean.getCurrentpage() == totalpage) {
			// 尾页应查找个数
			List<Product> list = pd.lastpage(pagebean);
			pagebean.setPlist(list);
			return pagebean;
		} else if (pagebean.getCurrentpage() - pagebean.getSrcpage() > 0) {
			List<Product> list = pd.pagelist1(pagebean);
			pagebean.setPlist(list);
			return pagebean;
		} else {
			List<Product> list = pd.pagelist2(pagebean);
			pagebean.setPlist(list);
			return pagebean;
		}

	}

	@Override
	public Product findbypid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		ProductDao pd = (ProductDao) new BeanFactory().getBean("ProductDao");
		return pd.finbypid(pid);
	}

	/**
	 * 查询当前页的未下架商品内容
	 */
	@Override
	public PageBaen2 findall(String currPage) throws SQLException {
		// TODO Auto-generated method stub
		PageBaen2 baen2 = new PageBaen2();
		baen2.setCurrPage(Integer.parseInt(currPage));
		baen2.setPageSize(10);
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		double totalcount = pd.gettotalcount();
		baen2.setTotalCount(totalcount);
		baen2.setTotalPage((int) Math.ceil(totalcount / baen2.getPageSize()));
		// 查询当前页的产品list
		List<Product> list = pd.findallProduct(baen2);
		baen2.setList(list);
		return baen2;
	}

	@Override
	public PageBaen2 findxall(String currPage) throws SQLException {
		// TODO Auto-generated method stub
		PageBaen2 baen2 = new PageBaen2();
		baen2.setCurrPage(Integer.parseInt(currPage));
		baen2.setPageSize(10);
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		double totalcount = pd.getxtotalcount();
		baen2.setTotalCount(totalcount);
		baen2.setTotalPage((int) Math.ceil(totalcount / baen2.getPageSize()));
		// 查询当前页的产品list
		List<Product> list = pd.findxallProduct(baen2);
		baen2.setList(list);
		return baen2;
	}

	@Override
	public void addProduct(Product product) throws SQLException {
		// TODO Auto-generated method stub
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		product.setPid(UUIDUtils.getUuid());
		product.setPdate(new Date());
		product.setPflag(0);
		pd.addProduct(product);
	}

	@Override
	public void deletbypid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		pd.deletebypid(pid);
	}

	@Override
	public void upbypid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		pd.upbypid(pid);
	}

}
