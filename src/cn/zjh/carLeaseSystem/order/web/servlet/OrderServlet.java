package cn.zjh.carLeaseSystem.order.web.servlet;

import java.awt.print.Pageable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zjh.carLeaseSystem.car.dao.CarDao;
import cn.zjh.carLeaseSystem.car.domain.Car;
import cn.zjh.carLeaseSystem.car.domain.CarCompany;
import cn.zjh.carLeaseSystem.order.domain.Order;
import cn.zjh.carLeaseSystem.order.domain.OrderItem;
import cn.zjh.carLeaseSystem.order.service.OrderService;
import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.worker.domain.Worker;

public class OrderServlet extends BaseServlet {
	
	private OrderService orderService = new OrderService();
	public String buildOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取车辆编号
		String cid = request.getParameter("carId");
		String rentTime = request.getParameter("rentTime");
		String returnTime = request.getParameter("returnTime");
		//设置订单号
		String oid = Long.toString(new Date().getTime());
		//获取车辆，不建议这样使用，破坏了分层结构，应该在url地址栏中把car的数据全传过来，用ssm参数绑定到car对象中。
		Car car = new CarDao().getCarById(cid);
		//将其他数据封装到order中。
		//将returnTime转化成日期传进来。
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date returnTimeDate =  sdf.parse(returnTime);
		Date rentTimeDate = sdf.parse(rentTime);
		Order order = null;
		order = CommonUtils.toBean(request.getParameterMap(), Order.class);
		order.setOid(oid);
		order.setReturnTime(returnTimeDate);
		order.setRentTime(rentTimeDate);
		//算出两个日期相差的天数
        int days=(int) ((returnTimeDate.getTime()-rentTimeDate.getTime())/(1000*3600*24));
		order.setDays(days);
		
