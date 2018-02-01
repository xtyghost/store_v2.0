package com.itheima.store.utils;

import java.util.UUID;

/**
 * 一个管理uuid的工具类
 * 
 * @author xt
 *
 */
public class UUIDUtils {
	private UUIDUtils() {

	}

	/**
	 * 获取唯一uuid字符串
	 */
	public static String getUuid() {
		String UUId = UUID.randomUUID().toString().replace("-", "");
		return UUId;
	}
}
