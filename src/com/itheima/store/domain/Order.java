package com.itheima.store.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 订单的实体类
 * @author xt
 *
 */
public class Order implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String oid;
private double total;
private Map<String,OrderItem> map;
private Date ordertime;
private String address;
private String addressee;
private String telephone;
private String bank;
private  int state;
private User user;
public String getOid() {
	return oid;
}
public void setOid(String oid) {
	this.oid = oid;
}
public double getTotal() {
	return total;
}
public void setTotal(double total) {
	this.total = total;
}
public Map<String, OrderItem> getMap() {
	return map;
}
public void setMap(Map<String, OrderItem> map) {
	this.map = map;
}

public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getAddressee() {
	return addressee;
}
public void setAddressee(String addressee) {
	this.addressee = addressee;
}
public String getTelephone() {
	return telephone;
}
public void setTelephone(String telephone) {
	this.telephone = telephone;
}
public String getBank() {
	return bank;
}
public void setBank(String bank) {
	this.bank = bank;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public Date getOrdertime() {
	return ordertime;
}
public void setOrdertime(Date ordertime) {
	this.ordertime = ordertime;
}
@Override
public String toString() {
	return "Order [oid=" + oid + ", total=" + total + ", map=" + map + ", ordertime=" + ordertime + ", address="
			+ address + ", addressee=" + addressee + ", telephone=" + telephone + ", bank=" + bank + ", state=" + state
			+ ", user=" + user + "]";
}


}
