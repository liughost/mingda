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
<div class="maink">
	<div class="main">
		<div class="guide-t">
			<img src="${context }/resources/rabbit/images/banner-4.jpg" alt="" />
			<div class="guide-tbox">
				<div class="guide-tl">
					<img src="http://guantravel.com:5984/tours/${tour.id}/head.jpg"
						alt="" />
				</div>
				<div class="guide-tr">
					<div class="guide-trt">
						<em>${tour.nickName }</em>

						<c:forEach var="i" begin="1" end="${tour.serviceLevel}" step="1">
							<span class="star"></span>
						</c:forEach>
						<c:forEach var="i" begin="${tour.serviceLevel}" end="4" step="1">
							<span class="star-no"></span>
						</c:forEach>

					</div>
					<ul class="guide-trb">
						<li>常驻城市：<span>${tour.location }</span></li>
						<li>服务城市：<span>${tour.serviceCities }</span></li>
						<li>服务景点：<span>${tour.scenic }</span></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="guide-b">
			<div class="guide-bl">
				<ul id="guide" class="guide">
					<li class="guTag"><a onClick="guTag('guideCont0',this)"
						href="javascript:;">导游简介</a></li>
					<li><a onClick="guTag('guideCont1',this)" href="javascript:;">评论</a></li>
				</ul>
				<div id="guideCont">
					<div id="guideCont0" class="guideCont guTag">
						<div class="guide-j">
							<div class="guide-jt">
								<p>${tour.introduce }</p>
							</div>
							<ul class="guide-jb">
								<li>
									<div class="guide-jbl">语言能力</div>
									<div class="guide-jbr">
										<em>母语：<span>${tour.language.motherLang }</span></em> <em>外语：
											<span>${tour.language.foreignLang }</span>
										</em>
									</div>
								</li>
								<li>
									<div class="guide-jbl">车型</div>
									<div class="guide-jbr">
										<em>${tour.carType }</em>
									</div>
								</li>
								<li>
									<div class="guide-jbl">服务项目</div>
									<div class="guide-jbr">
										<c:forEach var="item" items="${tour.serviceItem}"
											varStatus="status">
											<div class="g-cda">
												<div class="cda-l"></div>
												<div class="cad-r">${item }</div>
											</div>
										</c:forEach>

										<!-- 
										<div class="g-cda">
											<div class="cda-l"></div>
											<div class="cad-r">纽约</div>
										</div>

										<div class="g-cfc">
											<div class="cfc-l"></div>
											<div class="cfc-r">我是兼职导游</div>
										</div>
										 -->

									</div>
								</li>
								<li>
									<div class="guide-jbl">费用包含</div>
									<div class="guide-jbr">
										${tour.feeDesc }
										<!-- 每天服务时长：<span>9个小时</span><br /> 超时服务费：<span>CNY
											100（1小时）</span><br /> 接待人数：<span>最多4人</span>
											-->
									</div>
								</li>
								<li>
									<div class="guide-jbl">费用不含</div>
									<div class="guide-jbr">
										${tour.feeExcept }
										<!-- 服务小费：<span>无</span><br /> 餐补费：<span>无</span><br /> 异地住宿补贴：<span>无</span>
										-->
									</div>
								</li>
								<li>
									<div class="guide-jbl">其他说明</div>
									<div class="guide-jbr">${tour.supplement }</div>
								</li>
							</ul>
						</div>
					</div>
					<div id="guideCont1" class="guideCont">
						<div class="view-t">
							<div id="briefInfo" style="height: auto;">
								<ul id="commentList">

									<c:forEach var="comment" items="${commentsList}"
										varStatus="inx">
										<li>
											<div class="view-tl">
												<c:choose>
													<c:when test="${not empty comment.headImg }">
														<img src="${comment.headImg}" alt="" />
													</c:when>
													<c:otherwise>
														<img
															src="${context }/resources/rabbit/images/head22-22.jpg"
															alt="" />
													</c:otherwise>
												</c:choose>

											</div>
											<div class="view-tr">
												<div class="view-trt">
													<a href="#">${comment.nickName }</a><span>${comment.commentDate}</span>
												</div>
												<div class="view-trb">${comment.context }</div>
											</div>
										</li>
									</c:forEach>

								</ul>
							</div>
							<div class="open" id="expandMore"
								<c:if test="${empty nextKey }">style="display:none;"</c:if></div>
							<a href="javascript:;">更多</a>（${commentCount} 条评论）
						</div>
						<script type="text/javascript">
								var nextKey = "${nextKey}";
								var pageCount = "${pageCount}";
								function addComment(headImg, nickName, context, commentDate)
								{
									var liNode = document.createElement("li");
									if(headImg.length == 0) headImg="${context }/resources/rabbit/images/head22-22.jpg";
									liNode.innerHTML = '<div class="view-tl"><img src="'+headImg+'" alt="" /> </div>'
								+ '<div class="view-tr"> <div class="view-trt"> <a href="#">'+nickName+'</a><span>'+commentDate+'</span>	</div>'
								+ '<div class="view-trb">'+context +'</div></div>';
									commentList.appendChild(liNode);
								}
								function loadMore()
								{
									var url = "${context}/tour/comment/more";
									
									$.post(url, {
										id: '${tour.id}',
										startKey: nextKey
										
									}, function(result, status) {
										debugger;
										
										if (status == 'success') {
											
											var comments = eval(result);
											for(var i=0; i<comments.length; i++){
												if(i==comments.length-1){
													//最后一个不显示，作为下一页的索引
													if(comments.length>=pageCount)
														nextKey = comments[i].id;
													else{
														nextKey = "";
														expandMore.style.display = "none";
													}
												}
												
												//不是最后一个 或者 总数小于pageCount
												if(i!=comments.length-1 || comments.length<pageCount){
													addComment(comments[i].headImg, decodeURIComponent(comments[i].nickName), decodeURIComponent(comments[i].context), comments[i].commentDate);
												}
												
												
											}	
										} else {
											msg += '网络异常，或者未登录，请重试。';
										}
									
									});
								}
								$('#expandMore').click(function() {
									loadMore();
								});
							</script>
					</div>
					<div class="view-b">
						<textarea placeholder="发表留言" id="commentText"></textarea>
						<div class="view-bb">
							<div class="l" id="send_error" style="color: red;">别忘了发表留言哦</div>
							<div class="r">
								<input type="button" class="inputbg2" value="确定"
									onclick="javascript:sendComment();" /> <a href="#modal2"
									class="modalLink inputbg3">去注册</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="guide-br">
			<div class="guide-brt">
				<div class="guide-brtitle">
					<div class="l">服务日历</div>
				</div>
				<div class="route-tr">
					<c:forEach var="groupCanlendar" items="${groupCanlendars}"
						varStatus="g">
						<c:choose>
							<c:when test="${g.index==0}">
								<s:url var="show" value="" />
							</c:when>
							<c:otherwise>
								<s:url var="show" value="none" />
							</c:otherwise>
						</c:choose>
						<table width="100%" cellpadding="0" cellspacing="0" border="0"
							id="can-${g.index}" style="display: ${show};">
							<tbody>
								<tr style="background: #f9e509;">
									<td colspan="7">
										<table class="line-table" width="100%" cellpadding="0"
											cellspacing="0" border="0">
											<tbody>
												<tr>
													<td class="last_mon" style="width: 56px;"
														onclick="getGroup(0,${g.index})"></td>
													<td>${groupCanlendar.year
											}年${groupCanlendar.month }月</td>
													<td class="next_mon" style="width: 56px;"
														onclick="getGroup(1,${g.index})"></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr class="tdword">
									<td width="14.2%" class="weekend">日</td>
									<td width="14.2%">一</td>
									<td width="14.2%">二</td>
									<td width="14.2%">三</td>
									<td width="14.2%">四</td>
									<td width="14.2%">五</td>
									<td class="weekend">六</td>
								</tr>
								<tr>
									<c:forEach var="items" items="${groupCanlendar.days}"
										varStatus="status">

										<c:choose>
											<c:when test="${items.price != 0 }">
												<td class="group_date"
													onmouseover="onPriceMouse('${items.date}',${items.price })"><a
													class="modalLink" href="#modal9">${items.day}<br /> <span>￥${items.price }</span></a>
												</td>
											</c:when>
											<c:otherwise>
												<c:if test="${items.localMon }">
													<td class="thismon">${items.day }</td>
												</c:if>
												<c:if test="${items.localMon == false }">
													<td class="non_thismon">${items.day }</td>
												</c:if>
											</c:otherwise>
										</c:choose>

										<c:if test="${(status.index+1)%7==0 }">
								</tr>
								<tr>
								</c:if>
					</c:forEach>
					</tr>
					<tr></tr>
					</tbody>
					</table>
					</c:forEach>



					<script type="text/javascript">
							function getGroup(t, inx) {
								var oInx = inx;
								if (t == 0) {
									inx = parseInt(inx) - 1;
								} else {
									inx = parseInt(inx) + 1;
								}
								//alert(inx);
								//设置新选择的日历出现
								var canObj = document.getElementById("can-"
										+ inx);
								if (canObj != null) {
									canObj.style.display = "";
								} else {
									//新日历显示失败，直接退回
									return;
								}
								//隐藏原有日历
								var canObj = document.getElementById("can-"
										+ oInx);
								if (canObj == null) {
									return;
								}
								canObj.style.display = "none";
							}
						</script>
				</div>
			</div>
			<div class="guide-brb">
				<div class="guide-brtitle">
					<div class="l">联系方式</div>
				</div>
				<div class="guide-brcont">
					<ul class="guide-brcont-l">
						<li class="gu1">${tour.mobile }</li>
						<li class="gu2">${tour.QQ }</li>
						<li class="gu3">${tour.EMail }</li>
						<li class="gu4">${tour.weixinId }</li>
					</ul>
					<div class="guide-brcont-r">
						<img
							src="http://guantravel.com:5984/tours/${tour.id}/weixinQR.jpg"
							alt="" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<!-- 预约弹框 STA -->
