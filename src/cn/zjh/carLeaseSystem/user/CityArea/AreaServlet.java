package cn.zjh.carLeaseSystem.user.CityArea;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class AreaServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cname = request.getParameter("cName");
		
		SAXReader saxReader;
		try {
			saxReader = new SAXReader();
			InputStream inputStream = this.getClass().getResourceAsStream("/ProvinceCity.xml");
			Document document = saxReader.read(inputStream);
			Element root = document.getRootElement();
			Element ele = (Element) root.selectSingleNode("//city[@name='"+cname+"']");
			
			//响应内容为xml，那么设置页面类型时就要设置text/xml
			response.setContentType("text/xml;charset=utf-8");
			response.getWriter().print(ele.asXML());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
