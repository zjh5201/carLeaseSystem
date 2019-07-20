package cn.zjh.carLeaseSystem.worker.service;

import java.sql.SQLException;
import java.util.List;

import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.user.domain.UserException;
import cn.zjh.carLeaseSystem.worker.dao.WorkerDao;
import cn.zjh.carLeaseSystem.worker.domain.Worker;

public class WorkerService {
	private WorkerDao workerDao = new WorkerDao();

	public List<Worker> selectAllWorkers() throws SQLException {
		return workerDao.selectAllWorkers();
	}

	public void setWorkerOrderCount(String wid) throws SQLException {
		workerDao.setWorkerOrderCount(wid);
	}
	
	public Worker login(Worker form) throws UserException {
		Worker worker = workerDao.findByName(form.getWname());
		if(worker == null) {
			throw new UserException("您输入的账户不存在！");
		}else {
			String password = worker.getWpassword();
			if(!password.equals(form.getWpassword())) 
				throw new UserException("您输入的密码不正确！");
		}
		return worker;
	}
	//根据用户名和密码获取worker的完整信息
	public Worker getWorkerByNameAndPassword(Worker worker) throws SQLException {
		return workerDao.getWorkerByNameAndPassword(worker);
	}

	public Worker getWorkerByWid(String wid) throws SQLException {
		// TODO Auto-generated method stub
		return workerDao.getWorkerByWid(wid);
	}
}