<link rel="stylesheet"
	href="${context }/resources/rabbit/css/jcDate.css" type="text/css" />
<script src="${context }/resources/rabbit/js/jquery-jcDate.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $(function (){
        $("input.jcDate").jcDate({					       
            IcoClass : "jcDateIco",
            Event : "click",
            Speed : 100,
            Left : 0,
            Top : 54,
            format : "-",
            Timeout : 100
        });
    });
    </script>
<div id="modal9" class="modal makebox"
	style="top: 708px; left: 438.5px; display: none;">
	<div class="make">
		<div class="make-t">
			<div class="l">在线预约</div>
			<div class="r" id="divUnitPrice">
				￥${tour.unitPrice}/天<br>（每天10小时）
			</div>
		</div>
		<div class="make-b">
			<ul class="make-li">
				<li>
					<div class="l">
						<!--  input type="radio" name="pick" value="pick"> -->
						<c:if test="${fn:length(tour.carType)==0 }">不</c:if>
						带车，最多6人
					</div> <!-- 
					<div class="r">￥10880</div>
					 -->
				</li>

			</ul>
			<div class="make-date">
				<div class="date-zi">预约日期</div>
				<input id="startDate" class="jcDate jcDateIco" value="2015-01-28">
				<div class="on"></div>
			</div>
			<div class="make-sel">
				<div class="l">
					<div class="date-zi">旅客人数</div>
					<select id="selPersons">
						<option value="1">1人</option>
						<option value="2">2人</option>
						<option value="3">3人</option>
						<option value="4">4人</option>
						<option value="5">5人</option>
						<option value="6">6人</option>
						<option value="7">7人</option>
						<option value="8">8人</option>
						<option value="9">9人</option>
						<option value="10">10人</option>
						<option value="11">11人</option>
						<option value="12">12人</option>
						<option value="13">13人</option>
						<option value="14">14人</option>
						<option value="15">15人</option>
						<option value="16">16人</option>
						<option value="17">17人</option>
						<option value="18">18人</option>
						<option value="19">19人</option>
						<option value="20">20人</option>
					</select>
					<div class="on"></div>
				</div>
				<div class="l" style="float: right;">
					<div class="date-zi">预约天数（每天10小时）</div>
					<select id="selDays" onchange="calTotal()">
						<option value="1">1天</option>
						<option value="2">2天</option>
						<option value="3">3天</option>
						<option value="4">4天</option>
						<option value="5">5天</option>
						<option value="6">6天</option>
						<option value="7">7天</option>
						<option value="8">8天</option>
						<option value="9">9天</option>
						<option value="10">10天</option>
						<option value="11">11天</option>
						<option value="12">12天</option>
						<option value="13">13天</option>
						<option value="14">14天</option>
						<option value="15">15天</option>
					</select>
					<div class="on"></div>
				</div>
			</div>
			<div class="make-btn">
				<div class="btn1">
					费用总计：<br> <em>￥<span id="totalPrice">10880</span></em>
				</div>
				<em> <input type="button" onclick="order();" class="inputbg4"
					value="立即预定">
				</em>
			</div>
			<em> </em>
		</div>
		<em> </em>
	</div>
	<em> </em>
