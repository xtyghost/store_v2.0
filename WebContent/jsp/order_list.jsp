<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
<!-- 头jsp -->
			<%@ include file="header.jsp" %>
		</nav>

		<div class="container">
			<div class="row">
				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
					<c:forEach var="i" items="${orders.list }">
						<tbody>
							<tr class="success">
								<th colspan="5">订单编号:${i.oid} 总金额:${i.total }元  &nbsp 
								<c:if test="${i.state==1}"><a href="${pageContext.request.contextPath}/OrderServlet?method=findbyoid&oid=${i.oid}">付款</a></c:if>
								<c:if test="${i.state==2}">未发货</c:if>
								<c:if test="${i.state==3}">运输中</c:if>
								<c:if test="${i.state==4}"><a href="${pageContext.request.contextPath}/OrderServlet?method=findbyoid&oid=${i.oid}">确认收货</a></c:if>
								
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach var="n" items="${i.map }">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${n.value.product.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank"> ${n.value.product.pname }</a>
								</td>
								<td width="20%">
									￥${n.value.product.pname }
								</td>
								<td width="10%">
									${n.value.count }
								</td>
								<td width="15%">
									<span class="subtotal">￥${n.value.subtotal }</span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						</c:forEach>
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
				<c:if test="${orders.currPage==1 }">
				<li class="disabled"><a href="${pageContext.request.contextPath}/#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${orders.currPage!=1 }">
							<li class="active"><a href="${pageContext.request.contextPath}/OrderServlet?method=findMyOrders&currPage=${orders.currPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
					<c:forEach var="k" begin="1" end="${orders.totalPage}">
					<c:if test="${orders.currPage==k }">
						<li class="disabled"><a href="${pageContext.request.contextPath}/#">${k}</a></li>
					</c:if>
					<c:if test="${orders.currPage!=k }">
						<li class="active"><a href="${pageContext.request.contextPath}/OrderServlet?method=findMyOrders&currPage=${k}">${k}</a></li>
					</c:if>
					</c:forEach>
						<c:if test="${orders.currPage<orders.totalPage }">
						<li class="active">
						<a href="${pageContext.request.contextPath}/OrderServlet?method=findMyOrders&currPage=${orders.currPage+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					   </li>
					</c:if>
					<c:if test="${orders.currPage>=orders.totalPage }">
					<li class="disabled">
						<span  aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</span>
					</li>
					
					</c:if>
					
				</ul>
			</div>
		</div>

		<%@ include file="floor.jsp" %>
</html>