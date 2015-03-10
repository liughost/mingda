<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication var="username" property="principal.username" />
</sec:authorize>
<!-- 主体内容区 STA -->
<div class="maink">
	<div class="main">
		<div class="use">
			<div class="use-l">
				<div class="use-lhead">
					<img src="${context }/resources/rabbit/images/head166-166.jpg" alt="" />
				</div>
				<div class="use-lname">${username }</div>
				<ul class="use-lul">
					<li><a href="${context }/member/info">我的信息</a></li>
					<li><a href="${context }/member/invite/send">我的邀请</a></li>
					<li class="active"><a href="#">我的订单</a></li>
					<!-- active为当前状态 -->
					<li><a href="#">我的旅伴</a></li>
					<li><a class="modalLink" href="#modal4">修改密码</a></li>
				</ul>
			</div>
			<div class="use-r">
				<div class="use-rt">我的订单</div>
				<div class="use-rb">
					<div class="use-order">
						<table id="mytable" width="100%" cellpadding="0" cellspacing="0"
							border="0" align="center">
							<thead>
								<tr>
									<th width="26" align="center"><input type="checkbox" style="display:none;"/></th>
									<th width="100">订单号</th>
									<th width="90">出发日期</th>
									<th width="100">人数（儿童）</th>
									<th width="125">单价</th>
									<th>优惠价</th>
									<th>总价</th>
									<th>状态</th>
									<th>下单时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="items" items="${orderList}" varStatus="i">
									<c:choose>
										<c:when test="${i.index%2==0}">
											<s:url var="trName" value="trbg1" />
										</c:when>
										<c:otherwise>
											<s:url var="trName" value="trbg2" />
										</c:otherwise>
									</c:choose>
									<tr class="${trName}">
										<td align="center" valign="top"><input type="checkbox" style="display:none;"/></td>
										<td colspan="8" align="left"><a
											href="${context }/custom/detail_all?id=${items.productId}"">产品名称：${items.productName }</a></td>
									</tr>

									<tr class="${trName}">
										<td></td>
										<td>${items.orderId }<br /> <!-- <a href="#">详情</a>  --></td>
										<td>${items.arriveDate }</td>
										<td>${items.adultCount }(${items.childrenCount })</td>
										<td>成人价：${items.productPrice }<br />儿童价：${items.childrenPrice }
										</td>
										<td>￥${items.offPrice }</td>
										<td>￥${items.payMoney }</td>
										<td><c:choose>
												<c:when test="${items.orderState==1 }">有效</c:when>
												<c:otherwise>无效</c:otherwise>
											</c:choose></td>
										<td>${items.orderDate }</td>
									</tr>

								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 主体内容区 End -->
