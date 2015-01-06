<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${context }/resources/js/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<link href="${context }/resources/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<script src="${context }/resources/custom/js/bootstrap-carousel.js"></script>
<c:if test="${IsMobile==false }">
	<div style="height: 0px;"></div>
</c:if>
<style>
.leader { /*position: relative;
	margin-top: -100px;*/
	margin-left: -10px;
	/*z-index: 1234;*/
	color: white; height : 70px;
	/*width: 1140px;*/
	background-color: rgb(27, 26, 26); /*rgba(244, 239, 239, 1);*/
	color: white;
	height: 70px;
}

.leader_head {
	width: 70px;
	height: 70px;
	margin-top: -15px;
	margin-left: 5px;
	z-index: 33;
	position: relative;
}

.table td {
	border-top: 0;
	border-bottom: 1px solid lightgray;
}

.innerTB td {
	border-bottom: 0;
}
</style>
<s:url var="orderURL" value="/custom/order?productID="></s:url>
<c:choose>
	<c:when test="${isMobile==true }">
		<s:url var="detailURL" value="/show/spec?id="></s:url>
	</c:when>
	<c:otherwise>
		<s:url var="detailURL" value="/custom/detail?id="></s:url>
	</c:otherwise>
</c:choose>
<div style="height: 20px"></div>
<div class="container" style="max-width: 600px;">
	<div class="row-fluid">
		<img src="${tour.introImg }" style="width: 100%;" />
		<div id="property" class="span12 leader" style="margin-left: 0px;">
			<table style="width: 100%">
				<tr style="vertical-align: top;">
					<td><img src="${tour.headImg }"
						class="leader_head img-circle img-polaroid" /></td>

					<td></td>
					<td class=" text-right">
						<button class="btn btn-large btn-warning" type="button"
							style="margin-top: 5px; margin-right: 5px;" onclick="tourBook()">预约</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row-fluid">

		<div class="span12">
			<p style="font-size: 16px;">
				${tour.nickName }<span style="line-height: 24px;">&nbsp;&nbsp;&nbsp;<c:if
						test="${tour.gender==2 }">男</c:if> <c:if test="${tour.gender==1 }">女</c:if></span>
				服务等级：
				<c:forEach var="i" begin="1" end="${tour.serviceLevel }" step="1">
					<img src="${context }/resources/image/red-star.png"
						style="width: 22px; margin-top: -5px;">
				</c:forEach>
			</p>
			<span style="font-size: 18px; line-height: 32px;">基础价格</span><span>(天)</span>
			<strong style="font-size: 18px; line-height: 20px; color: gray;">人民币￥：<c:choose>
					<c:when test="${tour.unitPrice==0}">面议</c:when>
					<c:otherwise>${tour.unitPrice}</c:otherwise>
				</c:choose></strong>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span12">
			<legend>导游简介</legend>
			<p>您好，我是${tour.nickName }</p>
			<p>提供服务的城市： ${tour.location }</p>
			<p>语言能力： ${tour.language }</p>

			<p>${tour.introduce }</p>
		</div>
	</div>
	<div class="row-fluid">
		<legend>服务明细</legend>
		<div class="span12">
			<table class="table">
				<tr>
					<td style="width: 90px;">服务明细</td>
					<td colspan="2">
						<table class="innerTB">
							<tr>
								<td
									style="width:16px;height:24px;background:url(${context}/resources/image/service.png) 0 0px no-repeat">
								</td>
								<td
									style="width: 80px;padding-top: 0px;<c:if test="${tourItems.accompany==0}">text-decoration: line-through;color: gray;</c:if>">伴游向导</td>
								<td
									style="width:16px;height:24px;background:url(${context}/resources/image/service.png) 0 -58px no-repeat">
								</td>
								<td class="span2"
									style="width: 80px;padding-top: 0px;<c:if test="${tourItems.pathPlan==0}">text-decoration: line-through;color: gray;</c:if>">行程规划</td>
							</tr>
							<tr>

								<td
									style="width:16px;height:24px;background:url(${context}/resources/image/service.png) -31px 0px no-repeat">
								</td>
								<td
									style="width: 80px;padding-top: 0px;<c:if test="${tourItems.helpBook==0}">text-decoration: line-through;color: gray;</c:if>">代订服务</td>
								<td
									style="width:16px;height:24px;background:url(${context}/resources/image/service.png) 0 -119px no-repeat">
								</td>
								<td
									style="width: 80px;padding-top: 0px;<c:if test="${tourItems.camera==0}">text-decoration: line-through;color: gray;</c:if>">跟随摄像</td>

							</tr>
							<tr>
								<td
									style="width:16px;height:24px;background:url(${context}/resources/image/service.png) 0 -178px no-repeat">
								</td>
								<td
									style="width: 80px;padding-top: 0px;<c:if test="${tourItems.translate==0}">text-decoration: line-through;color: gray;</c:if>">陪同翻译</td>
								<td
									style="width:16px;height:24px;background:url(${context}/resources/image/service.png) -31px -120px no-repeat">
								</td>
								<td
									style="width: 80px;padding-top: 0px;<c:if test="${tourItems.drive==0}">text-decoration: line-through;color: gray;</c:if>">跟随驾车</td>

							</tr>
						</table>

					</td>
				</tr>

				<tr>
					<td>服务位置</td>
					<td colspan="2">${tour.location }</td>

				</tr>
				<tr>
					<td>费用说明</td>
					<td colspan="2">${tour.feeDesc }</td>

				</tr>
				<tr>
					<td>费用不含</td>
					<td colspan="2">${tour.feeExcept }</td>
				</tr>
				<tr>
					<td>补充说明</td>
					<td colspan="3">${tour.supplement }</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<table>
				<tr>
					<td style="width: 106px; text-align: center;"><img
						src="${tour.weixinQR}" style="max-width: 90px;"></td>
					<td>
						<table>
							<tr>
								<td
									style="height:36px;width:0px;background:url(${context }/resources/image/ico-m-u.png) 0 -157px no-repeat;"></td>
								<td>QQ：</td>
								<td>${tour.QQ}</td>
							</tr>
							<tr>
								<td
									style="height:36px;width:0px;background:url(${context }/resources/image/ico-m-u.png) 0 -229px no-repeat"></td>
								<td>手机号：</td>
								<td>${tour.mobile}</td>
							</tr>
							<tr>
								<td
									style="height:36px;width:0px;background:url(${context }/resources/image/ico-m-u.png) 0 -198px no-repeat"></td>
								<td>邮箱：</td>
								<td><a href="mailto:${tour.EMail}">${tour.EMail}</a></td>
							</tr>
							<tr>
								<td
									style="height:36px;width:0px;background:url(${context }/resources/image/ico-m-u.png) 2px -84px no-repeat;"></td>
								<td>微信：</td>
								<td>${tour.weixinId}</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>

	</div>
	<div class="row-fluid">
		<div class="span12">
			<legend>游客评论</legend>
			<c:forEach var="items" items="${commentsList}" varStatus="status">
				<div class="row-fluid" style="line-height: 35px;">
					<!-- <div class="span4"> 匿名[IP:${items.commentIP }] </div>-->
					<div class="span8">${items.commentDate}&nbsp;发表：</div>
				</div>
				<div class="row-fluid">
					<div class="span12" style="line-height: 30px;">${items.context}
					</div>
				</div>
				<div class="row-fluid" style="border-bottom: 1px solid lightgray;">
					<div class="span12 text-right">
						<a href="javascript:approve(${items.id});"
							id="approve_${items.id}">支持[${items.approveCount}]</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>


	<div class="row-fluid">
		<div class="span12">
			<table style="width: 100%">
				<tr>
					<td></td>
					<td></td>
					<td style="text-align: right; width: 80px;"><strong
						id="comment_count" style="color: red;">${tour.commentCount
							}</strong>&nbsp;人评论</td>
				</tr>
				<tr>
					<td style="width: 120px;"><img
						src="${context }/resources/image/guanxing_logo.jpg"
						style="width: 100px; height: 100px;"></td>
					<td colspan="2"><textarea id="commentText" placeholder="欢迎评论"
							style="height: 100px; width: 100%"></textarea></td>
				</tr>
				<tr>
					<td style="width: 100px;"></td>
					<td><input type="text" id="nickName" placeholder="昵称"
						style="width: 100%"></td>
					<td style="text-align: right; width: 80px;"><button
							class="btn btn-primary" id="btnAddComment"
							style="margin-bottom: 10px;" onclick="addComment()">评论</button></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="span8"></div>
