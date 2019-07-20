package cn.zjh.carLeaseSystem.user.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mchange.v2.beans.BeansUtils;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zjh.carLeaseSystem.user.domain.User;
import cn.zjh.carLeaseSystem.user.domain.UserException;
import cn.zjh.carLeaseSystem.user.service.UserService;

public class UserServlet1 extends BaseServlet {
	UserService service = new UserService();
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到Bean中
		 * */
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			user = service.login(user);
			HttpSession session = request.getSession();	//获得Session
			session.setAttribute("user", user);	//将该用户保存到session中。
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/user/login.jsp";
		}
		return "f:/home.jsp";
	}
	
	/*
	 * 	注册功能
	 * 	1.获取表单数据到Bean对象
	 * 	2.传递给service
	 * 	3.
	 * */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		user.setCredit(3);
		Date date = new Date();
		String id = Long.toString(date.getTime());
		user.setId(id);
		try {
			service.regist(user);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/user/regist.jsp";
		}
		request.setAttribute("id", user.getId());
		return "f:/WEB-INF/success.jsp";
	}
}
