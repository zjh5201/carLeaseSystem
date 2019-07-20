package cn.zjh.carLeaseSystem.user.CityArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

public class CityServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解析XML文档，并返回城市列表给home.jsp
		SAXReader saxReader;
		try {
			saxReader = new SAXReader();
			InputStream inputStream = this.getClass().getResourceAsStream("/ProvinceCity.xml");
			Document document = saxReader.read(inputStream);
			Element root = document.getRootElement();
			List<Node> list = root.selectNodes("//city/@name");
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for(Node node : list){
				sb.append(node.getText());
				i++;
				if(i<list.size()){
					sb.append(",");
				}
			}
			
			//这里设置response，将相应内容返回给客户端
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(sb.toString());
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
