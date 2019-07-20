<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>成功消息提示页面</title>
    <style type="text/css">
    	body{
    		text-align:center;
    	}
    	div{
    		width:180px;
    		height:190px;
    		background-color:gray;
    		margin:auto;
    		margin-top:10%;
    		padding:auto;
    		border-radius:25px;
    	}
    	img{
    		margin-top:20px;
    		width:90px;
    		height:90px;
    	}
    	span{
			font-size: 16px;
			font-weight: bolder;
			color: white;
		}
    </style>
  </head>
  
  <body>
    	<div>
	    		<img src="${pageContext.request.contextPath }/UI/images/icon/success.png">
	    	<br><br>
	    	<span>
	    		操作成功<br>${message }
	    	</span>
    	</div>
  </body>
</html>
