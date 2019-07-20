<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>Insert title here</title>
	<style type="text/css">
		*{
		    margin:0px;
		    padding:0;
		}
		.bg{
		    position: absolute;
		    /*相对于最近一个实现了position的元素进行定位，也就是body*/
		    width:100%;
		    height:100%;
		    background-image: url(images/loginBg1.jpg);
		    /* background-color: aqua; */
		    z-index: -1;
		}
		
		.login{
		    width:35%;
		    height:75%;
		    background-color:white;
		    margin-left: auto;
		    margin-right: auto;
		    margin-top:120px;
		    /* opacity: 0.7; */
		    border:1px solid grey;
		    border-radius:2%;
		}
		.box{
		    width:205px;
		    height:380px;
		    margin:30px 105px;
		    /* background-color: aquamarine; */
		}
		.box p{
		    
		    height:30px;
		    font-size: 20px;
		    font-family: "微软雅黑";
		    color:black;
		    line-height: 30px;
		    /* font-weight: bold; */
		    border-bottom:1px solid teal;
		}
		.box input{
		    width:200px;
		    height:30px;
		    border-radius:4px;
		    outline: none;
		    border:1px solid gray;
		}
		.i1{
		    margin-top:30px;
		}
		.p1{
		    display: inline-block;
		    margin-left: 34px;
		    color:black;
		    font-size: 13px;
		}
		.box button{
		    color:white;
		    font-size:16px;
		    font-weight: bold;
		    width:100px;
		    height:40px;
		    border:0px;
		    background-color:rgb(116, 205, 255);
		    border-radius: 2px;
		    margin-left: 50px;
		    margin-top: 10px;
		}
		span{
			font-size:12px;
			color:red;
		}
			
	</style>
</head>
<script type="text/javascript">
	/*
		使用js实现表单验证
		功能需求：
			1.姓名和密码、身份证输入格式
			2.手机号和邮箱的正则判断
	*/
	window.onload = function(){
		var username = document.getElementsByName("username")[0];
		var password = document.getElementsByName("password")[0];
		var uid = document.getElementsByName("uid")[0];
		var email = document.getElementsByName("email")[0];
		var phone = document.getElementsByName("phone")[0];
		isFocus(username,"请输入您的姓名！要与身份证上一致！","username");
		isBlur(username,"username");
		isFocus(password,"请输入3~20位的密码！","password");
		isBlur(password,"password",/[0-9,A-z]{3,20}$/g,"密码格式不正确");
		isFocus(uid,"请输入您的身份证号码！","uid");
		isBlur(uid,"uid",/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/g,"身份证格式不正确");
		isFocus(phone,"请输入您的手机号码！","phone");
		isBlur(phone,"phone",/^1[3-9][0-9]{9}$/,"手机号码格式不正确");
		isFocus(email,"请输入您的邮箱账号！","email");
		isBlur(email,"email",/^[A-z 0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,"邮箱格式不正确");
		function isFocus(element,msg,id){
			element.onfocus = function(){
				var span = document.getElementById(id);
				var br = document.getElementById("br_"+id);
				br.style.display = "none";
				span.innerHTML = msg;
			}
		}
		function isBlur(element,id,regexp,msg){
			element.onblur = function(){
				var span = document.getElementById(id);
				var br = document.getElementById("br_"+id);
				if(id.toString() == "username"){
					if(checkName(element.value,span)==1){
						br.style.display = "none";
					}else{
						span.innerHTML = "";
						br.style.display = "inline";
					}	
				}else{
					if(checkNumber(element.value,regexp,msg,span)==1){
						br.style.display = "none";
					}else{
						span.innerHTML = "";
						br.style.display = "inline";
					}
				}
			}
		}
		function checkName(name,span){
			if(name.replace(/ /g, "").length == 0 || name.replace(/ /g, "").length == 1 ||name == null){
				span.innerHTML = "请您输入正确格式的姓名";
				return 1;
			}
			if(name.match(/[0-9]/g)){
				span.innerHTML = "姓名中不可包含数字符号！";
				return 1;
			}
			return 0;
		}
		function checkNumber(value,regexp,msg,span){
			if(!value.match(regexp)){
				span.innerHTML = msg;
				return 1;
			}
			return 0;
		}
	}
</script>
<body>
    <div class="bg">
        <div class="login">
            <div class="box">
                <p>注册</p>
                <form action="<c:url value='/UserServlet1?method=regist'/>" method="post" class="loginform">
                  	<span>${requestScope.msg }</span>
                    <input type="text" name="username"  placeholder=" 请输入姓名" class="i1"/>
                    <span id="username"></span><br/><br id="br_username"/>
                    <input type="password" name="password"  placeholder=" 请输入密码"/>
                    <span id="password"></span><br/><br id="br_password"/>
                   	<input type="text" name="uid"  placeholder=" 请输入身份证号"/>
                    <span id="uid"></span><br/><br  id="br_uid"/>
                 	<input type="text" name="phone"  placeholder=" 请输入手机号"/>
                    <span id="phone"></span><br/><br id="br_phone"/>
                    <input type="text" name="email"  placeholder=" 请输入邮箱"/>
                    <span id="email"></span><br/><br id="br_email"/>
                    <button type="submit">同意并注册</button>
                </form><br/>
                <span class="p1">我已有账户，直接<a href="login.jsp">登录</a></span>
            </div>
        </div>
    </div>
</body>
</html>