</div>

<div class="modal hide fade" id="tourBook">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h3>导游预约</h3>
	</div>
	<div class="modal-body form-horizontal">

		<div class="control-group">
			<label class="control-label" for="inputEmail">起始日期</label>

			<div class="input-append date form_datetime"
				style="padding-left: 20px;">
				<span class="add-on"> <i class="icon-calendar"></i></span><input
					size="16" type="text" id="startDate" value="2014-07-25"
					name="startDate" class="input-medium" readonly
					style="background-color: white;">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="inputEmail">截止日期</label>

			<div class="input-append date form_datetime"
				style="padding-left: 20px;">
				<span class="add-on"> <i class="icon-calendar"></i></span><input
					size="16" type="text" id="endDate" value="2014-07-25"
					name="endDate" class="input-medium" readonly
					style="background-color: white;">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="inputEmail">联系电话</label>
			<div class="controls">
				<input type="text" id="linkPhone">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="inputEmail">联系人姓名</label>
			<div class="controls">
				<input type="text" id="linkName">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="inputEmail">备注信息</label>
			<div class="controls">
				<textarea id="memo"></textarea>
			</div>
		</div>

	</div>
	<div class="modal-footer">
		<a href="#" onclick="javascript:tourBookClose();" class="btn">关闭</a>
		<button id="freeBook" onclick="updateBook()" class="btn btn-warning">预约</button>
	</div>
