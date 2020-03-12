<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>


	<div class="row" style="width: 1210px; margin: 0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
			</ol>
		</div>

		<!-- 遍历所有商品信息 -->
		<c:forEach items="${pageBean.list}" var="product">
			<div class="col-md-2" style="height: 250px">
				<a
					href="${pageContext.request.contextPath}/product?method=productInfo&pid=${product.pid}&cid=${cid}&currentPage=${pageBean.currentPage}">
					<img src="${product.pimage}" width="170" height="170"
					style="display: inline-block;">
				</a>
				<p>
					<a
						href="${pageContext.request.contextPath}/product?method=productInfo&pid=${product.pid}&cid=${cid}&currentPage=${pageBean.currentPage}"
						style='color: green'>${product.pname}</a>
				</p>
				<p>
					<font color="#FF0000">商城价：&yen;${product.shop_price}</font>
				</p>
			</div>
		</c:forEach>

	</div>

	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination"
			style="text-align: center; margin-top: 10px; margin-left: 70px">
			<c:choose>
				<%--如果是第一页,则前一页按钮无法使用--%>
				<c:when test="${pageBean.currentPage==1}">
					<li class="disabled"><a href="javascript:void(0);"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:when>
				<%-- 如果不是第一页,则前一页按钮可以使用 --%>
				<c:otherwise>
					<li><a
						href="${pageContext.request.contextPath}/product?method=productListByCid&cid=${cid}&currentPage=${pageBean.currentPage-1}"
						aria-label="Previous"><span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:otherwise>
			</c:choose>

			<!-- 中间的页数 -->
			<c:forEach begin="1" end="${pageBean.totalPage}" step="1"
				varStatus="status">

				<c:choose>
					<c:when test="${pageBean.currentPage==status.index}">
						<li class="active"><a href="javascript:void(0);">${status.index}</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.request.contextPath}/product?method=productListByCid&cid=${cid}&currentPage=${status.index}">${status.index}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<%--如果是最后一页,则无法继续翻页 --%>
				<c:when test="${pageBean.currentPage==pageBean.totalPage}">
					<li class="disable"><a href="javascript:void(0);"
						aria-label="Next"><span aria-hidden="true">&raquo;</span> </a></li>
				</c:when>
				<%--如果不是最后一页,则可以继续翻页 --%>
				<c:otherwise>
					<li><a
						href="${pageContext.request.contextPath}/product?method=productListByCid&cid=${cid}&currentPage=${pageBean.currentPage+1}"
						aria-label="Next"><span aria-hidden="true">&raquo;</span> </a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	<!-- 分页结束 -->

	<!--商品浏览记录-->
	<div
		style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">

		<h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
		<div style="width: 50%; float: right; text-align: right;">
			<a href="">more</a>
		</div>
		<div style="clear: both;"></div>

		<div style="overflow: hidden;">

			<ul style="list-style: none;">
				<c:forEach items="${historyList}" var="history">
					<li
						style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;">
						<a
						href="${pageContext.request.contextPath}/product?method=productInfo&pid=${history.pid}&cid=${history.cid}&currentPage=${pageBean.currentPage}"><img
							src="${history.pimage}" width="130px" height="130px" /></a>
					</li>
				</c:forEach>
			</ul>

		</div>
	</div>


	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>