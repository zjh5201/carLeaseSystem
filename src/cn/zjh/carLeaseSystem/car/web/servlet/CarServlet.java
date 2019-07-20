package cn.zjh.carLeaseSystem.car.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.itcast.servlet.BaseServlet;
import cn.zjh.carLeaseSystem.car.domain.Car;
import cn.zjh.carLeaseSystem.car.service.CarService;
import cn.zjh.carLeaseSystem.user.domain.UserException;
import net.sf.json.JSONArray;

public class CarServlet extends BaseServlet {
	private CarService carService = new CarService();
	private int types;	//车辆类型
	private List<Car> checkList = new ArrayList<Car>();	//表示通过checkbox复选框筛选出的车辆集合
	private List<Car> finalList = new ArrayList<Car>();	//表示所有的车辆集合
	private List<Car> returnList = new ArrayList<Car>();	//表示最终返回给前台的车辆集合
	private boolean highLow = true;

	public void responseJson(List<Car> list, HttpServletResponse response) throws ServletException, IOException{
		response.getWriter().print(JSONArray.fromObject(list));
	}
	public String carList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnCity = request.getParameter("returnCity");
		String rentCity = request.getParameter("rentCity");
		String rentTime = request.getParameter("rentTime");
		String rentArea = request.getParameter("rentArea");
		String returnTime = request.getParameter("returnTime");
		String returnArea = request.getParameter("returnArea");
		System.out.println(returnArea);
		if(returnArea == null || returnArea.equals("")){	//如果为空，设置默认值
			if(rentCity.equals(returnCity)){
				returnArea = rentArea;
			}else{
				returnArea = getReturnArea(returnCity);
			}
		}
		request.setAttribute("returnArea", returnArea);
		request.setAttribute("rentArea", rentArea);
		request.setAttribute("returnCity", returnCity);
		request.setAttribute("rentCity", rentCity);
		request.setAttribute("rentTime", rentTime);
		request.setAttribute("returnTime", returnTime);
		try {
			finalList = carService.service(rentCity,rentArea,returnCity,returnArea);
			finalList.sort(new Comparator<Car>(){
				public int compare(Car c1, Car c2) {
					return c1.getPrice()-c2.getPrice();
				}
			});
			request.setAttribute("list", finalList);
		} catch (UserException e) {
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		return "f:/carList.jsp";
	}
	public void carsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnCity = request.getParameter("returnCity");
		String rentCity = request.getParameter("rentCity");
		String rentArea = request.getParameter("rentArea");
		String returnArea;
		//给typeList让他等于null，这样当我们使用热销时排序就是按照finalList进行排序
		
		if(rentCity.equals(returnCity)){
			returnArea = rentArea;
		}else{
			returnArea = getReturnArea(returnCity);
		}
		try {
			finalList = carService.service(rentCity,rentArea,returnCity,returnArea);
			if(highLow == true){
				finalList.sort(new Comparator<Car>(){
					public int compare(Car c1, Car c2) {
						return c1.getPrice()-c2.getPrice();
					}
				});				
			}else{
				finalList.sort(new Comparator<Car>(){
					public int compare(Car c1, Car c2) {
						return c2.getPrice()-c1.getPrice();
					}
				});
			}
			types = 1;
			returnList.clear();
			checkList.clear();
			returnList.addAll(finalList);
			checkList.addAll(finalList);
			response.setContentType("text/json;charset=utf-8");
			responseJson(returnList, response);
		} catch (UserException e) {
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
		}
	}
	//使用Dom4j来搜索还车的第一个
	private String getReturnArea(String returnCity) {
		SAXReader saxReader = new SAXReader();
		List<Element> list;
		InputStream input = this.getClass().getResourceAsStream("/ProvinceCity.xml");
		try {
			Document document = saxReader.read(input);
			Element root = document.getRootElement();
			//获取City节点
			Element ele = (Element) root.selectSingleNode("//city[@name='"+returnCity+"']");
			list = ele.elements("area");
			return list.get(0).getText();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	public void decend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String value = request.getParameter("value");
		if(value!=null){
			if(value.equals("low")){
				highLow = true;
			}else{
				highLow = false;
			}		
		}
		if(highLow == false){
			returnList.sort(new Comparator<Car>() {
				public int compare(Car o1, Car o2) {
					return o2.getPrice()-o1.getPrice();
				}
			});			
		}else{
			returnList.sort(new Comparator<Car>() {
				public int compare(Car o1, Car o2) {
					return o1.getPrice()-o2.getPrice();
				}
			});				
		}
		responseJson(returnList, response);
	}
	public void carListByType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		returnList.clear();
		response.setContentType("text/json;charset=utf-8");
		types = Integer.parseInt(type);
		/*
		 * 当type为0时就是表示所有类型的车辆，这就用checkList返回即可。因为checkList表示从finalList中根据复选框条件选出的车辆集合
		 * */
		if(types == 0){
			returnList.addAll(checkList);
			responseJson(returnList, response);
			//这里必须return;否则还会继续执行下面的代码，导致返回的json数据格式不对
			return;
		}
		for (Car car : checkList) {
			if(car.getType() == types){
				returnList.add(car); 
			}
		}
		decend(request, response);
	}
	public void getCarListByCheckBox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//先清空初始化
		returnList.clear();	
		checkList.clear();
		response.setContentType("text/json;charset=utf-8");
		//定义两个中间集合变量，让list作为最终的returnList返回
		List<Car> list = new ArrayList<Car>();
		//ff作为每次筛选条件后的存储集合
		List<Car> ff = new ArrayList<Car>();
		ff.addAll(finalList);
		//当出现三个参数都为空的时候，returnList = finalList;
		int x = 0;
		String cc = request.getParameter("cc");
		String gear =request.getParameter("gear");
		String brand =request.getParameter("brand");
		String[] ccArr = cc.split(" ");
		String[] gearArr = gear.split(" ");
		String[] brandArr = brand.split(" ");
		if(cc.equals("")){
			x++;
		}else{
			for(int i = 0;i<ccArr.length;i++){
				for (Car car : ff) {
					if(ccArr[i].equals(car.getCc().getCcname())){
						list.add(car);
					}
				}
			}
		}
		if(list.size()!=0){
			ff.clear();
			ff.addAll(list);
		}
			
		if(gear.equals("")){
			x++;
		}else{
			list.clear();
			for(int i = 0;i<gearArr.length;i++){
				for (Car car : ff) {
					if(Integer.parseInt(gearArr[i]) == car.getGear()){
						list.add(car);
					}
				}
			}
		}
		if(list.size()!=0){
			ff.clear();
			ff.addAll(list);
			
		}
		if(brand.equals("")){
			x++;
		}else{
			list.clear();
			for(int i = 0;i<brandArr.length;i++){
				for (Car car : ff) {
					if(brandArr[i].equals(car.getBrand())){
						list.add(car);
					}
				}
			}
		}
		if(x==3){
			checkList.addAll(finalList);
		}else{
			checkList.addAll(list);
		}
		for (Car car : checkList) {
			if(types==0){
				returnList.addAll(checkList);
				break;
			}
			if(car.getType() == types){
				returnList.add(car);
			}
		}
		decend(request, response);
	}	
}
