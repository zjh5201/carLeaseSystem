<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Order</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/order.css"/>
    <script type="text/javascript">
       
        window.onload = function(){
           console.log("111");
            window.onscroll = function(){
                var gopay = document.getElementsByName("goPay")[0];
                var money = document.getElementsByName("money")[0];
                var t = document.documentElement.scrollTop;
                if(t<=480){
                    gopay.id = "goPay1";
                    money.id = "m1";
                }else if(t>480&&t<720){
                    gopay.id = "goPay2";
                    money.id = "m1";
                }else{
                    gopay.id = "goPay2";
                    money.id = "m2";
                }
            }
        	goPay = function(){	//正确的调用方法。
        		var uname = document.getElementById("uname").value;
        		var uid = document.getElementById("uid").value;
        		var uphone = document.getElementById("uphone").value;
        		if(uname==null||uid==null||uphone==null||uname==""||uid==""||uphone==""){
        			alert("请先完善驾驶员信息！");
        			return;
        		}
        		if(!uid.match(/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/)){
        			alert("身份证信息不正确");
        			return;
        		}
        		if(!uphone.match(/^1(3|4|5|7|8)\d{9}$/)){
        			alert("手机号码有误，请重填");
        			return;
        		}
        		document.item_form.submit();	
        	}
        }
    </script>
