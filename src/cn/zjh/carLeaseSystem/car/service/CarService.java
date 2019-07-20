package cn.zjh.carLeaseSystem.car.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.zjh.carLeaseSystem.car.dao.CarDao;
import cn.zjh.carLeaseSystem.car.domain.Car;
import cn.zjh.carLeaseSystem.car.domain.CarCompany;
import cn.zjh.carLeaseSystem.user.domain.UserException;

public class CarService {
	private CarDao carDao = new CarDao();

	public List<Car> service(String rentCity, String rentArea, String returnCity, String returnArea) throws UserException {
		try {
			List<CarCompany> rentCarCompanyList = carDao.findRentInfoCarCompany(rentCity,rentArea);
			List<CarCompany> returnCarCompanyList = carDao.findRentInfoCarCompany(returnCity, returnArea);
			List<CarCompany> finallyList = new ArrayList<CarCompany>();
			if(rentCarCompanyList == null||returnCarCompanyList == null){
				throw new UserException("当前没有车型");
			}
			for(int i = 0 ;i<rentCarCompanyList.size();i++){
				String rentCarCompanyName = rentCarCompanyList.get(i).getCcname();
				for(int j = 0;j<returnCarCompanyList.size();j++){
					String returnCarCompanyName = returnCarCompanyList.get(j).getCcname();
					if(returnCarCompanyName.equals(rentCarCompanyName)){
						finallyList.add(rentCarCompanyList.get(i));
					}
				}
			}
			List<Car> finalCarList = new ArrayList<Car>();
			for(int i = 0;i<finallyList.size();i++){
				List<Car> list = carDao.getCarList(finallyList.get(i));
				for (Car car : list) {
					car.setCc(finallyList.get(i));
				}
				finalCarList.addAll(list);
			}
			return finalCarList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}

}
