package cn.zjh.carLeaseSystem.order.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import cn.zjh.carLeaseSystem.car.domain.Car;
import cn.zjh.carLeaseSystem.car.domain.CarCompany;
import cn.zjh.carLeaseSystem.order.dao.OrderDao;
import cn.zjh.carLeaseSystem.order.domain.Order;
import cn.zjh.carLeaseSystem.order.domain.OrderItem;
import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.worker.domain.Worker;

public class OrderService {
	private OrderDao dao = new OrderDao();
	//添加订单，用户订单添加。
	public String userAdd(Order form,OrderItem orderItem) throws SQLException{
		
		//这块返回的orderNum代表着该用户下所有订单中未付款的且车型相符的订单。
		long orderNum = (long)dao.queryToCheck(form.getCar(),form.getUser().getId());	//看订单列表中是否存在该订单，根据车辆ID来查。
		if(orderNum==0){
			//要先创建订单条目，在创建订单，否则会出现外键报错问题
			dao.createOrderItem(orderItem);
			dao.addOrder(form);
		}else{
			//如果不为空，则返回已存在的订单Id
			Object object = dao.getOidByExist(form.getCar(),form.getUser().getId());
			if(object!=null)
				return object.toString();
			else {
				return null;
			}
		}
		return null;
	}
	//添加订单，游客订单添加。涉及了添加订单和订单条目项
	public void visitorAdd(Order form,OrderItem orderItem) throws SQLException{
		//要先创建订单条目，在创建订单，否则会出现外键报错问题
		dao.createOrderItem(orderItem);
		dao.addOrder(form);
		
	}
	
	//模拟mybatis的resultMap。。。。参数：order，目的是为了该Order绑定其他的关联对象
	public Order mybatis(Order o) throws SQLException {
		OrderItem orderItem = getRelatedObj(o.getOid(), "itemId", "orderitem","order","oid", OrderItem.class);
		Car car = getRelatedObj(o.getOid(), "carId", "car","order","oid", Car.class);
		CarCompany carCompany = getRelatedObj(car.getCarId(), "ccid", "carcompany","car","carId", CarCompany.class);
		User user = getRelatedObj(o.getOid(), "id", "user","order","oid", User.class);
		//worker可能是空值，因为当订单状态为1时不会分配工作人员。
		Worker worker = getRelatedObj(o.getOid(), "wid", "worker","order","oid", Worker.class);
		o.setOrderItem(orderItem);
		o.setWorker(worker);
		car.setCc(carCompany);
		o.setUser(user);
		o.setCar(car);
		return o;
	}
	
	//完善OrderItem信息。
	public void finishInfo(String itemId, OrderItem orderItem) throws SQLException {
		dao.finishOrderItem(itemId,orderItem);
	}
	//设置订单的状态，当去支付的时候设置为2
	public void setState(String oid,int state) throws SQLException {
		dao.setState(oid,state);
		
	}
	//根据Id查找Order
	public Order findOrderById(String oid) throws SQLException{
		return dao.findOrderById(oid);
	}
	
	//设置订单的wid
	public void setWidforOrder(String oid,String wid) throws SQLException{
		dao.setWidforOrder(oid,wid);
	}
	//获取指定id用户的所有订单
	public List<Order> getAllOrdersOfUser(String uid) throws SQLException {
		return dao.selectAllOrdersOfUser(uid);
	}
	public String getOrderIds(String id,String column,String firstTable,String primaryKey) throws SQLException {
		return dao.getOrderIds(id, column, firstTable, primaryKey);
	}
	//获取order表中关联表的对象实例。
	//idValue:主表的id值  columnName：要查询主表的字段列  secondtable:关联表 firstTable:主表 key：主表的主键字段 class：要映射出的对象
	public <T> T getRelatedObj(String idValue,String columnName,String secondTable,String firstTable, String primaryKey,Class<T> t) throws SQLException {
		//首先获取主表中关联表的主键值，主表通过主表名和主键值获得。
		String id = dao.getOrderIds(idValue, columnName,firstTable,primaryKey);
		if(id==null)
			return null;
		//根据查出来的主键和表名，获取对应class的实例
		return dao.getRelatedObj(id,secondTable,columnName, t);
	}
	public void deleteOrder(String oid) throws SQLException {
		dao.deleteOrder(oid);
	}
	public void decWorkerCount(Order order) throws SQLException {
		dao.decWorkerCount(order);
	}
	public List<Order> getOrdersByUid(String uid) throws SQLException {
		return dao.getOrdersByUid(uid);
	}
	
	public List<Order> getOrdersByWid(String wid) throws SQLException{
		return dao.getOrdersByWid(wid);
	}
	public Order getOrderByOid(String oid)throws SQLException{
		return dao.getOrderByOid(oid);
	}
	public void updateOrder(String oid, Order order) throws SQLException {
		dao.updateOrder(oid,order);
	}
	public Long currentOrderCount(Worker worker) throws SQLException {
		Long count = (Long)dao.currentOrderCount(worker);
		return count;
	}
	public OrderItem getOrderItemByItemId(String itemId) throws SQLException {
		return dao.getOrderItemByItemId(itemId);
	}
}
