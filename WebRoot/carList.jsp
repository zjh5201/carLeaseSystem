<%@page import="cn.zjh.carLeaseSystem.user.domain.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta   http-equiv="Expires"   CONTENT="0">
    <meta   http-equiv="Cache-Control"   CONTENT="no-cache">
    <meta   http-equiv="Pragma"   CONTENT="no-cache">
    <title>carList</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath }/css/carList.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/css/getXMLHttpRequest.js"></script>
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

		function loadCarList(method,query){
		//加载前要先清除原先所有子节点
			var table = document.getElementById("tb");
			table.innerHTML = "";	//清除所有子节点的方法
			var rentCity = '<%=request.getAttribute("rentCity") %>';
			var rentArea = '<%=request.getAttribute("rentArea") %>';
			var returnCity = '<%=request.getAttribute("returnCity") %>';
			var returnArea = '<%=request.getAttribute("returnArea") %>';
			var returnCity = '<%=request.getAttribute("returnCity") %>';
			var rentTime = '<%= request.getAttribute("rentTime") %>';
			var returnTime = '<%= request.getAttribute("returnTime") %>';
	//		var uid = ${sessionScope.user.uid};
			var carCount = document.getElementById("carCount");
			var xmlHttpRequest = createXmlHttpRequest();
			xmlHttpRequest.open("POST","<c:url value='/CarServlet?method="+method+"'/>",true);
			xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttpRequest.send("rentCity="+rentCity+"&rentArea="+rentArea+"&returnCity="+returnCity+"&returnArea="+rentArea+query);
			xmlHttpRequest.onreadystatechange = function(){
				if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
					var carList = xmlHttpRequest.response;
					var obj = JSON.parse(carList);
					console.log("obj="+obj);
					carCount.innerHTML = obj.length;
					if(obj.length == 0||obj == null){
						var tr1 = document.createElement("tr");
						var td1 = document.createElement("td");
						td1.className = "errtd1";
						var img = document.createElement("img");
						img.src = "images/err2.png";
						img.className = "errimg";
						td1.appendChild(img);
						var p1 = document.createElement("p");
						p1.className = "errp1";
						p1.innerHTML = "抱歉，没找到符合要求的车型";
						td1.appendChild(p1);
						var p2 = document.createElement("p");
						p2.className = "errp2";
						p2.innerHTML = "未找到符合条件的车型，修改条件或看看其他车型";	
						td1.appendChild(p2);
						tr1.appendChild(td1);
						table.appendChild(tr1);	
						td1.style.border = "0px";
					}else{
						for(var i = 0;i<obj.length;i++){
							var tr = document.createElement("tr");
							tr.className = 'trr';
							var td = document.createElement("td");
						//	tr.appendChild(td);
							var _img = document.createElement("div");
							_img.className = '_img';
							var img = document.createElement("img");
							img.src = obj[i].carImage;
							var _img_p = document.createElement("p");
							_img_p.className = 'common';
							_img_p.innerHTML = obj[i].brand+obj[i].model;
							var gear = document.createElement("p");
							gear.className = 'gear';
							if(obj[i].type==1){
								gear.innerHTML = '手动挡';
							}else{
								gear.innerHTML = '自动挡';
							}
							_img.appendChild(img);
							_img.appendChild(_img_p);
							_img.appendChild(gear);
							td.appendChild(_img);
							var _info = document.createElement("div");
							_info.className = "_info";
							var _info_p1 = document.createElement("p");
							_info_p1.className = "ccname";
							_info_p1.innerHTML = obj[i].cc.ccname;
							var _info_p2 = document.createElement("p");
							_info_p2.className = "ccphone";
							_info_p2.innerHTML = obj[i].cc.phone;
							var _info_p3 = document.createElement("p");
							_info_p3.className = "myqh";
							_info_p3.innerHTML = '满油取还';
							_info.appendChild(_info_p1);
							_info.appendChild(_info_p2);
							_info.appendChild(_info_p3);
							td.appendChild(_info);
							var _button = document.createElement("div");
							_button.className = "_button";
							var _a = document.createElement("a");
							_a.href = "<c:url value='/OrderServlet?method=buildOrder&carId="+obj[i].carId+"&uid="+obj[i].uid+"&rentCity="+rentCity+"&rentArea="+rentArea
							+"&returnCity="+returnCity+"&returnArea="+returnArea+"&rentTime="+rentTime+":00&returnTime="+returnTime+":00'/>";
							_a.innerHTML = "预订";
							_button.appendChild(_a);
							td.appendChild(_button);
							var _money = document.createElement("div");
							_money.className = "_money";
							var _money_p = document.createElement("p");
							_money_p.className = "price";
							_money_p.innerHTML = "¥"+obj[i].price;
							var _money_span = document.createElement("span");
							_money_span.innerHTML = "/天";
							_money_p.appendChild(_money_span);
							_money.appendChild(_money_p);
							td.appendChild(_money);
							tr.appendChild(td);
							table.appendChild(tr);
						}
					}

				}
			}
		};
	//使用ajax来实时获取各类型的车辆信息
	function carTypes(){
		//车辆类型：热销 经济....
			var t1 = document.getElementById("li1");
			var t2 = document.getElementById("li2");
			var t3 = document.getElementById("li3");
			var t4 = document.getElementById("li4");
			var t5 = document.getElementById("li5");
			var t6 = document.getElementById("li6");
			var t7 = document.getElementById("li7");
			var t8 = document.getElementById("li8");
			t1.onclick = function(){
				loadCarList("carListByType","&type="+0);
				change("li1");
			}
			t2.onclick = function(){
				loadCarList("carListByType","&type="+1);
				change("li2");
			}
			t3.onclick = function(){
				loadCarList("carListByType","&type="+2);
				change("li3");
			}
			t4.onclick = function(){
				loadCarList("carListByType","&type="+3);
				change("li4");
			}
			t5.onclick = function(){
				loadCarList("carListByType","&type="+4);
				change("li5");
			}
			t6.onclick = function(){
				loadCarList("carListByType","&type="+5);
				change("li6");
			}
			t7.onclick = function(){
				loadCarList("carListByType","&type="+6);
				change("li7");
			}
			t8.onclick = function(){
				loadCarList("carListByType","&type="+7);
				change("li8");
			}
		}
	//更细致的使用各种选项来筛选车辆
	
	var strs = ["&cc=","&gear=","&brand="];
	/*
		用来完成租车公司复选框的选择并使用ajax传递参数给后台。
	*/
	function optionals(name){
		var cc = document.getElementsByName(name);
		for(var i = 0;i<cc.length;i++){
			cc[i].x = "false";		//为cc一行的checkbox设置x属性并赋值为false，该属性用来作为表示checkbox框的选中和未选中的两种状态 一个是true、一个false
		}
		firstIsChecked(name);	//初始时，默认被选中的是不限
		for(var i=0;i<cc.length;i++){ 
            (function(n){ //这个是function里n，即function的形参，也可以换成j，换成什么变量名都无所谓
                cc[n].onclick=function(){ 
                    checks(cc[n],name);
                    loadCarList("getCarListByCheckBox",(strs[0]+strs[1]+strs[2]));
					console.log(strs[0]+strs[1]+strs[2]);
                }
            })(i);//这是循环中的i,被作为参数传入
        }
	}
		//设置checkbox的第2个之后的checked属性都为false。就是默认不选中的状态
		function setCheckedAttr(name){
			var o = document.getElementsByName(name);
			for(var i = 1;i<o.length;i++){
				o[i].x = "false";		//当点击不限时，要将第1个元素之后的所有标签的x属性赋值为false，否则会出现异常，相当于初始化操作。
				o[i].checked = false;
			}
		}
		//检测方法，最终目的是生成参数字符串，来传递给服务器
		function checks(opp,name){
			var index;
			if(name == "cc"){
				index = 0;
			}else if(name == "gear"){
				index = 1;
			}else{
				index = 2;
			}
			if(opp.value == "不限"){
				setCheckedAttr(name);
				strs[index] = "&"+name+"=";
				return;
			}
			if(opp.x == "true"){
				opp.x = "false";
				var ind = strs[index].indexOf(opp.value);
				console.log("  ind="+ind);
				strs[index] = strs[index].substring(0,ind)+strs[index].substring(ind+opp.value.length);
			}else{
				opp.x = "true";
				strs[index] = strs[index].concat(opp.value.toString());
			}
			firstIsChecked(name);
		}
		//当所有checkbox都未选中时，则不限被选中
		function firstIsChecked(name){
			var op = document.getElementsByName(name);
			for(var i = 1;i<op.length;i++){
				if(op[i].x=="true"){
					op[0].checked = false;
					return;
				}
			}
			op[0].checked = "checked";		
	}
	//加载城市的所有区
		function loadArea(select,select2,requestValue,area) {
			console.log("area="+area)
			var xmlHttp = createXmlHttpRequest();
			xmlHttp.open("POST", "<c:url value='/AreaServlet'/>", true);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			if(requestValue == null)
				xmlHttp.send("cName=" + select.value);//把下拉列表中选择的值发送给服务器！
			else
				xmlHttp.send("cName=" + requestValue);//把request的值发送给服务器！
			xmlHttp.onreadystatechange = function() {
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					/*
					把select中的所有option移除（除了请选择）
					*/
					// 获取其所有子元素
					var optionEleList = select2.getElementsByTagName("option");
					// 循环遍历每个option元素，然后在citySelect中移除
					while(optionEleList.length > 0) {//子元素的个数如果大于0就循环，等于0就不循环了！
						select2.removeChild(optionEleList[0]);//总是删除0下标，因为1删除了，2就变成1了！
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
						select2.appendChild(op);
						if(area != null)	//判断当area参数不为空时，设置Areaselect的值为area
							select2.value=area;
					}
			}
		};
	};
		//添加节点方法，因为有好几个下拉框，所以提取出来方法。
		function addNode(res,select,select2,city,area){
			var list = res.split(",");
			for(var i = 0 ;i<list.length;i++){
				var option = document.createElement("option");
				option.innerHTML = list[i];
				select.appendChild(option);
			}
			loadArea(select,select2,city,area);
		};
		window.onload = function(){
			//获取异步对象
			var xmlHttpRequest = createXmlHttpRequest();
			var select1 = document.getElementById("rentLocat");
			var select2 = document.getElementById("returnLocat");
			var s3 = document.getElementById("rentArea");
			var s4 = document.getElementById("returnArea");
			var rentCity = '<%=request.getAttribute("rentCity") %>';
			var rentArea = '<%=request.getAttribute("rentArea") %>';
			var returnCity = '<%=request.getAttribute("returnCity") %>';
			var returnArea = '<%=request.getAttribute("returnArea") %>';
			var returnCity = '<%=request.getAttribute("returnCity") %>';
			//创建连接
			xmlHttpRequest.open("GET", "<c:url value='/CityServlet'/>", true);
			//发送请求
			xmlHttpRequest.send(null);
			//获取响应
			xmlHttpRequest.onreadystatechange = function(){
				if(xmlHttpRequest.readyState ==4 && xmlHttpRequest.status ==200){
					var result = xmlHttpRequest.responseText;
					//切记加''号
					addNode(result,select1,s3,rentCity,rentArea);
					addNode(result,select2,s4,returnCity,returnArea);
					select1.value = rentCity;
					select2.value = returnCity;
					console.log(returnArea);
					loadCarList("carsList");
				}
			};
			/*正确写法,	市、区联动*/
			select1.onchange = function(){
				loadArea(select1,s3);
			}
			select2.onchange = function(){
				loadArea(select2,s4);
			}
			//初始选择为由低到高
			var priceLowHigh = document.getElementById("chooce");
			priceLowHigh.value="low";
			//按价格排序
			var chooce = document.getElementById("chooce");
			chooce.onchange = function(){
				var value = chooce.value;
				console.log(value);
				loadCarList("decend","&value="+value);
			};
			//加载文档时，把所有的复选框第一个之后的checked属性赋值为false，也就是清除checked属性
			setCheckedAttr("cc");
			setCheckedAttr("gear");
			setCheckedAttr("brand");
			//使用ajax技术动态刷新汽车列表信息
			carTypes();
			//使用ajax技术动态刷新公司信息
			optionals("cc");
			optionals("gear");
			optionals("brand");
	}
	</script>
