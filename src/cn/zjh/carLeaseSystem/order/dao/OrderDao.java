package cn.zjh.carLeaseSystem.order.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.enterprise.inject.New;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.zjh.carLeaseSystem.car.domain.Car;
import cn.zjh.carLeaseSystem.order.domain.Order;
import cn.zjh.carLeaseSystem.order.domain.OrderItem;
import cn.zjh.carLeaseSystem.worker.domain.Worker;


public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();

	public void addOrder(Order form) throws SQLException {
		String sql = "insert into cls_order values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String id,wid;
		if(form.getUser()==null){
			id = null;
		}else {
			id = form.getUser().getId();
		}
		if(form.getWorker()==null){
			wid = null;
		}else{
			wid = form.getWorker().getWid();
		}
		qr.update(sql, form.getOid(),id,wid,form.getRentTime(),
				form.getRentCity(),form.getRentArea(),form.getReturnTime(),form.getReturnCity(),form.getReturnArea()
				,form.getCar().getCarId(),form.getCash(),form.getRentmoney(),form.getOrderstate(),form.getDestoryCompensation(),
				form.getIllegalCompensation(),form.getOrderItem().getItemId(),form.getDays(),null);
	}
	//创建订单条目
	public void createOrderItem(OrderItem orderItem) throws SQLException {
		String sql = "Insert into cls_orderItem values(?,?,?,?,?)";
		qr.update(sql,orderItem.getItemId(),orderItem.getDname(),orderItem.getDphone(),orderItem.getdIdcard(),
				new Date());
	}
	
	//完善或修改订单，适用于用户登录下订单。
	public void finishOrderItem(String itemId, OrderItem orderItem) throws SQLException {
		String sql = "update cls_orderItem set dname=?,dphone=?,dIdcard=?,book=? where itemId=?";
		qr.update(sql, orderItem.getDname(),orderItem.getDphone(),orderItem.getdIdcard(),orderItem.getBook(),itemId);
		
	}

	//设置订单状态
	public void setState(String oid, int state) throws SQLException {
		String sql = "update cls_order set orderstate = ? where oid = ?";
		qr.update(sql,state,oid);
	}
	
	//查询订单表中有无汽车型号的数据,多表关联查询
	//这里应该考虑sessionId或者用户Id，因为整个订单表中有很多同车辆型号的订单，要精确到个人并且是订单状态为1的订单。
	public Object queryToCheck(Car car,String userId) throws SQLException {
		String sql = "select count(*) from cls_order o,cls_car c where c.model=? and c.brand=? and o.orderstate=1 and id=? and c.carId = o.carId";
		return qr.query(sql, new ScalarHandler(),car.getModel(),car.getBrand(),userId);
	}
	
	public Order findOrderById(String oid) throws SQLException {
		String sql = "select * from cls_order where oid = ?";
		return qr.query(sql, new BeanHandler<Order>(Order.class), oid);
	}
	
	public void setWidforOrder(String oid, String wid) throws SQLException {
		String sql = "update cls_order set wid = ? where oid = ?";
		qr.update(sql, wid,oid);
	}
	
	public List<Order> selectAllOrdersOfUser(String uid) throws SQLException {
		String sql = "select * from cls_order where id = ?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
	}
	
	//获取Order相关对象的id值，因为Order表中存放的都是各个关联表的主键值，最后通过主键来获取各表对象。
	public String getOrderIds(String id,String column,String firstTable, String key) throws SQLException {
		String sql = "select "+column+" from cls_"+firstTable+" where "+key+" = ?";
		Object ids = qr.query(sql, new ScalarHandler(),id);
		if(ids==null)
			return null;
		
		return ids.toString();	//ScalarHandler()方法返回Integer
	}
	//获取实例
	public <T> T getRelatedObj(String id, String tableName,String column,Class<T> t) throws SQLException  {
		String sql = "select * from cls_"+tableName+" where "+column+"=?";
		return qr.query(sql, new BeanHandler<T>(t), id);
	}
	public void deleteOrder(String oid) throws SQLException {
		String querySql = "select itemId from cls_order where oid=?";
		String itemId = qr.query(querySql, new ScalarHandler(), oid).toString();
		String deleteSql1 = "delete from cls_order where oid=?";
		String deleteSql2 = "delete from cls_orderitem where itemId = ?";
		//移除
		qr.update(deleteSql1, oid);
		qr.update(deleteSql2, itemId);
	}
	public Object getOidByExist(Car car, String id) throws SQLException {
		String sql =  "select o.oid from cls_order o,cls_car c where c.model=? and c.brand=? and o.orderstate=1 and id=? and c.carId = o.carId";
		return qr.query(sql, new ScalarHandler(), car.getModel(),car.getBrand(),id);
	}
	//工作人员的订单数量减一
	public void decWorkerCount(Order order) throws SQLException {
		String getWidSql = "select wid from cls_order where oid=?";
		Object obj = qr.query(getWidSql, new ScalarHandler(), order.getOid());
		String wid = null;
		if(obj!=null){
			wid = obj.toString();
			String sql = "update cls_worker set ordercount = ordercount-1 where wid=?";
			qr.update(sql, wid);
		}
	}
	public List<Order> getOrdersByUid(String uid) throws SQLException {
		String sql = "select * from cls_order where id=?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
	}
	public List<Order> getOrdersByWid(String wid) throws SQLException {
		String sql = "select * from cls_order where wid = ?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class), wid);
	}
	public Order getOrderByOid(String oid) throws SQLException {
		String sql = "select * from cls_order where oid = ?";
		return qr.query(sql, new BeanHandler<Order>(Order.class), oid);
	}
	public void updateOrder(String oid, Order order) throws SQLException {
		String sql = "update cls_order set orderstate=?,beizhu=? where oid=?";
		qr.update(sql, order.getOrderstate(),order.getBeizhu(),oid);
	}
	public Object currentOrderCount(Worker worker) throws SQLException {
		String sql = "select count(*) from cls_order where wid=? and orderstate !=5";
		return qr.query(sql, new ScalarHandler(), worker.getWid());
	
	}
	public OrderItem getOrderItemByItemId(String itemId) throws SQLException {
		String sql = "select * from cls_orderitem where itemId = ?";
		return qr.query(sql, new BeanHandler<OrderItem>(OrderItem.class), itemId);
	}
}
