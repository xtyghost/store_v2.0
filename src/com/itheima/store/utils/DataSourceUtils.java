package com.itheima.store.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//这是一个获取连接池的类,单例
public class DataSourceUtils {
	private DataSourceUtils() {
	}

	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static Connection con = null;

	/**
	 * 从数据库获取连接池的方法
	 * 
	 * @return
	 */
	public static DataSource findDataSource() {
		return dataSource;
	}

	/**
	 * 获得连接的方法
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
