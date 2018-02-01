package com.itheima.store.service;

import java.sql.SQLException;

import com.itheima.store.domain.User;

/**
 * 用户模块service层的接口
 * @author xt
 *
 */
public interface UserService {
	public boolean checkUserName(String name) throws SQLException;
	public void regist(User user) throws SQLException;
	public boolean active(String code) throws SQLException;
	public User login(String username, String password) throws SQLException;
}
