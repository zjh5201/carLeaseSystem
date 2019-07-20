package cn.zjh.carLeaseSystem.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.zjh.carLeaseSystem.user.domain.User;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner(); 
	
	//按用户名和身份证查找数据库。  有重名，但是身份证是不会重复的。
	public User findByUid(User form){
			String sql = "select * from cls_user where uid=?";
			try {
				return qr.query(sql, new BeanHandler<User>(User.class),form.getUid());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	public void createUser(User user) throws SQLException {
		String sql = "insert into cls_user values(?,?,?,?,?,?,?)";
		Object[] params = {user.getId(),user.getUid(),user.getUsername(),user.getPassword(),user.getPhone()
				,user.getEmail(),user.getCredit()};
		qr.update(sql, params);
	}

	public User findById(String id) {
		String sql = "select * from cls_user where id=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class),id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User getUserById(String uid) throws SQLException {
		String sql = "select * from cls_user where id=?";
		
		return qr.query(sql, new BeanHandler<User>(User.class),uid);
	}
	
}