</div>

<script type="text/javascript">
	function tourBook() {
		$('#tourBook').modal("show");
	}
	function tourBookClose() {
		$('#tourBook').modal("hide");
	}
	function updateBook() {
		var custId = "${tour.custId}";

		if (startDate.value > endDate.value) {
			alert("对不起，起始日期不能晚于截止日期，请确认重新填写。");
			return;
		}
		if (linkPhone.value.length > 11 || linkPhone.value.length == 0) {
			alert("请填写正确的联系电话号码");
			linkPhone.focus();
			return;
		}
		if (linkName.value.length == 0 || linkName.value.length > 5) {
			alert("请填写联系人姓名");
			linkName.focus();
			return;
		}

		var url = "${context}/tour/book";
		$.post(url, {
			custId : custId,
			startDate : startDate.value,
			endDate : endDate.value,
			linkPhone : linkPhone.value,
			linkName : linkName.value,
			memo : memo.value
		}, function(result, status) {
			//debugger;
			if (status == 'success') {
				if (result == 0) {
					alert("预约信息已经保存，领队会及时与您联系，谢谢！");
					freeBook.disabled = true;
				} else
					alert("对不起，评论数据提交失败，请重新尝试！");
			}

		});
	}

	function addComment() {
		if (commentText.value.length == 0) {
			alert("请输入评论内容。");
			return;
		}
		var url = "${context}/tour/addComments";
		//debugger;
		$.post(url, {
			custId : "${tour.custId}",
			nick : nickName.value,
			context : commentText.value
		}, function(result, status) {
			//debugger;
			if (status == 'success') {
				if (result > 0) {
					alert("您的评论数据已经提交，谢谢！");
					btnAddComment.disabled = true;
				} else
					alert("对不起，预约失败，请重新尝试！");
			}

		});
	}//end function addComment

	function approve(id) {
		var url = "${context}/tour/approve";
		//debugger;
		$.post(url, {
			commentId : id
		}, function(result, status) {
			//debugger;
			if (status == 'success') {
				if (result > 0) {
					var aObj = document.getElementById("approve_" + id);
					aObj.innerHTML = "支持[" + result + "]";
					aObj.href = "javascript:void(0)";
				}
			}

		});
	}//end function approve

	$(".form_datetime").datetimepicker({
		format : 'yyyy-mm-dd',
		forceParse : 0,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		todayBtn : 1,
		language : 'zh-CN',
		autoclose : 1
	});

	startDate.value = CurentTime(1);
	endDate.value = CurentTime(1);

	
</script>