</head>
<body>
    <div id="header">
        <h1>诚安汽车租赁</h1>
        <div class="header_a">
            <ul class="ul1">
                <li><a href="#" class="a1">车辆信息</a></li>
                <c:choose>
                  	<c:when test="${not empty sessionScope.user }">
                		<li><a href="${pageContext.request.contextPath }/OrderServlet?method=myOrders&uid=${sessionScope.user.id}" class="a1">我的订单</a></li>
                		<li><span class="lr_b">尊敬的&nbsp;<span style="font-size:16px;text-decoration: underline;">${sessionScope.user.username },</span>&nbsp;您好！</span></li>
                	</c:when>
                	<c:otherwise>
                		<li><a href="${pageContext.request.contextPath }/OrderServlet?method=myOrders&uid=${sessionScope.user.id}" class="a1">查询订单</a></li>
                		<li><span class="lr_a"><a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> / <a href="${pageContext.request.contextPath }/user/regist.jsp">注册</a></span></li>
                	</c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
    <div id="content">
         <div class="c1">
             <form action="${pageContext.request.contextPath }/CarServlet" method="post">
             	<input type="hidden" name="method" value="carList"/>
                <div class="rent">
                    <p class="p1">取车地点</p>
                    <select name="rentCity" id="rentLocat">
                    </select>
                    <select name="rentArea" id="rentArea">
                    </select>
                </div>
                <div class="return">
                    <p class="p1">还车地点</p>
                    <select name="returnCity" id="returnLocat">
                    </select>
                    <select name="returnArea" id="returnArea">
                    </select>
                </div>
                <button type="submit" class="seek">重新搜索</button>
                <div class="returnTime">
                    <p class="p2">还车时间</p>
                    <input type="text" name="returnTime"  value="${requestScope.rentTime }" placeholder=" 时间格式例如:2010-12-01 12:00 (24小时制)" class="i2"/>
                </div>
                <div class="rentTime">
                    <p class="p2">租车时间</p>
                    <input type="text" name="rentTime"  value="${requestScope.returnTime }" placeholder=" 时间格式例如:2010-12-01 12:00 (24小时制)" class="i1"/>
                </div>
                <br/>
            </form>
        </div>
        <div class="carType" id="cartype">
            <div class="list">
                <ul id="u1">
                    <li  id="li1">
                        <p>热销</p>
                        <img src="images/cars/hot.png" alt="hot" class="hot"/>
                    </li>
                    <li id="li2">
                        <p>经济型</p>
                        <img src="images/cars/2.png"alt="2">
                    </li>
                    <li  id="li3">
                        <p>舒适型</p>
                        <img src="images/cars/3.png" alt="舒适型">
                    </li>
                    <li  id="li4">
                        <p>SUV</p>
                        <img src="images/cars/6.png" alt="6">
                    </li>
                    <li  id="li5">
                        <p>商务车</p>
                        <img src="images/cars/4.png" alt="4">
                    </li>
                    <li id="li6">
                        <p>豪华型</p>
                        <img src="images/cars/5.png" alt="5">
                    </li>
                    <li id="li7">
                        <p>跑 车</p>
                        <img src="images/cars/9.png" alt="跑车">
                    </li>
                    <li id="li8">
                        <p>小巴士</p>
                        <img src="images/cars/7.png" alt="巴士">
                    </li>
                </ul>
            </div>
            <div class="carCompany" id="xuanxiang">
                    <table>
                        <tr>
                            <td class="mark">租车公司</td>
                            <td><input name="cc" type="checkbox" value="不限" checked="" /> 不限</td>
                            <td><input name="cc" type="checkbox"  value="悟空租车 "/> 悟空租车</td>
                            <td><input type="checkbox" name="cc" value="大方租车 "/> 大方租车</td>
                            <td><input type="checkbox" name="cc" value="瑞卡租车 "/> 瑞卡租车</td>
                            <td><input type="checkbox" name="cc" value="保军租车 "/> 保军租车</td>
                            <td><input type="checkbox" name="cc" value="的亚租车 "/> 的亚租车</td>
                        </tr>
                        <tr>
                            <td class="mark">车辆排挡</td>
                            <td><input type="checkbox" name="gear" value="不限"  checked="checked" /> 不限</td>
                            <td><input type="checkbox" name="gear" value="2 "/> 自动挡</td>
                            <td><input type="checkbox" name="gear" value="1 "/> 手动挡</td>                                
                        </tr>
                        <tr>
                            <td class="mark">车型品牌</td>
                            
                                <td><input type="checkbox" name="brand" value="不限"  checked="checked" /> 不限</td>
                                <td><input type="checkbox" name="brand" value="奔驰 "/> 奔驰</td>
                                <td><input type="checkbox" name="brand" value="宝马 "/> 宝马</td>
                                <td><input type="checkbox" name="brand" value="保时捷 "/> 保时捷</td>
                                <td><input type="checkbox" name="brand" value="福特 "/> 福特</td> 
                                <td><input type="checkbox" name="brand" value="奥迪 "/> 奥迪</td>
                                <td><input type="checkbox" name="brand" value="雪佛兰 "/> 雪佛兰</td>
                                <td><input type="checkbox" name="brand" value="丰田 "/> 丰田</td> 
                                <td><input type="checkbox" name="brand" value="别克 " /> 别克</td>
                                <td><input type="checkbox" name="brand" value="大众 "/> 大众</td>
                            
                        </tr>
                    </table>
                </div>
                <div class="_xuanxiang">
            	<div class="_div">
            		<button id="optional">租车公司，档位，品牌等选项▼</button>
            	</div>
            </div>
        </div>
        <!-- 实现下拉选项列表的显示与隐藏 -->
        <script type="text/javascript">
        	var chooce = false;
        	var btn = document.getElementById("optional");
        	var xuanxiang = document.getElementById("xuanxiang");
        	var carType = document.getElementById("cartype");
        	xuanxiang.style.display = "none";
        	carType.style.height = "130px";
        	btn.onclick = function(){
        		if(chooce == true){
        			xuanxiang.style.display = "none";
        			btn.innerHTML = "租车公司，档位，品牌等选项 ▼";
        			chooce = false;
        			btn.style.width ="210px";
        			carType.style.height = "130px";
        		}
        		else{
        			xuanxiang.style.display = "table";
        			btn.innerHTML = "&nbsp;&nbsp;收起选项 ▲";
        			btn.style.width ="150px";
        			
        			carType.style.height = "330px";
        			chooce = true;
        		}
        	}
			        	
        </script>
        <div id="changed_info">
            <p style="font-size:17px;font-weight: bold;margin-top:10px;font-family: '微软雅黑';color:rgb(51, 51, 51);font-size:21px;">当前<span id="carCount" style="color:rgb(37,119,229);"></span>款车型可选</p>
            <div class="cars_info">
                <div class="choose">
                    <select name="pay" id="chooce">
                        <option value="low">价格从低到高</option>
                        <option value="high" >价格从高到低</option>
                    </select>
                </div>
                <div class="cars_list">
                    <table id="tb">
                    </table>
                </div>
            </div>
            <div class="question">
				<p style="color:rgb(51,51,51);font-size:19px;font-weight:bold;">租车常见问答</p>
				<p style="color:rgb(51,51,51);font-size:15px;margin:15px 0;"><span style="font-weight:bold;color:rgb(51,51,51);font-size:15px">Q</span>&nbsp;&nbsp;&nbsp;取车需要携带什么证件？</p>
				<p style="font-size:15px;color:rgb(102,102,102);text-indent:2em;">携带本人身份证原件(部分产品支持护照、回乡证、台胞证)、本人中国驾驶证正副本和本人国内信用卡（部分产品支持无需信用卡）办理取车手续。身份证有效期需要在1个月以上。驾驶证有效期需要在2个月以上，信用卡有效期需1个月以上。</p>
				<p style="color:rgb(51,51,51);font-size:15px;margin:15px 0;"><span style="font-weight:bold;color:rgb(51,51,51);font-size:15px">Q</span>&nbsp;&nbsp;&nbsp;租车费用包含？</p>
				<p style="font-size:15px;color:rgb(102,102,102);text-indent:2em;">费用包含租车费、基本保障服务费用、车行手续费；如果您选择夜间取还车，可能还会产生夜间服务费。
		    </p>
				<p style="color:rgb(51,51,51);font-size:15px;margin:15px 0;"><span style="font-weight:bold;color:rgb(51,51,51);font-size:15px">Q</span>&nbsp;&nbsp;&nbsp;是否提供发票？</p>
				<p style="font-size:15px;color:rgb(102,102,102);text-indent:2em;">发票开具方式有两种：1.订单提交后您可以在订单详情页申请开具发票；2.取车时由门店工作人员为您开具发票；具体方式以订单填写页面重要信息披露为准。（携程开票最高限额为您在携程支付订单总额，不含礼品卡支付金额）</p>

    
  
    
    
    
            </div>
        </div>
        <script type="text/javascript">
            function change(li){
                var e = document.getElementById("u1");
                var list = e.getElementsByTagName("li");
                for(var i = 0;i<list.length;i++){
                    list[i].style.border="0px";
                }
                var ele = document.getElementById(li);
                ele.style.border="2px solid rgb(65,95,140)";
            }
        </script>
    </div>
</body>
</html>