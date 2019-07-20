package cn.zjh.carLeaseSystem.car.domain;

public class CarCompany {
	private int ccid;
	private String ccname;
	private String city;
	private String area;
	private String phone;
	public int getCcid() {
		return ccid;
	}
	public void setCcid(int ccid) {
		this.ccid = ccid;
	}
	@Override
	public String toString() {
		return "CarCompany [ccid=" + ccid + ", ccname=" + ccname + ", city=" + city + ", area=" + area + ", phone="
				+ phone + "]";
	}
	public String getCcname() {
		return ccname;
	}
	public void setCcname(String ccname) {
		this.ccname = ccname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
