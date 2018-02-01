<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script type="text/javascript">
var timer;
   $(function(){
	timer= window.setInterval("timechange()", 1000) ;  
   })
    function  timechange(){
	 var time= $("#time").text();
	 time++;
	 if(time==8){
		 /* 清除计时器 */
		 window.clearInterval(timer)
		$.post("${pageContext.request.contextPath}/OrderServlet",{"method":"callback"},function(data){
		   if(data==1){
			   $("#msg").html("订单付款成功") 
		   }else{
			  $("#msg").html("非常抱歉请1小时后在查询") 
		   }
		})
	 }
	 $("#time").html(time);
	 
   }

</script>
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
	/* position:relative;
	 float:left; */
	
}

font {
	color: #666;
	font-size: 22px;
	font-weight: normal;
	padding-right: 17px;
}
</style>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="container"
		style="width: 100%; height: 460px; ">
		<div class="row">
			<div class="col-md-7"><center id="msg"><span id="time">0</span>秒跳转到结果页面</center></div>

		</div>
	</div>
	<%@ include file="floor.jsp"%>
</body>
</html>