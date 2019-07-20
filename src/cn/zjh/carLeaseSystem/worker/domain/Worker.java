package cn.zjh.carLeaseSystem.worker.domain;

public class Worker {
	private String wid;
	private String wname;
	private String wpassword;
	private String wphone;
	private int ordercount;
	public String getWid() {
		return wid;
	}
	public String toString() {
		return "Worker [wid=" + wid + ", wname=" + wname + ", wpassword=" + wpassword + ", wphone=" + wphone
				+ ", ordercount=" + ordercount + "]";
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getWname() {
		return wname;
	}
	public void setWname(String wname) {
		this.wname = wname;
	}
	public String getWpassword() {
		return wpassword;
	}
	public void setWpassword(String wpassword) {
		this.wpassword = wpassword;
	}
	public String getWphone() {
		return wphone;
	}
	public void setWphone(String wphone) {
		this.wphone = wphone;
	}
	public int getOrdercount() {
		return ordercount;
	}
	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}
	
	
}