</div>
<!-- 预约弹框 End -->
<!-- 
<script type="text/javascript"
	src="${context }/resources/rabbit/js/Validate.js"></script>
<script type="text/javascript"
	src="${context }/resources/rabbit/js/Validate.form.js"></script>
 -->
<script type="text/javascript">
var unitPrice = 0;
function onPriceMouse(day, price)
{
	startDate.value = day;
	divUnitPrice.innerHTML = '￥'+price+'/天<br/>每天10小时';
	unitPrice = parseInt(price);
	calTotal();
}
function calTotal()
{
	//debugger;
	var days = selDays.options[selDays.selectedIndex].value;
	var persons = selPersons.options[selPersons.selectedIndex].value;
	var total = days*unitPrice;
	totalPrice.innerHTML = total;
	console.log({days:days, persons:persons, unitPrice:unitPrice, total:total});
}
function order()
{
	//判断是否登录
	//下单
	//支付
}

function sendComment()
{
	
	debugger;
	if($("#commentText").val().length==0)
		{
		$("#send_error").text('请输入评论的内容。');
		commentText.focus();
		return;
		}
	var url = "${context}/tour/addComments";
		
	$.post(url, {
		username: '${username}',
		tourKey: '${tour.id}',
		context: $("#commentText").val()
		
	}, function(result, status) {
		debugger;
		
		if (status == 'success') {
			
			if (result.length>0) {

				$("#send_error").text('评论发送成功');
				$("#commentText").text("");
			} else {
				$("#send_error").text('评论发送失败！');
			}
		} else {
			msg += '网络异常，或者未登录，请重试。';
		}
	});
	
}
</script>