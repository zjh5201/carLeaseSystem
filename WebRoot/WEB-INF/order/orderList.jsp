<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>订单列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/orderItems.css"/>
    <script src="${pageContext.request.contextPath }/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/css/getXMLHttpRequest.js"></script>
</head>
<body>
    <div id="header">
        <h1>诚安汽车租赁</h1>
        <div class="header_a">
            <a href="${pageContext.request.contextPath }/home.jsp">回到首页</a>
        </div>
    </div>
    <div id="content">
        <table>
            <thead class="thead">
                <tr class="headtr">
                    <th>订单明细</th>
                    <th>用车人</th>
                    <th>用车日期</th>
                    <th>总金额</th>
                    <th>订单状态</th>
                    <th>操作</th>
                </tr>
            </thead>
        </table>
        <ul>
        
        <!-- 分页操作，设置初始和末尾索引，设置pageCount数 -->
        <c:set var="num" value="${pageMaxContent }"></c:set>
    	<c:set var="pageCount" value="${pages}"></c:set>
            <c:set var="length" value="${listLen }"></c:set>
            <!-- 设置页面List的开始索引和结束索引 -->
		    <c:set var="begin_index" value="${(current_page-1)*num }"></c:set>
		    <c:set var="end_index" value="${current_page*num-1 }"></c:set>
		    <c:if test="${current_page*num > length }">
		    	<c:set var="end_index" value="${length-1 }"></c:set>
    		</c:if>	
    		<!-- 设置页码条的开始索引和结束索引 -->
    		<c:set var="page_begin" value="${page_begin }"></c:set>
    		<c:set var="page_end" value="${page_end }"></c:set>	
        <c:forEach items="${orderList }" begin="${begin_index }" end="${end_index }" var="order">
            <li class="list">
                <h3>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;订单号：&nbsp;<span style="color:rgb(0,102,204);">${order.oid}</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <span>预订日期：&nbsp;<fmt:formatDate value="${order.orderItem.book}" pattern="yyyy-MM-dd" />&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <c:if test="${order.worker ne null }">
	                    <div style="display:inline-block;width:234px;border:1px solid rgb(190,211,238);height:17px;line-height:17px;">
		                    	<span style="font-size:13px;color:">&nbsp;工作人员：&nbsp;${order.worker.wname }&nbsp;&nbsp;Tel:${order.worker.wphone }</span>
	                    </div>
                    </c:if>
                    <a  class="deleteOrder" href="${pageContext.request.contextPath }/OrderServlet?method=deleteOrder&oid=${order.oid}&current_page=${current_page}&page_begin=${page_begin}&page_end=${page_end}" style="color:rgb(0,102,204);float:right;margin-right:500px;">删除订单</a>
                </h3>
                <table>
                    <tbody style="font-size:15px;">
                        <tr>
                            <td>${order.car.cc.city}-${order.car.cc.ccname }</td>
                            <td>${order.orderItem.dname }</td>
                            <td><fmt:formatDate value="${order.rentTime}" pattern="yyyy-MM-dd HH:00" /></td>
                            <td style="color:rgb(229, 103, 0);font-weight:bold;">¥${order.rentmoney }</td>
                            <td><c:choose>
                            	<c:when test="${order.orderstate eq 1 }">未支付</c:when>
                            	<c:when test="${order.orderstate eq 2 }">已付款</c:when>
                            	<c:when test="${order.orderstate eq 3 }">已完成</c:when>
                            </c:choose></td>
                            <td>
                            	<c:choose>
                            		<c:when test="${order.orderstate eq 1 }"><a href="${pageContext.request.contextPath }/OrderServlet?method=goPay&oid=${order.oid}">去付款</a></c:when>
                            	</c:choose>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </li>
         </c:forEach>
        </ul>
    </div>
    <div id="footer">
    	<div class="page">
    		<span style="font-size:15px;">总页数:${pageCount }&nbsp;&nbsp;</span>
	    	<span>页码:</span>
		    <c:forEach begin="${page_begin }" end="${page_end}" var="i">
		    	<c:choose>
		    		<c:when test="${i ne current_page }">
		    			<a href="${pageContext.request.contextPath }/OrderServlet?method=setPage&page=${i }&page_begin=${page_begin}&page_end=${page_end}">${i }</a>
		    		</c:when>
		    		<c:otherwise>${i }</c:otherwise>
		    	</c:choose>
		    </c:forEach>
		   	<c:if test="${current_page > 1 }">
		   		<a href="${pageContext.request.contextPath }/OrderServlet?method=setPage&page=${current_page-1 }&page_begin=${page_begin}&page_end=${page_end}">上一页</a>/
		   	</c:if>
		    <c:if test="${current_page < pageCount }">
		    	<a href="${pageContext.request.contextPath }/OrderServlet?method=setPage&page=${current_page+1 }&page_begin=${page_begin}&page_end=${page_end}">下一页</a>
		    </c:if>
	    </div>
    </div>
</body>
</html>