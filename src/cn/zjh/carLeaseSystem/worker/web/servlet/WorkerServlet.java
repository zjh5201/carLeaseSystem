package cn.zjh.carLeaseSystem.worker.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zjh.carLeaseSystem.order.domain.Order;
import cn.zjh.carLeaseSystem.order.domain.OrderItem;
import cn.zjh.carLeaseSystem.order.service.OrderService;
import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.user.domain.UserException;
import cn.zjh.carLeaseSystem.user.service.UserService;
import cn.zjh.carLeaseSystem.worker.domain.Worker;
import cn.zjh.carLeaseSystem.worker.service.WorkerService;

public class WorkerServlet extends BaseServlet {

	private WorkerService workerService = new WorkerService();
	private OrderService orderService = new OrderService();
	private UserService userService = new UserService();
	
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到Bean中
		 * */
		Worker worker = CommonUtils.toBean(request.getParameterMap(), Worker.class);
		try {
			worker = workerService.login(worker);
			HttpSession session = request.getSession();	//获得Session
			session.setAttribute("worker", worker);	//将该用户保存到session中。
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/worker/login.jsp";
		}
		return "f:/WEB-INF/worker/index.jsp";
	}
	
	
	//为已结单订单分配工作人员
	public String chooceWorker(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		/*
		 * 1.获取Oid以备找到合适员工后传递给OrderServlet
		 * 2.查询Worker表，返回一个Worker集合。
		 * 3.对worker集合中的orderCount进行排序，找出值最少的那个，然后保存数据，转发到OrderServlet
		 * */
		String oid = request.getParameter("oid");
		
		List<Worker> workerList = workerService.selectAllWorkers();
		workerList.sort(new Comparator<Worker>() {
			public int compare(Worker w1, Worker w2) {
				return w1.getOrdercount()-w2.getOrdercount();
			}
		});
		//每位某位员工分配一比订单，那么他的订单数就要加一
		workerService.setWorkerOrderCount(workerList.get(0).getWid());
		request.setAttribute("worker", workerList.get(0));
		request.setAttribute("oid", oid);
		return "f:/OrderServlet?method=setWorkerforOrder";
	}
	//进入工作人员后台欢迎界面
	public String welcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		Worker worker = (Worker) request.getSession().getAttribute("worker");
		//获取worker完整信息
		worker = workerService.getWorkerByNameAndPassword(worker);
		Long count = orderService.currentOrderCount(worker);
		request.getSession().setAttribute("worker", worker);
		request.setAttribute("currentOrderCount", count);
		request.setAttribute("worker", worker);
		return "f:/WEB-INF/worker/welcome.jsp";
		
	}
	
	//查询所负责订单
	public String orderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String wid = request.getParameter("wid");
		Worker worker = workerService.getWorkerByWid(wid);
		List<Order> list = orderService.getOrdersByWid(worker.getWid());
		for (Order order : list) {
			order = orderService.mybatis(order);
		}
		request.setAttribute("orderList", list);
		request.setAttribute("worker", worker);
		return "f:/WEB-INF/worker/member-list.jsp";
	}
	
	//展示订单信息
	public String showOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String oid = request.getParameter("oid");
		Order order = orderService.getOrderByOid(oid);
		order = orderService.mybatis(order);
		request.setAttribute("order", order);
		return "f:/WEB-INF/worker/member-add.jsp";
	}
	//编辑订单
	public String editOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		Order order = CommonUtils.toBean(request.getParameterMap(), Order.class);
		orderService.updateOrder(order.getOid(),order);
		order = orderService.mybatis(order);
		request.setAttribute("order", order);
		request.setAttribute("message","点击关闭刷新页面");
		return "f:/WEB-INF/success.jsp";
	}
	
	//删除订单
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String oid = request.getParameter("oid");
		System.out.println(oid);
		orderService.deleteOrder(oid);
		request.setAttribute("message","点击关闭刷新页面");
		return "f:/WEB-INF/worker/member-list.jsp";	
	}
	
	//查看用户账户信息（用户）
	public String showUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String uid = request.getParameter("uid");
		User user = userService.getUserById(uid);
		request.setAttribute("user", user);
		request.setAttribute("model",1);
		return "f:/WEB-INF/worker/member-show.jsp";
		
	}
	
	//查看驾驶人信息
		public String showVisitor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
			String itemId = request.getParameter("itemId");
			OrderItem orderItem = orderService.getOrderItemByItemId(itemId);
			request.setAttribute("orderItem", orderItem);
			request.setAttribute("model",2);
			return "f:/WEB-INF/worker/member-show.jsp";
		}
		
	public String showOrderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String itemId = request.getParameter("itemId");
		
		return "";
	}
}