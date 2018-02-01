package com.itheima.web.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.hibernate.EhCacheProvider;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 跳转到add页面的方法
     * @param request
     * @param response
     * @return
     */
	public String savaUi(HttpServletRequest request, HttpServletResponse response) {
		return "/admin/category/add.jsp";

	}
	/**
	 * 跳转到存储页面的方法
	 * @param request
	 * @param response
	 * @return
	 */
	public String editUi(HttpServletRequest request, HttpServletResponse response) {
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		request.setAttribute("cid", cid);
		request.setAttribute("cname", cname);
		return "/admin/category/edit.jsp";
		
	}

	/**
	 * 管理员查商品分离的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) {
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		try {
			List<Category> list = cs.findmenu();
			request.setAttribute("list", list);
			return "admin/category/list.jsp";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * admin添加商品分类的方法`
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) {
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		String cname = request.getParameter("cname");
		try {
			cs.save(cname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 清除缓存
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("categoryCache");
		cache.remove(cache.getKeys().get(0));
		// 想类别页转发
		List<Category> list;
		try {
			list = cs.findmenu();
			request.setAttribute("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin/category/list.jsp";

	}

	/**
	 * admin修改商品分类的方法`
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String changbycid(HttpServletRequest request, HttpServletResponse response) {
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		String cname = request.getParameter("cname");
		String cid = request.getParameter("cid");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		try {
			
			cs.changbycid(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 清除缓存
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("categoryCache");
		cache.remove(cache.getKeys().get(0));
		// 想类别页转发
		List<Category> list;
		try {
			list = cs.findmenu();
			request.setAttribute("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin/category/list.jsp";

	}

	/**
	 * 从数据空中删除列表记录的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String deleteCate(HttpServletRequest request, HttpServletResponse response) {
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		String cid = request.getParameter("cid");
		try {
			cs.delete(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 清除缓存
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("categoryCache");
		cache.remove(cache.getKeys().get(0));
		// 想类别页转发
		List<Category> list;
		try {
			list = cs.findmenu();
			request.setAttribute("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin/category/list.jsp";
	}

}