		//先为订单创建一个条目项，这样在修改订单项的时候能够少访问一次order表
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(Long.toString(new Date().getTime()));
		//设置实际用户的id
		orderItem.setItemId(orderItem.getItemId());
		order.setRentmoney(car.getPrice()*order.getDays()+100);
		//设置押金，押金公式，押金 = 车辆租金*10;
		order.setCar(car);
		order.setOrderstate(1);	//设置订单状态为1
		order.setCash(car.getPrice()*10);
		order.setOrderItem(orderItem);
		//获取Session中的User对象设置给Order。
		User user = (User)request.getSession().getAttribute("user");
		order.setUser(user);
		if(user!=null){
			//如果user不为空，添加默认用户给orderItem，并将order存储到request域中。
			orderItem.setDname(user.getUsername());
			orderItem.setdIdcard(user.getUid());
			orderItem.setDphone(user.getPhone());
			//userAdd是用户添加订单使用，返回值为oid，如果数据库中已存在未支付的相同订单，那么返回那个订单ID，否则返回null。
			String ooid = orderService.userAdd(order, orderItem);
			if(ooid!=null){
				order = orderService.findOrderById(ooid);	//获取已存在的订单数据
				order.setCar(car);
				order.setUser(user);
				order.setOrderstate(1);	//设置订单状态为1
				order.setCash(car.getPrice()*10);
				order.setOrderItem(orderItem);
			}
			request.setAttribute("order", order);
		}else{	//如果user为空，先将order对象保存到session中，以生成的订单编号为命名，方便游客支付时获取。
			request.getSession().setAttribute(oid, order);
			request.setAttribute("order", order);
		}
		return "f:/WEB-INF/order/order.jsp";
	}

	public String setOrderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String oid = request.getParameter("oid");
		String itemId = request.getParameter("itemId");
		Order order = null;
		OrderItem orderItem = CommonUtils.toBean(request.getParameterMap(), OrderItem.class);
		orderItem.setBook(new Date());
		orderItem.setItemId(itemId);
		if(request.getSession().getAttribute("user")!=null){	//当用户下订单时，第二次完善信息也就是付款时生成完整订单
			orderService.finishInfo(itemId,orderItem);
			order = orderService.findOrderById(oid);
		}else{	//当游客下订单时，需要付款时才能生成订单。
			order = (Order) request.getSession().getAttribute(oid);
			order.setOrderstate(1);
			order.setOrderItem(orderItem);
			orderService.visitorAdd(order, orderItem);	//游客添加订单
		}
		request.setAttribute("order", order);
		return "f:/WEB-INF/pay/pay.jsp";
	}
	
	//为已支付的订单分配工作人员，并且当user不为空时封装order相关类对象整合为orderList，并且还有分页的代码。冗余！
	public String setWorkerforOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String oid = (String)request.getAttribute("oid");
		Worker worker = (Worker)request.getAttribute("worker");
		Order order = orderService.findOrderById(oid);
		order.setWorker(worker);
		orderService.setWidforOrder(oid, worker.getWid());
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			List<Order> orderList = getAllOrdersOfUser(user.getId());
			//为order完善数据。
			for (Order o : orderList) {
				o.setUser(user);
				o = orderService.mybatis(o);	//这块封装mybatis:resultMap，最好弄。
			}
			request.getSession().setAttribute("orderList", orderList);//将orderList列表也存到session域中，方便其他方法操作。
			request.setAttribute("orderList", orderList);
			request.setAttribute("listLen", orderList.size());
			request.setAttribute("current_page", 1);
			//设置初始分页信息
			int listLen = orderList.size();
			int page_end;
			int allPageCounts;
			Properties prop = new Properties();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/pageProperties.properties");
			prop.load(inputStream);
			Integer pageMaxContent = Integer.parseInt(prop.getProperty("pageMaxContent"));//获取页面中放置的最大记录数
			Integer pageCounts = Integer.parseInt(prop.getProperty("pageCounts"));	//获取显示页码的总条数
			//计算出page_end，这是第一页
			if(listLen%pageMaxContent!=0){
				allPageCounts = listLen/pageMaxContent+1;
			}else{
				allPageCounts = listLen/pageMaxContent;
			}
			if(allPageCounts>pageCounts){
				page_end = pageCounts;
			}else{
				page_end = allPageCounts;
			}
			page(request, orderList, 1, 1, page_end);
			return "f:/WEB-INF/order/orderList.jsp";
		}else{
			request.setAttribute("order", order);
			return "f:/WEB-INF/payed.jsp";
		}
	}

	//支付完成后，跳到该方法设置订单状态为已支付
	public String setOrderState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String oid = request.getParameter("oid");
		orderService.setState(oid, 2);
		return "f:/WorkerServlet?method=chooceWorker&oid="+oid;
	}
	
	//删除订单
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		@SuppressWarnings("unchecked")
		List<Order> orderList = (List<Order>) request.getSession().getAttribute("orderList");
		String oid = request.getParameter("oid");
		Integer current_page = Integer.parseInt(request.getParameter("current_page"));
		Integer page_begin = Integer.parseInt(request.getParameter("page_begin"));
		Integer page_end = Integer.parseInt(request.getParameter("page_end"));
		for(int i = 0;i<orderList.size();i++){
			if(orderList.get(i).getOid().equals(oid)){	//找到与oid相等的order订单
				//worker的ordercount要减一
				orderService.decWorkerCount(orderList.get(i));
				orderService.deleteOrder(oid);
				orderList.remove(i);
			}
		}
		
		request.getSession().setAttribute("orderList", orderList);//重新设置进session域
		page(request, orderList, current_page, page_begin, page_end);
		return "f:/WEB-INF/order/orderList.jsp";
	}
	
	//获取用户的所有订单
	private List<Order> getAllOrdersOfUser(String uid) throws SQLException{
		return orderService.getAllOrdersOfUser(uid);
	}
	
	//在订单页面给未付款的订单付款时。
	public String goPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String oid = request.getParameter("oid");
		List<Order> orderList = (List<Order>) request.getSession().getAttribute("orderList");
		Order target = null;
		for (Order order : orderList) {
			if(order.getOid().equals(oid)){
				target = order;
			}
		}
		if(target == null){
			request.setAttribute("msg", "您要找的订单不存在或丢失！请您重新下单吧！！");
			return "f:/WEB-INF/error.jsp";
		}
		request.setAttribute("order", target);
		return "f:/WEB-INF/order/order.jsp";
	}
	
	//分页，接收传进来的页数，设置并返回界面。
	public String setPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		Integer current_page = Integer.parseInt(request.getParameter("page"));
		@SuppressWarnings("unchecked")
		List<Order> orderList = (List<Order>) request.getSession().getAttribute("orderList");
		int page_end = Integer.parseInt(request.getParameter("page_end"));
		int page_begin = Integer.parseInt(request.getParameter("page_begin"));
		page(request, orderList, current_page, page_begin, page_end);	//调用分页方法
		return "f:/WEB-INF/order/orderList.jsp";
	}
	
	//完成分页的所有操作。
	public void page(HttpServletRequest request,List<Order> list,int current_page,int page_begin,int page_end) throws IOException{
		/*
		 * 分页步骤：
		 * 	1.接收参数变量
		 * 		request
		 * 		list：要分页的记录
		 * 		current_page:当前页，由前台JSP页面传来
		 * 		page_begin:页码条的第一个页码
		 * 		page_end:页码条的最后一个页码
		 * 	2.从配置文件中获取变量
		 * 		单页面显示的最大记录数
		 * 		页码条的最大容量
		 * 	3.计算总页数
		 * 	4.设置页码滚动
		 * 	5.加一个判断，当我们删除订单的时候总页数可能会改变，那么当页面传来的当前页等于最后一页时，就需要重新赋值。
		 * 	6.设置request域
		 * */
		//从配置文件中读取数据
		Properties prop = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/pageProperties.properties");
		prop.load(inputStream);
		Integer pageMaxContent = Integer.parseInt(prop.getProperty("pageMaxContent"));//获取页面中放置的最大记录数
		int listLen = list.size();
		int pages;
		//计算总页数
		if(listLen%pageMaxContent!=0){
			pages = listLen/pageMaxContent+1;
		}else{
			pages = listLen/pageMaxContent;
		}
		//设置页码滚动
		if(current_page>((page_begin+page_end)/2)){
			if(page_end<pages){
				page_begin++;
				page_end++;
			}
		}else{
			if(page_begin>1){
				page_begin--;
				page_end--;				
			}
		}
		//如果当前页数大于总页数，那么就是当前页等于最后一页，需要将当前页和page_end都要设置为总页数
		if(current_page>pages){
			current_page = pages;
			page_end = pages;	
		}
		//设置属性转发给jsp
		request.setAttribute("orderList", list);
		request.setAttribute("listLen", list.size());
		request.setAttribute("current_page", current_page);
		request.setAttribute("page_begin", page_begin);
		request.setAttribute("page_end", page_end);
		request.setAttribute("pageMaxContent", pageMaxContent);
		request.setAttribute("pages", pages);
	}

	public String myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String uid = request.getParameter("uid");
		List<Order> orderList = orderService.getOrdersByUid(uid);
		User user = (User)request.getSession().getAttribute("user");
		for (Order order : orderList) {
			order.setUser(user);
			orderService.mybatis(order);
		}
		request.getSession().setAttribute("orderList", orderList);
		request.setAttribute("orderList", orderList);
		request.setAttribute("listLen", orderList.size());
		request.setAttribute("current_page", 1);
		//设置初始分页信息
		int listLen = orderList.size();
		int page_end;
		int allPageCounts;
		Properties prop = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/pageProperties.properties");
		prop.load(inputStream);
		Integer pageMaxContent = Integer.parseInt(prop.getProperty("pageMaxContent"));//获取页面中放置的最大记录数
		Integer pageCounts = Integer.parseInt(prop.getProperty("pageCounts"));	//获取显示页码的总条数
		//计算出page_end，这是第一页
		if(listLen%pageMaxContent!=0){
			allPageCounts = listLen/pageMaxContent+1;
		}else{
			allPageCounts = listLen/pageMaxContent;
		}
		if(allPageCounts>pageCounts){
			page_end = pageCounts;
		}else{
			page_end = allPageCounts;
		}
		page(request, orderList, 1, 1, page_end);
		return "f:/WEB-INF/order/orderList.jsp";
	}
}
