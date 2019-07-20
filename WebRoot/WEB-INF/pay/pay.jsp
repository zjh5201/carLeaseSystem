<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>诚安租车平台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/pay.css"/>
    <script>
        window.onload = function(){
            var ul = document.getElementById("type");
            var wx = document.getElementById("wx");
            wx.style.borderBottom = "3px solid rgb(64,100,147)";
            var ali = document.getElementById("ali");
            var div_wx = document.getElementById("wechatPay");
            var div_ali = document.getElementById("alipay");
            var wx_btn = document.getElementById("wx_btn");
            var ali_btn = document.getElementById("ali_btn");
            var wx_ewm = document.getElementById("wx_ewm");
            var wx_close = document.getElementById("wx_close");
            var ali_close = document.getElementById("ali_close");
            var mark = document.getElementById("mark");
            var payed = document.getElementsByClassName("payed");
            console.log(payed);
            div_ali.style.display = "none";
            wx.onclick = function(){
                wx.style.borderBottom = "3px solid rgb(64,100,147)";
                ali.style.borderBottom = "0";
                div_wx.style.display = "block";
                div_ali.style.display = "none";
            }
            ali.onclick = function(){
                ali.style.borderBottom = "3px solid rgb(64,100,147)";
                wx.style.borderBottom = "0";
                div_wx.style.display = "none";
                div_ali.style.display = "block";
            }
            wx_btn.onclick = function(){
                wx_ewm.style.display = "block"; 
                mark.style.display = "block";
                
                //设置在支付页面定时出现“支付完成的按钮"
                window.setTimeout(function(){
            		payed[0].style.display = "block";
            	},4000);
            }
            wx_close.onclick = function(){
                wx_ewm.style.display = "none"; 
                mark.style.display = "none";
            }
            ali_btn.onclick = function(){
                ali_ewm.style.display = "block"; 
                mark.style.display = "block";
                 //设置在支付页面定时出现“支付完成的按钮"
                window.setTimeout(function(){
            		payed[1].style.display = "block";
            	},4000);
            }
            ali_close.onclick = function(){
                ali_ewm.style.display = "none"; 
                mark.style.display = "none";
            }
			payed[0].onclick = function(){
				document.form_payed.submit();
			}
			payed[1].onclick = function(){
				document.form_payed.submit();
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
        <div class="money">
                <p style="font-size: 15px;margin:30px 30px;color:rgb(14, 38, 68);">订单金额&nbsp;<span style="color:rgb(255,140,24);">¥</span><span style="color:rgb(255, 140, 24);font-size: 28px;font-family: '微软雅黑';">${order.rentmoney }.00</span></p>
        </div>
        <div id="payType">
                <ul id="type">
                    <li id="wx" value="wx">微信支付</li>
                    <li id="ali" value="ali">支付宝支付</li>
                </ul>
                <div id="wechatPay">
                    <img src="${pageContext.request.contextPath }/images/wechatPay.png" alt="">
                    <p>点击“去支付”后，请打开手机微信的“扫一扫”，扫描二维码支付</p>
                    <button id="wx_btn">¥${order.rentmoney }.00, 去支付</button>
                </div>
                <div id="alipay">
                    <img src="${pageContext.request.contextPath }/images/alipay.png" alt="">
                    <p>点击“去支付”后，请打开手机支付宝的“扫一扫”，扫描二维码支付</p>
                    <button id="ali_btn">¥${order.rentmoney }.00, 去支付</button>        
                </div>
            </div>       
        
    </div>
    <div class="mark" id="mark"></div>
        <div id="wx_ewm" class="ewm">
            <div>
                <span style="font-size:20px;color:rgb(14, 38, 68);font-weight: bold;">微信支付</span>
                <span class="s1" style="font-size:30px;cursor:pointer;" id="wx_close">×&nbsp;</span>
            </div>
            <div class="d1">
                <p>支付金额：¥${order.rentmoney }.00</p>
                <img src="${pageContext.request.contextPath }/images/weixinpay.jpg" alt="" width="150px" style="margin-top:20px;">
                
                <div class="d2"><img src="${pageContext.request.contextPath }/images/sys.png" alt="" width="28px"><p>打开手机微信扫一扫二维码</p></div>
            	<button class="payed" style="display:none;">已支付，请点击</button>
            </div>
        </div>
    
    	<form name="form_payed" action="${pageContext.request.contextPath }/OrderServlet?method=setOrderState" method="post">
    		<input type="hidden" name="oid" value="${order.oid }"/>
    	</form>
    
        <div id="ali_ewm" class="ewm">
                <div>
                    <span style="font-size:20px;color:rgb(14, 38, 68);font-weight: bold;">支付宝支付</span>
                    <span class="s1" style="font-size:30px;cursor:pointer;" id="ali_close">×&nbsp;</span>
                </div>
                <div class="d1">
                    <p>支付金额：¥${order.rentmoney }.00</p>
                    <img src="${pageContext.request.contextPath }/images/alipay.jpg" alt="" width="150px" style="margin-top:20px;">
                    <div class="d2"><img src="${pageContext.request.contextPath }/images/sys.png" alt="" width="28px"><p>打开手机支付宝扫描二维码</p></div>
					<button class="payed" style="display:none;">已支付，请点击</button>
                </div>
        </div>
</body>
</html>