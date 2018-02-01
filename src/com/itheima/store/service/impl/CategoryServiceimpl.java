package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.dao.impl.CategoryDaoImpl;
import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceimpl implements CategoryService {
	/**
	 * 从数据库中查询menu列表的方法
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findmenu() throws SQLException {
		// TODO Auto-generated method stub
		// 建立缓冲管理者
		CacheManager manager = CacheManager
				.create(CategoryServiceimpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 查询所需的缓冲部分
		Cache cache = manager.getCache("categoryCache");
		// 从缓冲区中查询所需的元素
		Element element = cache.get("mlist");
		if (element == null) {
			// 如果为空查询并将查到的列表存入cache
			CategoryDao cd = ((CategoryDao) new BeanFactory().getBean("CategoryDao"));
			List<Category> list = cd.findmenu();
			Element element2 = new Element("mlist", list);
			cache.put(element2);
			return list;
		} else {
			// 从缓冲中那数据
			return (List<Category>) element.getObjectValue();
		}
	}

	@Override
	public void save(String cname) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		 Category category = new Category();
		 category.setCid(UUIDUtils.getUuid());
		 category.setCname(cname);
		 cd.save(category);
	}

	@Override
	public void delete(String cid) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		 Category category = new Category();
		 category.setCid(cid);
		 cd.delete(category);
	}

	@Override
	public void changbycid(Category category) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.reset(category);
	}

	

}
