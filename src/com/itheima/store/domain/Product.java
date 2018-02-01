package com.itheima.store.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 一个用于表示商品的javabean
 * @author xt
 *
 */
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pid;
	private String pname;
	private double market_price;
	private double shop_price;
	private String pimage;
	private Date pdate;
	private int is_hot;
	private String pdesc;
	private int pflag;
	private int cid;
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	private Category category;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getMarket_price() {
		return market_price;
	}
	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}
	public double getShop_price() {
		return shop_price;
	}
	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public int getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public int getPflag() {
		return pflag;
	}
	public void setPflag(int pflag) {
		this.pflag = pflag;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cid;
		result = prime * result + is_hot;
		long temp;
		temp = Double.doubleToLongBits(market_price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pdate == null) ? 0 : pdate.hashCode());
		result = prime * result + ((pdesc == null) ? 0 : pdesc.hashCode());
		result = prime * result + pflag;
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((pimage == null) ? 0 : pimage.hashCode());
		result = prime * result + ((pname == null) ? 0 : pname.hashCode());
		temp = Double.doubleToLongBits(shop_price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (cid != other.cid)
			return false;
		if (is_hot != other.is_hot)
			return false;
		if (Double.doubleToLongBits(market_price) != Double.doubleToLongBits(other.market_price))
			return false;
		if (pdate == null) {
			if (other.pdate != null)
				return false;
		} else if (!pdate.equals(other.pdate))
			return false;
		if (pdesc == null) {
			if (other.pdesc != null)
				return false;
		} else if (!pdesc.equals(other.pdesc))
			return false;
		if (pflag != other.pflag)
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (pimage == null) {
			if (other.pimage != null)
				return false;
		} else if (!pimage.equals(other.pimage))
			return false;
		if (pname == null) {
			if (other.pname != null)
				return false;
		} else if (!pname.equals(other.pname))
			return false;
		if (Double.doubleToLongBits(shop_price) != Double.doubleToLongBits(other.shop_price))
			return false;
		return true;
	}
	
}
