package cn.zjh.carLeaseSystem.order.domain;

import java.util.Date;

public class OrderItem {
	private String itemId;
	private String dname;
	private String dphone;
	private String dIdcard;
	private Date book;	
	public Date getBook() {
		return book;
	}
	@Override
	public String toString() {
		return "OrderItem [itemId=" + itemId + ", dname=" + dname + ", dphone=" + dphone + ", dIdcard=" + dIdcard
				+ ", book=" + book + "]";
	}
	public void setBook(Date book) {
		this.book = book;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDphone() {
		return dphone;
	}
	public void setDphone(String dphone) {
		this.dphone = dphone;
	}
	public String getdIdcard() {
		return dIdcard;
	}
	public void setdIdcard(String dIdcard) {
		this.dIdcard = dIdcard;
	}
	
}
