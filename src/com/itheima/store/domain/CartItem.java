package com.itheima.store.domain;

import java.io.Serializable;

import com.itheima.store.service.ProductService;

/**
 * 一个用于记录订单项信息的类
 * 
 * @author xt
 *
 */
public class CartItem implements Serializable {
	private double total;
	private Product product;
	private int count;
	private String uname;



	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItem(Product product2, Integer valueOf, String uname) {
		// TODO Auto-generated constructor stub
		this.product = product2;
		this.count = valueOf;
		this.uname = uname;
	}

	public double getTotal() {
		return product.getShop_price() * count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

}