</head>
<body>
    <div id="header">
        <h1>诚安汽车租赁</h1>
        <div class="header_a">
        </div>
    </div>
    <div id="content">
        <div class="left">
            <div class="user">
                <div class="top">
                    <div class="s1">
                        &nbsp;&nbsp;&nbsp;<span class="span1">驾驶员</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;<span class="span2">取车时需出示驾驶员本人身份证、本人驾驶证，本人信用卡，且证件有效期不小于1个月</span>
                    </div>
                </div>
                <form method="post" name="item_form" action="${pageContext.request.contextPath }/OrderServlet?method=setOrderItem">
                <div class="s2">
                    <div>
                    		<input type="hidden" name="itemId" value="${order.orderItem.itemId }"/>
                    		<input type="hidden" name="oid" value="${order.oid }"/>
                            <p>姓名</p>
                            <input type="text" id="uname" name="dname" placeholder="请输入姓名" value="${sessionScope.user.username}"/>
                    </div>
                    <div>
                            <p>身份证号</p>
                            <input type="text" id="uid" name="dIdcard"  placeholder="请输入证件号码" value="${sessionScope.user.uid}"/>
                    </div>
                    <div>
                            <p>联系方式</p>
                            <input type="text" id="uphone" name="dphone" placeholder="请输入联系方式" value="${sessionScope.user.phone }"/>
                    </div>
                </div>
                </form>
            </div>
            <div class="rentInfo">
                    <div class="top">
                            <div class="s1">
                                &nbsp;&nbsp;&nbsp;<span class="span1">租车保障</span>
                            </div>
                            <div class="fuwu">
                                <div class="f1">
                                    <img src="${pageContext.request.contextPath }/images/base.png"/>
                                </div>
                                <div class="f2">
                                    <p class="p1">基本保障服务</p>
                                    <p class="p2">租赁过程中的车辆损失，您只需承担3000元以内的维修费用。</p>
                                </div>
                                <div class="f3">
                                        ￥100
                                </div>
                            </div>
                            <div class="fuwu">
                                    <div class="f1">
                                        <img src="${pageContext.request.contextPath }/images/renshen.png"/>
                                    </div>
                                    <div class="f2">
                                        <p class="p1">人身及财物险</p>
                                        <p class="p2">保障全车人员在驾乘过程中发生的意外及随车行李损失。</p>
                                    </div>
                                    <div class="f3" style="font-size:18px;margin-left: 160px;">
                                            免费提供
                                    </div>                                    
                                </div>
                    </div>
                   

                    </div>
                    <div class="pay">
                        <div class="top">
                            <div class="s1">
                                &nbsp;&nbsp;&nbsp;<span class="span1">支付方式</span>
                            </div>
                            <div class="allMoney">
                                <span style="line-height: 50px;font-size: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;在线付全额 <span style="color:rgb(255, 102, 0);font-size: 25px;">&nbsp;&nbsp;¥${order.rentmoney}</span></span>

                            </div>
                        <div name="goPay" class="font">
                            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我已阅读并同意 <span style="color:rgb(37,119,227);">预订条款，预订须知</span></span>
                            <button class="btn" onclick="goPay()">下一步，去支付</button>

                        </div>
                    </div>    
            </div>
            <div class="info">
                <div class="top">
                        <div class="s1">
                            &nbsp;&nbsp;&nbsp;<span class="span1">重要信息</span>
                        </div>
                </div>
                <div class="i1 i2">
                    <p class="p1">取车证件（身份证+驾驶证+本人信用卡）</p>
                    <p class="p2">身份证件有效期需1个月以上，驾驶证有效期2个月以上，信用卡有效需1个月以上</p>
                </div>
                <div class="i1">
                    <p class="p1">押金预授权</p>
                    <p class="p2">取车时冻结信用卡${order.cash }元作为租车预授权,还车时返还；还车时冻结3000元做为违章预授权，无违章后返还</p>
                </div>
                <div class="i1">
                    <p class="p1">发票</p>
                    <p class="p2">可在订单详情页开具</p>
                </div>
                <div class="i1">
                    <p class="p1">里程限制</p>
                    <p class="p2">不限里程</p>
                </div>
            </div>
        </div>
        <div class="right">
            <div class="carInfo">
                <img src="${order.car.carImage }" style="width:300px;height:170px;display:block;margin:0px auto;"/>
                <p style="font-size:20px;font-weight:bold;margin-top:30px;margin-left:40px;">${order.car.brand }${order.car.model }</p>
                <c:if test="${order.car.gear eq 2 }">
                	<p style="margin:10px;margin-left:50px;font-size:14px;color:rgb(102,102,102);">自动挡&nbsp;&nbsp;|&nbsp;&nbsp;满油取还</p>
                </c:if>
                <c:if test="${order.car.gear eq 1 }">
                	<p>手动挡&nbsp;&nbsp;|&nbsp;&nbsp;满油取还</p>
                </c:if>
                <p style="margin-left:20px;font-size:15px;margin-top:50px;"><span style="background-color:rgb(83,89,104);border-radius: 0.5;color:white;font-size:15px;">租期</span>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${order.rentTime}" pattern="yy-MM-dd HH:mm"/>--<fmt:formatDate value="${order.returnTime}" pattern="yy-MM-dd HH:mm"/></p>
                <p style="margin-left:13px;font-size:15px;margin-top:50px;"><span style="background-color:rgb(83,89,104);border-radius: 0.5;color:white;font-size:15px;">取车地</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${order.rentCity}${order.rentArea }</p>
                <p style="margin-left:13px;font-size:15px;margin-top:10px;"><span style="background-color:rgb(83,89,104);border-radius: 0.5;color:white;font-size:15px;">还车地</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${order.returnCity}${order.returnArea }</p>
                <p>${order.car.cc.phone }</p>
            </div>
            <div name="money">
                    <div class="top">
                            <div class="s1">
                                &nbsp;&nbsp;&nbsp;<span class="span1">费用明细</span>
                            </div>
                    </div>  
                    <p style="margin-left: 32px;margin-top:20px;">租车费：${order.car.price }&nbsp;x&nbsp;${order.days }</p>
                    <p style="margin-left: 32px;margin-top:10px;"> 基本保障服务：100元</p>
                    <div class="totalmoney">
                        <p style="line-height: 140px;margin-left: 50px;">总价:&nbsp;${order.rentmoney }元</p>
                    </div>
                    <div class="moneyExplain">
                        <p style="margin-bottom:12px;margin-top:">费用说明</p>
                    <p style="font-size:14px;margin-bottom:9px;color:rgb(102, 102, 102);">1. ,基本保障服务：指租车公司向您提供的车辆均购买有交强险及商业保险，保障您用车安全的一种服务，按天收费。</p>
                    <p style="font-size:14px;color:rgb(102, 102, 102);">2. 车行手续费：门店收取手续费用用于车辆清洁、车辆保养、单据制作、人员服务等。</p>
                    </div>              
            </div>
        </div>
    </div>
</body>
</html>