package com.itheima.store.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 一个用于封装订单分页的pageBean
 * 
 * @author xt
 *
 */
public class PageBaen2  implements Serializable{
	private int currPage;
	private int pageSize;
	private int totalPage;
	private double totalCount;
	private List<?> list;
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public double getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}

}
