package cn.zjh.carLeaseSystem.user.service;

import java.sql.SQLException;

import cn.zjh.carLeaseSystem.user.dao.UserDao;
import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.user.domain.UserException;


public class UserService {
	UserDao dao = new UserDao();
	public User login(User form) throws UserException {
		User user = dao.findById(form.getId());
		if(user == null) {
			throw new UserException("您输入的账户不存在！");
		}else {
			String password = user.getPassword();
			if(!password.equals(form.getPassword())) 
				throw new UserException("您输入的密码不正确！");
		}
		return user;
	}
	public void regist(User form) throws UserException {
		User user = dao.findByUid(form);
		if(user != null) {
			throw new UserException("该用户已注册");
		}else {
			try {
				dao.createUser(form);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public User getUserById(String uid) throws SQLException{
		return dao.getUserById(uid);
	}

}
