package cn.zjh.carLeaseSystem.car.dao;

import java.sql.SQLException;
import java.util.List;

import javax.enterprise.inject.New;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.zjh.carLeaseSystem.car.domain.Car;
import cn.zjh.carLeaseSystem.car.domain.CarCompany;

public class CarDao {
	private QueryRunner qr = new TxQueryRunner();

	public List<CarCompany> findRentInfoCarCompany(String rentCity, String rentArea) throws SQLException {
		// TODO Auto-generated method stub
		String sql ="select * from cls_carcompany where city=? and area=?";
		return qr.query(sql, new BeanListHandler<CarCompany>(CarCompany.class), rentCity,rentArea);
	}

	public List<Car> getCarList(CarCompany company) throws SQLException {
		String sql = "select * from cls_car where ccid=?";
		return qr.query(sql, new BeanListHandler<Car>(Car.class), company.getCcid());
	}

	public List<Car> carListByType(int type) throws SQLException {
		String sql = "select * from cls_car where type=?";
		return qr.query(sql, new BeanListHandler<Car>(Car.class),type);
	}
	public List<Car> getCompanyByCcid(int ccid) throws SQLException {
		String sql = "select * from cls_carcompany where ccid=?";
		return qr.query(sql, new BeanListHandler<Car>(Car.class),ccid);
	}
	public Car getCarById(String id)throws Exception{
		String sql = "select * from cls_car where carId=?";
		return qr.query(sql, new BeanHandler<Car>(Car.class), id);
	}
}
