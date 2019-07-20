<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="${pageContext.request.contextPath}/UI/lib/html5shiv.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/UI/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/UI/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/UI/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/UI/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/UI/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/UI/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="${pageContext.request.contextPath}/UI/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>用户查看</title>
</head>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
	<img class="avatar size-XL l" src="${pageContext.request.contextPath}/UI/static/h-ui/images/ucnter/avatar-default.jpg">
	<dl style="margin-left:80px; color:#fff">
		<c:choose>
			<c:when test="${model eq 1 }">
				<dt>
					<span class="f-18">${user.username }</span>
					<span class="pl-10 f-12"></span>
				</dt>			
			</c:when>
			<c:when test="${model eq 2 }">
				<dt>
					<span class="f-18">${orderItem.dname }</span>
					<span class="pl-10 f-12"></span>
				</dt>	
			</c:when>
		</c:choose>
		<dd class="pt-10 f-12" style="margin-left:0">这家伙很懒，什么也没留下！</dd>
	</dl>
</div>
<div class="pd-20">
	<table class="table">
		<tbody>
			<c:choose>
				<c:when test="${model eq 1 }">
					<tr>
						<th class="text-r">电话：</th>
						<td>${user.phone }</td>
					</tr>
					<tr>
						<th class="text-r">身份证：</th>
						<td>${user.uid }</td>
					</tr>
					<tr>
						<th class="text-r">邮箱：</th>
						<td>${user.email }</td>
					</tr>
					<tr>
						<th class="text-r">信誉度：</th>
						<td>${user.credit }</td>
					</tr>
				</c:when>
				<c:when test="${model eq 2 }">
					<tr>
						<th class="text-r">电话：</th>
						<td>${orderItem.dphone }</td>
					</tr>
					<tr>
						<th class="text-r">身份证：</th>
						<td>${orderItem.dIdcard }</td>
					</tr>
					<tr>
						<th class="text-r">预订日期：</th>
						<td><fmt:formatDate value="${orderItem.book }" pattern="yyyy-MM-dd HH:00"></fmt:formatDate></td>
					</tr>
				</c:when>
			</c:choose>
			
		</tbody>
	</table>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath}/UI/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/UI/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/UI/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/UI/static/h-ui.admin/js/H-ui.admin.js"></script> 
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
</body>
</html>