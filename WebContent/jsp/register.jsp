<%@page import="com.itheima.store.utils.UUIDUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
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
	$(function() {
		$("#username").blur(function() {
			$("#signal").empty();
           $.post("${pageContext.request.contextPath}/UserServlet",{"name":this.value,"method":"checkUserName"},function(data){
        	   if(data==0){
        		   $("#signal").prepend("<font Style='color:green' face='楷体'>用户名可用</font>");
        		      $("input[name='submit']").prop({disabled:false});
        	   }
        	   if(data==1){
            		   $("#signal").prepend("<font Style='color:red' face='楷体'>用户名不可用</font>");
            		   $("input[name='submit']").prop({disabled:true});
            	   
        	   }
           })
		});
		$("#checkcode").click(function(){
			this.src="${pageContext.request.contextPath}/UserServlet?method=testcode&timer="+new Date().getTime();
		});	
		$("#inputPassword3").blur(function(){
			$("#mpassword").empty();
			var password=this.value;
			if(password.length>20||password.length<6){
				$("#mpassowrd").html("密码要由6到20位的字符或数字组成");
			}else if (new RegExp("\\d+").test(password)&&new RegExp("\\w+").test(password)){
				
				$("#mpassowrd").html("<img src='${pageContext.request.contextPath}/image/1.jpg' style='width: 20px; height: 20px'/>");
			}else{
			    $("#mpassowrd").html("密码必由字符和数字组成");
			}	
		});
		$("#confirmpwd").blur(function(){
			$("rpassword").empty();
			if($("#inputPassword3").val()!=$("#confirmpwd").val()){
				$("#rpassword").html("两次密码必须一致");
			}else{
				$("#rpassword").html("两次密码一致");
			}	
		})
		$("#inputEmail3").blur(function(){
			$("#emsg").empty();
		    if(!new RegExp("^[\\d\\w]*@store.com$").test(this.value)){
		    	$("#emasg").html("邮箱的格式有误");
		    }else{
		    	$("#emasg").html("邮箱的格式正确");
		    }
		})
		$("input[name='telephone']").blur(function(){
		
			if(new RegExp("^\\d{11}$").test(this.value)){
				$("#etelephone").empty();
			}else{
				$("#etelephone").html("错误的电话号码格式");
			}
		})
		$("input[name='birthday']").blur(function(){
			if(!new RepExp("^\\d{4}[01]\\d[0123]\\d$").test(this.value)){
				$("#birth").html("日期格式不对");
			}else{
				$("#birth").empty();
			}
			
		})
		if(!navigator.cookieEnabled){
			$("input").prop({disabled:true});
			$("input[name='submit']").prop({disabled:true});
			alert("cookie未开启请开启cookie")
		}
		
	})
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
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
</style>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">

			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form class="form-horizontal" style="margin-top: 5px;"
					action="${pageContext.request.contextPath}/UserServlet"
					method="post">
					<div class="form-group">
					<input type="hidden" name="method" value="regist">
					  <% 
					  String token=UUIDUtils.getUuid();
					  session.setAttribute("token", token);
					  %>
					  	<input type="hidden" name="token" value="<%=token%>">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"
								placeholder="请输入用户名" name="username">
						</div>
						<span id="signal"></span>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="inputPassword3"
								placeholder="请输入密码" name="password">
						</div>
						<span id="mpassowrd">${epassword}</span>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd"
								placeholder="请输入确认密码" name="repassword">
						</div>
						<span id="rpassword"></span>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="inputEmail3"
								placeholder="xxx@store.com" name="email">
						</div>
									<span id="emasg">${emsg }</span>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption"
								placeholder="请输入姓名" name="name">
						</div>
						<span>${esex}</span>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
							 id="inlineRadio1" value="男" name="sex" checked="checked">
								男
							</label> <label class="radio-inline"> <input type="radio"
								id="inlineRadio2" value="女" name="sex">
								女
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday">
						</div>
						<span id="birth"></span>
					</div>
					<div class="form-group">
						<label for="telephone" class="col-sm-2 control-label">电话号码</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="telephone">
						</div>
									<span id="etelephone">${etelephone }</span>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="testcode">

						</div>
						<div class="col-sm-2">
							<img
								src="${pageContext.request.contextPath}/UserServlet?method=testcode&timer="+new Date().getTime() id="checkcode"/>
								<span>${testmsg}</span>
						</div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								border="0"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<%@ include file="floor.jsp"%>

</body>
</html>




