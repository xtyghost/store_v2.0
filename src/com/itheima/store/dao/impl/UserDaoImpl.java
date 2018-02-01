package com.itheima.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.utils.DataSourceUtils;

/**
 * userDao的实现类
 * 
 * @author xt
 *
 */
public class UserDaoImpl implements UserDao {
	/**
	 * dao层查询username是否可用的方法
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public boolean checkUserName(String name) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from User where username = ?";
		User query = runner.query(sql, new BeanHandler<User>(User.class), name);
		if (query == null) {
			// 没查到
			return false;
		}
		return true;
	}
/**
 * 
 * 用户注册
 */
	public void regist(User user) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "insert into User (uid,username,password,name,email,telephone,birthday,sex,state,code) values (?,?,?,?,?,?,?,?,?,?)";
		int update = runner.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(),
				user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(),
				user.getCode());
	}

	/**
	 * 一个根据code查user的方法
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public User checkUserBycode(String code) throws SQLException {
		// TODO Auto-generated method stu b
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from user where code=?";
		User user = runner.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	/**
	 * 一个修改用户信息的方法
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean changUsermsg(User user) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		Object[] params = { user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode(),
				user.getUid() };
		int update = runner.update(sql, params);
		if (update == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 一个用于检验用户是否登录成功的方法
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User login(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.findDataSource());
		String sql = "select * from User where Username=?and password=?and state=2";
		User user = runner.query(sql, new BeanHandler<User>(User.class), username, password);
		return user;
	}

}
