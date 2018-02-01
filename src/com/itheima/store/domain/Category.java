package com.itheima.store.domain;

import java.io.Serializable;

/**
 * 这是分类的实体对象
 * 
 * @author xt
 *
 */
public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cid;
	private String cname;

	public String getCid() {
		return cid;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
}
