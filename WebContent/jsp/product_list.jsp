<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>WEB01</title>
<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
  $(function(){
	  $(".removehistory").click(function(){ 
	     var data=this.name;
    	$.post("${pageContext.request.contextPath}/ProductServlet",{"pid":data,"method":"removehistory"}); 
    	$(this).fadeOut("slow");
	  })
  })
    
</script>
</head>
	<body>
		
	<%@include file="/jsp/header.jsp" %>


		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="${pageContext.request.contextPath}/IndexServlet?method=index" >首页</a></li>
				</ol>
			</div>
        <c:forEach var="i" items="${pagebean.plist}">
			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/ProductServlet?method=findbypid&pid=${i.pid}">
					<img src="${pageContext.request.contextPath}/${i.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath}/ProductServlet?method=findbypid&pid=${i.pid}" style='color:green'>${fn:substring(i.pname,0,7) }</a></p>
				<p><font color="#FF0000">商城价：&yen;${i.shop_price}</font></p>
			</div>
			</c:forEach>
		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
			<c:if test="${pagebean.currentpage==1}">
		
			<li class="disabled"><span aria-hidden="Next">&laquo;</span></li>
			</c:if>
			<c:if test="${pagebean.currentpage>1 }">
				<li class="active"><a href="${pageContext.request.contextPath}/ProductServlet?method=findbycid&cid=${pagebean.cid}&firtpid=${pagebean.firtpid }&lastpid=${pagebean.lastpid}&currentpage=${pagebean.currentpage-1 }&srcpage=${pagebean.currentpage }" aria-label="Next"><span >&laquo;</span></a></li>
			</c:if>
			
				<c:forEach var="i" begin="1" end="${pagebean.totalpage}" varStatus="status"  >
				<c:if test="${pagebean.currentpage==status.count}">
				<li class="disabled"><span aria-hidden="Next">${status.count}</span></li>
				</c:if>
				<c:if test="${pagebean.currentpage!=status.count}">
				<li class="active"><a href="${pageContext.request.contextPath}/ProductServlet?method=findbycid&cid=${pagebean.cid}&firtpid=${pagebean.firtpid }&lastpid=${pagebean.lastpid}&currentpage=${status.count}&srcpage=${pagebean.currentpage}" aria-label="Next">${status.count}</a></li>
				</c:if>
				</c:forEach>
					<c:if test="${pagebean.currentpage<pagebean.totalpage}">
					<li>
					<a href="${pageContext.request.contextPath}/ProductServlet?method=findbycid&cid=${pagebean.cid}&firtpid=${pagebean.firtpid }&lastpid=${pagebean.lastpid}&currentpage=${pagebean.currentpage+1 }&srcpage=${pagebean.currentpage }" aria-label="Next">
						<span aria-hidden="Next">&raquo;</span>
					</a>
				    </li>	
					</c:if>
						<c:if test="${pagebean.currentpage==pagebean.totalpage}">
					<li class="disabled">
						<span aria-hidden="Next">&raquo;</span>
				
				    </li>	
					</c:if>
				
			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
	<div
		style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">

		<h4 style="width: 50%; float: left; font: 14px/30px"微软雅黑 ";">浏览记录</h4>
		<div style="width: 50%; float: right; text-align: right;">
			<a href="${pageContext.request.contextPath}/">more</a>
		</div>
		<div style="clear: both;"></div>
		<div style="overflow: hidden;">
		<!-- 没有商品记录时 -->
		
		<c:if test="${cookie.history.value==null}">
		<strong>暂时还没有浏览记录</strong>
		</c:if>

		<c:if test="${cookie.history.value!=null}">
        <c:forEach var="i" items="${history}">
  
			<ul style="list-style: none;">
				<li
					style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;">
					<img
					src="${pageContext.request.contextPath}/${i.pimage}"
					width="130px" height="130px" class="removehistory" name="${i.pid}"/>
					</li>
			</ul>

       </c:forEach>
		</c:if>


		</div>
	</div>
	<%@include file="/jsp/floor.jsp" %>

	</body>

</html>