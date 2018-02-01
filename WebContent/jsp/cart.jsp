<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<script type="text/javascript">
		$(function(){
			$("input[type='submit']").mousedown(function(){
				<c:if test="${empty exsitUser}">
				$(this).prop("disabled",true);
				var i=window.confirm("必须登录才能提交订单");
				if(i){
					location.href="${pageContext.request.contextPath}/jsp/login.jsp";
				}
				</c:if>
			})
			$("input[name='quantity']").blur(function(){
				var quantity=$(this).val();
			    var id=	$(this).prop("id")
			    location.href="${pageContext.request.contextPath}/CartServlet?method=changCart&count="+quantity+"&pid="+id;
			})
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
		<!--头部jsp  -->
		<%@include file="header.jsp"%>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
					<table class="table table-bordered">
						<tbody>
						
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							<c:if test="${fn:length(cart.map)==0}">
							<td colspan="6">
							亲您的订单空空的<br>
							赶紧去购物吧
							</td>
							</c:if>
							<c:if test="${fn:length(cart.map)!=0}">
							<c:forEach var="entry" items="${cart.map}">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="${entry.key}">
									<img src="${pageContext.request.contextPath}/${entry.value.product.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank"> ${entry.value.product.pname}</a>
								</td>
								<td width="20%">
									￥${entry.value.product.shop_price}
								</td>
								<td width="10%">
									<input type="text" name="quantity" value="${entry.value.count }" maxlength="4" size="10" id="${entry.key}">
								</td>
								<td width="15%">
									<span class="subtotal">￥${entry.value.count*entry.value.product.shop_price}</span>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/CartServlet?method=deleteCartitem&pid=${entry.key}" class="delete">删除</a>
								</td>
							</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:right;">
					<em style="color:#ff6600;">
				登录后确认是否享有优惠&nbsp;&nbsp;
			</em> 赠送积分: <em style="color:#ff6600;">${cart.totalcount*10 }</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${cart.totalcount*1}元</strong>
				</div>
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
					<a href="${pageContext.request.contextPath}/CartServlet?method=clearCart" id="clear" class="clear">清空购物车</a>
					<a href="${pageContext.request.contextPath}/OrderServlet?method=subCart">
						<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
					</a>
				</div>
			</div>

		</div>

        <!--尾部jsp  -->
		<%@include file="floor.jsp"%>
	</body>

</html>