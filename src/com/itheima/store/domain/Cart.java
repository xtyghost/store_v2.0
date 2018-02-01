package com.itheima.store.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 这是一个用于记录订单的类
 * 
 * @author xt
 *
 */
public class Cart implements Serializable {
	private Map<String, CartItem> map;
	private double totalcount;
     private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public double getTotalcount() {
		totalcount=0;
		Collection<CartItem> cartitm = map.values();
		for (CartItem cartItem : cartitm) {
			totalcount = cartItem.getTotal() + totalcount;
		}
		return totalcount;
	}

}
