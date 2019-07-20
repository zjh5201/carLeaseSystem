package cn.zjh.carLeaseSystem.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {}
	public void destroy() {}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws IOException, ServletException {
		HttpServletRequest request1 = (HttpServletRequest)request;
		HttpServletResponse response1 = (HttpServletResponse)response;
		request1.setCharacterEncoding("UTF-8");
		//1.获取URL路径
		String url = request1.getRequestURL().toString();
		//2.获取参数列表
		String query = request1.getQueryString();
		/*
		 * 3.判断URL路径，如果是以.css和.js结尾，那么代表他是静态文件，那么就会进行过滤
		 * 过滤的方式是加一个时间戳time的参数
		 * */
		if(url!=null&&!url.trim().isEmpty()&&(url.endsWith(".css")||url.endsWith(".js"))){
			String newUrl;
			if(query==null||query.trim().isEmpty()){
				newUrl = url+"?timesquence="+new Date().getTime();
				//重定向到新的url页面
				response1.sendRedirect(newUrl);
				return;
			}
			
			/*
			 * 这是原来就有time属性的情况，如果有就代表是被我们过滤的才有，如果在写以下代码，就会出现
			 * 重定向循环，永不结束
			 * */
//			if(query!=null&&!query.trim().isEmpty()&&query.contains("time")){
//				query = query.replace("time", "");
//				newUrl = url+"?"+query+"&time="+new Date().getTime();
//				response1.sendRedirect(newUrl);
//				return;
//			}
			if(query!=null&&!query.trim().isEmpty()&&!query.contains("timesquence")){
				newUrl = url+"?"+query+"&timesquence="+new Date().getTime();
				response1.sendRedirect(newUrl);
				filter.doFilter(request1, response1);
				return;
			}
			filter.doFilter(request1, response1);
			return;
		}else
			//如果访问的不是以css和js结尾的文件，那么直接往下执行
			filter.doFilter(request1, response1);
	}
}
