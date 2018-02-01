package com.itheima.store.domain;

import java.util.List;

/**
 * 关于分页到javaBean
 * 
 * @author xt method=findbycid&cid="+n.cid+"&firtbday=0&lastbday&currentpage=1&
 *         srcpage=0
 */
public class Pagebean {
	private int currentpage;
	private int srcpage;
	private String firtpid;
	private String lastpid;
	private int pagesize = 12;
	private int totalpage;
	private double totalcount;
	private List<?> plist;
	private int cid;
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	public int getSrcpage() {
		return srcpage;
	}
	public void setSrcpage(int srcpage) {
		this.srcpage = srcpage;
	}
	public String getFirtpid() {
		return firtpid;
	}
	public void setFirtpid(String firtpid) {
		this.firtpid = firtpid;
	}
	public String getLastpid() {
		return lastpid;
	}
	public void setLastpid(String lastpid) {
		this.lastpid = lastpid;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public double getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(double totalcount) {
		this.totalcount = totalcount;
	}
	public List<?> getPlist() {
		return plist;
	}
	public void setPlist(List<?> plist) {
		this.plist = plist;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cid;
		result = prime * result + currentpage;
		result = prime * result + ((firtpid == null) ? 0 : firtpid.hashCode());
		result = prime * result + ((lastpid == null) ? 0 : lastpid.hashCode());
		result = prime * result + pagesize;
		result = prime * result + ((plist == null) ? 0 : plist.hashCode());
		result = prime * result + srcpage;
		long temp;
		temp = Double.doubleToLongBits(totalcount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + totalpage;
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
		Pagebean other = (Pagebean) obj;
		if (cid != other.cid)
			return false;
		if (currentpage != other.currentpage)
			return false;
		if (firtpid == null) {
			if (other.firtpid != null)
				return false;
		} else if (!firtpid.equals(other.firtpid))
			return false;
		if (lastpid == null) {
			if (other.lastpid != null)
				return false;
		} else if (!lastpid.equals(other.lastpid))
			return false;
		if (pagesize != other.pagesize)
			return false;
		if (plist == null) {
			if (other.plist != null)
				return false;
		} else if (!plist.equals(other.plist))
			return false;
		if (srcpage != other.srcpage)
			return false;
		if (Double.doubleToLongBits(totalcount) != Double.doubleToLongBits(other.totalcount))
			return false;
		if (totalpage != other.totalpage)
			return false;
		return true;
	}


}
