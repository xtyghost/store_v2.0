package com.itheima.store.service.impl;

import java.sql.SQLException;

import com.itheima.store.dao.UserDao;
import com.itheima.store.dao.impl.UserDaoImpl;
import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;

/**
 * 用户功能userService接口的实现类
 * 
 * @author xt
 *
 */
public class UserServiceImpl implements UserService {
	/**
	 * 一个service层查询用户名是否存在的方法
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public boolean checkUserName(String name) throws SQLException {
		// TODO Auto-generated method stub
		UserDao ud = (UserDao) new BeanFactory().getBean("UserDao");
		return ud.checkUserName(name);
	}

	/**
	 * 一个在service层的注册的方法
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void regist(User user) throws SQLException {
		// TODO Auto-generated method stub
		UserDao ud = (UserDao) new BeanFactory().getBean("UserDao");

		ud.regist(user);
	}

	/**
	 * 这是一个在service层 关于用户激活的方法
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public boolean active(String code) throws SQLException {
		// TODO Auto-generated method stub
		// 先查询到code对应用户
		UserDaoImpl daoImpl = (UserDaoImpl) new BeanFactory().getBean("UserDao");
		User user = daoImpl.checkUserBycode(code);
		if (user == null) {
			// 没查到U
			return false;
		} else {

			// 修改用户状态
			user.setState(2);
			user.setCode(null);
			boolean flag = daoImpl.changUsermsg(user);
			return flag;
		}
	}

	/**
	 * 一个在service层管理用户登录的方法
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User login(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		UserDao ud = (UserDao) new BeanFactory().getBean("UserDao");
		return ud.login(username, password);
	}

}
