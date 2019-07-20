<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>主页</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath }/css/home.css" />
	<script type="text/javascript">
	//检验表单，到时候再说吧
//		function checkForm(){
//			var returnTime = document.getElementsByName("returnTime")[0].value;
//			var rentTime = document.getElementsByName("rentName")[0].value;
//			var date = new Date();
	//		var now = date.getTime();
			
		//}
	
	/*
		这里使用AJax来完成市区联动
		步骤：
			1.当jsp文档加载时，连接xml文档，读取城市列表。
			2.接收服务器的响应为字符串
			3.使用js解析响应数据并将城市信息添加到option元素中。
	*/
		//获取XMLHttpRequest
		function createXmlHttpRequest(){
			try{
				return new XMLHttpRequest();		//非IE浏览器
			}catch(e){
				try{
					return new ActiveXObject("Msxml2.XMLHTTP");	//IE6.0
				}catch(e){
					try{
						return new ActiveXObject("Microsoft.XMLHTTP");
					}catch(e){
						alert("您的浏览器版本过低！");
					}
				}
			}
		}

		window.onload = function(){
			//获取异步对象
			var xmlHttpRequest = createXmlHttpRequest();
			var select1 = document.getElementById("rentLocat");
			var select2 = document.getElementById("returnLocat");
			//创建连接
			xmlHttpRequest.open("GET", "<c:url value='/CityServlet'/>", true);
			//发送请求
			xmlHttpRequest.send(null);
			//获取响应
			xmlHttpRequest.onreadystatechange = function(){
				if(xmlHttpRequest.readyState ==4 && xmlHttpRequest.status ==200){
				console.log(5);
					var result = xmlHttpRequest.responseText;
					addNode(result,select1);
					addNode(result,select2);
					
				}
			};
			//添加节点方法，因为有好几个下拉框，所以提取出来方法。
			function addNode(res,select){
				var list = res.split(",");
				for(var i = 0 ;i<list.length;i++){
					var option = document.createElement("option");
					option.innerHTML = list[i];
					select.appendChild(option);
				}
				loadArea();
			};
			var areas = document.getElementById("areas");
			select1.onchange = loadArea;
			function loadArea() {
				var xmlHttp = createXmlHttpRequest();
				xmlHttp.open("POST", "<c:url value='/AreaServlet'/>", true);
				xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlHttp.send("cName=" + select1.value);//把下拉列表中选择的值发送给服务器！
				xmlHttp.onreadystatechange = function() {
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					/*
					把select中的所有option移除（除了请选择）
					*/
					var AreaSelect = document.getElementById("areas");
					// 获取其所有子元素
					var optionEleList = AreaSelect.getElementsByTagName("option");
					// 循环遍历每个option元素，然后在citySelect中移除
					while(optionEleList.length > 0) {//子元素的个数如果大于0就循环，等于0就不循环了！
						AreaSelect.removeChild(optionEleList[0]);//总是删除0下标，因为1删除了，2就变成1了！
					}
					var doc = xmlHttp.responseXML;
					// 得到所有名为city的元素
					var areaEleList = doc.getElementsByTagName("area");
					// 循环遍历每个city元素
					for(var i = 0; i < areaEleList.length; i++) {
						var areaEle = areaEleList[i];//得到每个city元素
						var areaName;
						if(window.addEventListener) {//处理浏览器的差异
							areaName = areaEle.textContent;//支持FireFox等浏览器
						} else {
							areaName = areaEle.text;//支持IE
						}
						
						// 使用市名称创建option元素，添加到<select name="city">中
						var op = document.createElement("option");
						op.value = areaName;
						// 创建文本节点
						var textNode = document.createTextNode(areaName);
						op.appendChild(textNode);//把文本节点追加到op元素中
						
						//把op添加到<select>元素中
						areas.appendChild(op);
					}
			}
		};		
	};
	}

	</script>
