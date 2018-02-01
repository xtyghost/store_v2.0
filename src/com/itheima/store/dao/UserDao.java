package com.itheima.store.dao;

import java.sql.SQLException;

import com.itheima.store.domain.User;

/**
 * 用户模块dao的接口
 * 
 * @author xt
 *
 */
public interface UserDao {
	/**
	 * dao层查询username是否可用的方法
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public boolean checkUserName(String name) throws SQLException;

	public void regist(User user) throws SQLException;

	/**
	 * 一个根据code查user的方法
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public User checkUserBycode(String code) throws SQLException;

	/**
	 * 一个修改用户信息的方法
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean changUsermsg(User user) throws SQLException;

	/**
	 * 一个用于检验用户是否登录成功的方法
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User login(String username, String password) throws SQLException;
}
