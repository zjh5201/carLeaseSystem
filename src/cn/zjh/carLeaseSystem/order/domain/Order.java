package cn.zjh.carLeaseSystem.order.domain;

import java.sql.Timestamp;
import java.util.Date;

import cn.zjh.carLeaseSystem.car.domain.Car;
import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.worker.domain.Worker;

public class Order {
	private String oid;
	private User user;
	private Worker worker;
	private Date rentTime;
	private String rentCity;
	private String rentArea;
	private Date returnTime;
	private String returnCity;
	private String returnArea;
	private Car car;
	private int cash;		//押金
	private int rentmoney;
	private OrderItem orderItem;
	private int days;
	private String beizhu;
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public int getRentmoney() {
		return rentmoney;
	}
	public void setRentmoney(int rentmoney) {
		this.rentmoney = rentmoney;
	}
	private int orderstate;	//订单状态	0:游客 需定期清理 1：购物车订单  2：付款  3：结单
	private int destoryCompensation;		//损毁罚款
	private int illegalCompensation;			//违章罚款
	public String getOid() {
		return oid;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", user=" + user + ", worker=" + worker + ", rentTime=" + rentTime + ", rentCity="
				+ rentCity + ", rentArea=" + rentArea + ", returnTime=" + returnTime + ", returnCity=" + returnCity
				+ ", returnArea=" + returnArea + ", car=" + car + ", cash=" + cash + ", rentmoney=" + rentmoney
				+ ", orderItem=" + orderItem + ", days=" + days + ", orderstate=" + orderstate
				+ ", destoryCompensation=" + destoryCompensation + ", illegalCompensation=" + illegalCompensation + "]";
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public String getRentCity() {
		return rentCity;
	}
	public void setRentCity(String rentCity) {
		this.rentCity = rentCity;
	}
	public String getRentArea() {
		return rentArea;
	}
	public void setRentArea(String rentArea) {
		this.rentArea = rentArea;
	}

	public Date getRentTime() {
		return rentTime;
	}
	public void setRentTime(Date rentTime) {
		this.rentTime = rentTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public String getReturnCity() {
		return returnCity;
	}
	public void setReturnCity(String returnCity) {
		this.returnCity = returnCity;
	}
	public String getReturnArea() {
		return returnArea;
	}
	public void setReturnArea(String returnArea) {
		this.returnArea = returnArea;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public int getOrderstate() {
		return orderstate;
	}
	public void setOrderstate(int orderstate) {
		this.orderstate = orderstate;
	}
	public int getDestoryCompensation() {
		return destoryCompensation;
	}
	public void setDestoryCompensation(int destoryCompensation) {
		this.destoryCompensation = destoryCompensation;
	}
	public int getIllegalCompensation() {
		return illegalCompensation;
	}
	public void setIllegalCompensation(int illegalCompensation) {
		this.illegalCompensation = illegalCompensation;
	}

	
}