</head>
<body>
    <div id="header">
        <h1>诚安汽车租赁</h1> 
        <div class="header_a">
            <ul class="ul1">
                <li><a href="#" class="a1">车辆信息</a></li>
                <li><a href="${pageContext.request.contextPath }/OrderServlet" class="a1">我的订单</a></li>
                <c:choose>
                	<c:when test="${not empty sessionScope.user }" >
                		<li><span class="lr_b">尊敬的&nbsp;<span style="font-size:16px;text-decoration: underline;">${sessionScope.user.username },</span>&nbsp;您好！</span></li>
                	</c:when>
                 <c:otherwise><li><span class="lr_a"><a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> / <a href="${pageContext.request.contextPath }/user/regist.jsp">注册</a></span></li></c:otherwise>
            	</c:choose>
            </ul>
        </div>
    </div>
    <div id="content">
        <div class="form_info">
            <h3>租车信息</h3>
            <div class="f1">
            <form action="<c:url value='/CarServlet?method=carList'/>" method="post" class="formStyle">
                取车地点&nbsp;&nbsp;&nbsp;&nbsp;
                <select name="rentCity" id="rentLocat">
                
                </select><br/><br/>
                取车区域&nbsp;&nbsp;&nbsp;&nbsp;
                <select name="rentArea" id="areas">
                
                </select><br/><br/>
                还车地点&nbsp;&nbsp;&nbsp;&nbsp;
                <select name="returnCity" id="returnLocat">
                
                 </select><br/><br/>
               	<input type="hidden" name="returnArea"/>
                取车时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"  name="rentTime" placeholder=" 时间格式例如:2010-12-01 12:00 (24小时制)"/><br/>
                <span></span><br/>
                还车时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="returnTime" placeholder=" 时间格式例如:2010-12-08 12:00 (24小时制)"/><br/>
                <span></span><br/>
                <button type="submit">搜索</button>
            </form>
        </div>
        </div>
        <div class="image">
            <img src="images/ad1.jpg" alt="广告">
        </div>
        <div class="pattern">
            <h3>合作伙伴</h3>
            <div class="p1">
                <img src="http://pic.ctrip.com/car_isd/site/join_ban.jpg" alt=""/>
            </div>
            <div class="p2">
                <img src="http://pic.ctrip.com/osdcar/site/isd/vendor/avis-70-42.jpg" alt="" class="i1">
                <img src="http://pic.ctrip.com/osdcar/site/isd/vendor/zhizui-70-42.jpg" alt="" class="i2">
                <img src="http://pic.ctrip.com/osdcar/site/isd/vendor/yihai-70-42.jpg" alt="" class="i2">
                <img src="http://pic.ctrip.com/osdcar/site/isd/vendor/guoxin-70-42.jpg" alt="" class="i2">
            </div>
            <div class="p3">
                <dl>
                    <dt><h1>选择诚安租车的理由</h1></dt>
                    <dd>
                        <i></i>
                        <h2>城市覆盖全</h2>
                        <p>百城千店，总能找到更方便交接的门店</p>
                    </dd>
                    <dd>
                        <i></i>
                        <h2>车型任意选</h2>
                        <p>奥拓到奔驰，百余款车型，总有您喜欢的</p>
                    </dd>
                    <dd>
                        <i></i>
                        <h2>平台比价</h2>
                        <p>多家租车公司，低于官网价，品牌价格任您选</p>
                    </dd>
                    <dd>
                        <i></i>
                        <h2>服务有保证</h2>
                        <p>万辆库存，节日高峰用车有保障</p>
                    </dd>
                </dl>
            </div>
        </div>
        <div class="carList">
            <div class="carList_ul">
                <h3>国内租车</h3>
            </div>
            <div class="car">
                <table class="car_info">
                    <tr>
                        <td>
                            <img src="images/cars/FordFiesta.jpg" alt="FordFiesta">
                            <p class="p1">经济型</p>
                            <p class="p2">福特嘉年华</p>
                            <p class="p3">¥<span>107.00</span>起</p>
                        </td>
                        <td>
                            <img src="images/cars/ChevroletAveo.jpg" alt="ChevroletAveo">
                            <p class="p1">经济型</p>
                            <p class="p2">雪佛兰爱唯欧</p>
                            <p class="p3">¥<span>107.00</span>起</p>
                        </td>
                        <td>
                            <img src="images/cars/ChevroletSail.jpg" alt="ChevroletSail">
                            <p class="p1">经济型</p>
                            <p class="p2">雪佛兰新赛欧</p>
                            <p class="p3">¥<span>115.00</span>起</p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="images/cars/CitroenElysee.jpg" alt="CitroenElysee">
                            <p class="p1">经济型</p>
                            <p class="p2">雪铁龙爱丽舍</p>
                            <p class="p3">¥<span>125.00</span>起</p>
                        </td>
                        <td>
                            <img src="images/cars/BuickExcelle.jpg" alt="BuickExcelle">
                            <p class="p1">经济型</p>
                            <p class="p2">别克凯越</p>
                            <p class="p3">¥<span>134.00</span>起</p>
                        </td>
                        <td>
                            <img src="images/cars/ChevroletEpica.jpg" alt="ChevroletEpica">
                            <p class="p1">经济型</p>
                            <p class="p2">雪佛兰景程</p>
                            <p class="p3">¥<span>134.00</span>起</p>
                        </td>
                    </tr>
                </table>
            </div>

        </div>
        
    </div>
    <div id="footer">
        
    </div>
</body>
</html>