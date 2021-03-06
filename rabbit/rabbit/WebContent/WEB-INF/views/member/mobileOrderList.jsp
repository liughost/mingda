<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.info_left {
	text-align: right;
	width: 120px;
}

.info_right {
	width: 220px;
}

.table td {
	border-top: 0px;
	border-bottom: 1px solid #dddddd;
}

.table th {
	border-top: 0px;
	border-bottom: 1px solid #dddddd;
}
input[type="checkbox"]{
margin:-2px 0 0 ;
}
</style>
<c:if test="${IsMobile }">
	<div style="height: 20px;"></div>
</c:if>
<div class="container" style="max-width: 960px; text-align: left;">
	<legend>订单信息</legend>
	<table class="table table-hover">
		<tr>
			<th style="width: 60px;"><input type="checkbox">订单号</th>
			<th>出发日期</th>
			<th>人数（儿童）</th>
			<th>单价</th>
			<th>优惠价</th>
			<th>总价</th>
			<th style="width: 40px;">状态</th>
			<th>下单时间</th>

		</tr>
		<c:forEach var="items" items="${orderList}">
			<tr rowspan="2">

				<td colspan="8" style="border: 0px;"><a
					href="${context }/custom/detail_all?id=${items.productId}">${items.productName }</a></td>
			</tr>
			<tr>
				<td style="width: 20px;"><input type="checkbox"
					name="sel_order" value="${items.id}">&nbsp;&nbsp;<a
					href="${context}/custom/order?productID=${items.productId }&gdate=${items.arriveDate }&orderId=${items.orderId }">${items.orderId }</a></td>

				<td>${items.arriveDate }</td>
				<td>${items.adultCount }(${items.childrenCount })</td>
				<td>成人：${items.productPrice }<br />儿童：${items.childrenPrice }
				</td>
				<td>${items.offPrice }</td>
				<td>${items.payMoney }</td>
				<td><c:if test="${items.orderState==1 }">有效</c:if> <c:if
						test="${items.orderState !=1 }">无效</c:if></td>
				<td>${items.orderDate }</td>

			</tr>
		</c:forEach>

	</table>
	<div class="pagination">
		<ul id="pageZone">

		</ul>
	</div>
</div>
<script>
showPage(${pageNo}, ${fn:length(orderList)});
function showPage(page, len) {
	var pageCount = 20;
	//debugger;
	console.log('page:' + page);
	var page_str = '';
	//上一页
	if (page > 0) {
		page_str += '<li><a href="${context}/member/order/list?page=' + (page - 1)
				+ '">上一页</a></li>';
	}

	//下一页
	if ( len >= pageCount) {
		page_str += '<li><a href="${context}/member/order/list?page=' + (page + 1)
				+ '">下一页</a></li>';
	}
	pageZone.innerHTML = page_str;
}
</script>
