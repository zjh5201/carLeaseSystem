package cn.zjh.carLeaseSystem.worker.dao;


import java.sql.SQLException;
import java.util.List;

import javax.enterprise.inject.New;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.worker.domain.Worker;

public class WorkerDao {
	private QueryRunner qr = new TxQueryRunner();

	public List<Worker> selectAllWorkers() throws SQLException {
		String sql = "select * from cls_worker";
		return qr.query(sql, new BeanListHandler<Worker>(Worker.class));
	}
	//为工作人员订单数加一
	public void setWorkerOrderCount(String wid) throws SQLException {
		String sql = "update cls_worker set ordercount = ordercount+1 where wid=?";
		qr.update(sql, wid);
	}
	public Worker findByName(String wname) {
		//按用户名和身份证查找数据库。  有重名，但是身份证是不会重复的。
		String sql = "select * from cls_worker where wname=?";
		try {
			return qr.query(sql, new BeanHandler<Worker>(Worker.class),wname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Worker getWorkerByNameAndPassword(Worker worker) throws SQLException {
		String sql = "select * from cls_worker where wname=? and wpassword=?";
		
		return qr.query(sql, new BeanHandler<Worker>(Worker.class), worker.getWname(),worker.getWpassword());
	}
	public Worker getWorkerByWid(String wid) throws SQLException {
		String sql = "select * from cls_worker where wid = ?";
		return qr.query(sql, new BeanHandler<Worker>(Worker.class), wid);
	}
}	
