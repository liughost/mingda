<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.inText {
	background: #fff;
	width: 130px;
	padding: 0px 0 0 0;
	margin-top: 8px;
}

.make-sel,.make-btn {
	margin-top: 6px;
}
</style>

<!-- 主体内容区 STA -->
<div class="maink" style="background-color: #f1f4f6;">
	<div class="main">
		<div class="route-title">${info.productName }</div>
		<div class="route-t">
			<div class="route-tl">
				<div class="route-tlimg">
					<img style="width: 346px; height: 346px;"
						src="http://guantravel.com:5984/travel/${info.id }/intro.jpg"
						alt="" />
				</div>
				<ul class="route-tlzi">
					<li>
						<div class="l">出发城市</div>
						<div class="r">${info.deaprtCity }</div>
					</li>
					<li>
						<div class="l">目 的 地</div>
						<div class="r">${info.destCity }</div>
					</li>
					<li>
						<div class="l">时间安排</div>
						<div class="r">${info.settle }</div>
					</li>
					<li>
						<div class="l">发团日期</div>
						<div class="r">${firstGroup }</div>
					</li>
					<li>
						<div class="l">交通信息</div>
						<div class="r">${info.traffic }</div>
					</li>
				</ul>
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
											<!-- onclick="group_detail(${info.pid},'${items.date}')"  -->
											<td class="group_date"
												onmouseover="onPriceMouse('${items.date}','${info.pid}', ${items.price },${items.childrenPrice}, ${items.hotelSpanPrice})"><a
												class="modalLink" href="#modal9">${items.day
										}<br />
													<span>￥${items.price }</span>
											</a></td>
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
						var canObj = document.getElementById("can-" + inx);
						if (canObj != null) {
							canObj.style.display = "";
						} else {
							//新日历显示失败，直接退回
							return;
						}
						//隐藏原有日历
						var canObj = document.getElementById("can-" + oInx);
						if (canObj == null) {
							return;
						}
						canObj.style.display = "none";
					}
				</script>
			</div>
		</div>
		<div class="t-det">
			<div class="route-b">
				<!-- 推荐理由 STA -->
				<div class="route-btj">
					<div class="route-btitle">推荐理由</div>
					<div class="route-btj-cont">${info.recommend}</div>
				</div>
				<!-- 推荐理由 End -->

				<!-- 产品详情 STA -->
				<div class="route-bcp">
					<div class="route-btitle">产品详情</div>
					<div class="route-bsm-cont">${info.detail}</div>
				</div>
				<!-- 产品详情 End -->

				<!-- 产品特色 STA -->
				<div class="route-bts">
					<div class="route-btitle">产品特色</div>
					<div class="route-bsm-cont">${info.feature}</div>
					<!-- 
					<ul class="route-bbh-cont">
						<li>全程中英文专业自驾向导7x24小时陪同，无超时费用，省心省力、安全无忧！</li>
						<li>轻松纯玩自驾小团，每团最多不超过15人！</li>
						<li>包含七大公园门票：环球影城+宰恩+拱门+布莱斯+大峡谷+羚羊谷+纪念碑峡谷!</li>
						<li>北京到洛杉矶国际航班往返直飞！</li>
						<li>城市繁华区4钻品质酒店（小镇3钻）！</li>
						<li>美国西海岸七大国家公园/部落公园+66号公路！</li>
						<li>无旅游商店购物或额外收费项目！</li>
						<li>巨无霸越野车+燃油+豪华保险套餐+导航全部包含！</li>
					</ul>
					 -->
				</div>
				<!-- 产品特色 End -->

				<!-- 行程安排 STA -->
				<div class="route-bxc">
					<div class="route-btitle">行程安排</div>
					<div class="route-bxc-cont">
						<ul class="route-bxc-cont-t">
							<li><span>集合地信息：</span>${info.assembly}</li>
						</ul>
						<c:forEach var="items" items="${info.journey}" varStatus="g">
							<div class="plan">
								<div class="plan-title">
									<strong><b>${g.index+1 }</b>day</strong>${items.title}
								</div>
								<div class="plan-icon">
									<div class="picon-jt">
										<div class="picon-t">交通</div>
										<ul class="picon-ul">
											<c:forEach var="traffic" items="${items.traffic}">
												<li>

													<div class="picon-ul-l">${traffic.vehicle}</div>

													<div class="picon-ul-r">${traffic.detail}</div>
												</li>
											</c:forEach>

										</ul>
									</div>
									<div class="picon-cy">
										<div class="picon-t">餐饮</div>
										<ul class="picon-li">
											<li><span>早餐：</span>${items.meal.breakfirst }</li>
											<li><span>午餐：</span>${items.meal.lanch }</li>
											<li><span>晚餐：</span>${items.meal.supper }</li>
										</ul>
									</div>
									<div class="picon-zx">
										<div class="picon-t">住宿</div>
										<div class="picon-zi">${items.hotel }</div>
									</div>
								</div>
								<c:forEach var="detail" items="${items.detail}">

									<c:if test="${detail.type == 'img' }">
										<div class="plan-img">
											<img src="${detail.text }">
										</div>
									</c:if>
									<c:if test="${detail.type == 'text' }">
										<div class="plan-b">${detail.text }</div>
									</c:if>

								</c:forEach>
								<!--
								<ul class="route-bbh-cont">
									<li>13:00左右，抵达洛杉矶</li>
									<li>14:30 过关提取行李，去车场提车</li>
									<li>15:30 提车后，前往圣莫妮卡海滩游玩(游玩时间约3小时)</li>
									<li>17:00 游览完毕，前往酒店</li>
									<li>18:00 抵达酒店</li>
									<li>19:00 酒店附近吃晚餐，餐后返回酒店休息</li>
									<li>本日行程取决于航班抵达时间，可能微调。</li>
								</ul>

								<p>【行程亮点】</p>
								洛杉矶是加州第一大城市，美国第二大城市，仅次于纽约。同时它也是全世界的文化、科技、国际贸易和高等教育中心之一洛杉矶一望无垠的沙滩和明媚的阳光、闻名遐迩的“电影王国”好莱坞、引人入胜的迪斯尼乐园、峰秀地灵的贝佛利山庄使洛杉矶成为一座举世闻名的“电影城”和“旅游城”。洛杉矶市区广阔，布局分散，整座城市是以千千万万栋一家一户的小住宅为基础。绿荫丛中，鳞次栉比的庭院式建筑，色彩淡雅，造型精巧，风格各异，遍布于平地山丘上。
								</div>
								-->

							</div>
						</c:forEach>
					</div>
				</div>
				<!-- 行程安排 End -->

				<!-- 费用说明 STA -->
				<div class="route-bsm">
					<div class="route-btitle">费用说明</div>
					<div class="route-bsm-cont">${info.feeIntro }</div>
				</div>
				<!-- 费用说明 End -->

				<!-- 费用包含 STA -->
				<div class="route-bbh">
					<div class="route-btitle">费用包含</div>
					<div class="route-bsm-cont">${info.feeInclude }</div>
					<!-- 
					<ul class="route-bbh-cont">
						<li>7x24小时优秀中/英双语自驾向导服务</li>
						<li>包含七大公园门票：环球影城+宰恩+拱门+布莱斯+大峡谷+羚羊谷+纪念碑峡谷</li>
						<li>北京/上海往返美国航班经济舱机票、燃油附加费、机票税</li>
						<li>城市繁华区4钻品质酒店，小镇3钻酒店住宿</li>
						<li>免收每人每天8美元的导游小费</li>
						<li>汽车租赁费及随车保险（车损或盗抢不计免赔，第三者100万美金+个人意外伤害保险+各种税费）</li>
						<li>全程燃油/对讲机/中文导航</li>
						<li>中国驾照英文翻译件</li>
						<li>英国太阳联合海外无忧个人旅游保险（高达100万的意外险），详情见保单。</li>
					</ul>
					 -->
				</div>
				<!-- 费用包含 End -->

				<!-- 费用不含 STA -->
				<div class="route-bbu">
					<div class="route-btitle">费用不含</div>
					<div class="route-bsm-cont">${info.feeNotInclude }</div>
					<!-- 
					<ul class="route-bbh-cont">
						<li>护照、签证等相关证件的费用</li>
						<li>超出机票包含免费行李的行李托运费</li>
						<li>全程餐食自理（多数酒店早餐包含，午、晚餐走到哪吃到哪儿，丰简由己，午餐约4-8美金，晚餐约10美金</li>
						<li>停车费、高速通行费及违章罚款等费用请自理（若产生）</li>
						<li>行程中自由活动时的产生的一切费用请自理</li>
						<li>行程以外的一切私人消费，如酒店内的洗衣费、电话费、饮料、付费电视、行李搬运费以及吸烟罚款等费用，个人消费所引起的小费、出入境的行李海关课税、超重行李的托运费、管理费等</li>
						<li>不可抗拒的客观原因（如天灾、战争、罢工等）或航空公司航班延误、取消、领馆签证延误等非我公司原因引起的特殊情况，我公司有权变更或取消行程，由此产生的相关费用（如在外延期签证、食、宿、行一切费用）请自理</li>
					</ul>
					-->
				</div>
				<!-- 费用不含 End -->
			</div>
			<div class="t-detr">
				<div class="t-detr-t">最新动态</div>
				<div class="t-detr-b">
					<ul>
						<c:forEach var="items" items="${articleList}">
							<li>
								<div class="t-detr-bl">
									<a href="${context }/custom/detail?id=${items.id }"><img
										src="${items.img }" style="width: 98px; height: 98px;" alt="" /></a>
								</div>
								<div class="t-detr-br">
									<div class="t-detr-brt">
										<a href="${context }/custom/detail?id=${items.id }">${items.title }</a>
									</div>
									<div class="t-detr-brb">${items.specialMaketime}</div>
								</div>
							</li>
						</c:forEach>

					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 主体内容区 End -->
<!-- 预订弹出窗口 Begin -->
<div id="modal9" class="modal makebox"
	style="top: 708px; left: 438.5px; display: none;">
	<div class="make">
		<div class="make-t">
			<div class="l">在线预约</div>
			<div class="r" id="divUnitPrice">
				成人:￥<br>儿童:￥
			</div>
		</div>
		<div class="make-b">
			<div class="make-date" style="margin-top: 5px;">
				<div class="date-zi">预约日期</div>
				<input readonly id="startDate" class="jcDate jcDateIco"
					value="2015-01-28">
				<div class="on"></div>
			</div>
			<div class="make-sel">
				<div class="l" style="width: 123px;">
					<div class="date-zi">旅客成人数</div>
					<select id="selPersons" onchange="priceChange()">
						<option value="1">1人</option>
						<option value="2" selected>2人</option>
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
				<div class="l" style="float:right;">
					<div class="date-zi">旅客儿童数</div>
					<select id="selChildren" onchange="priceChange()">
						<option value="0" selected>0人</option>
						<option value="1">1人</option>
						<option value="2">2人</option>
						<option value="3">3人</option>
						<option value="4">4人</option>
						<option value="5">5人</option>
						<option value="6">6人</option>
						<option value="7">7人</option>
						<option value="8">8人</option>
						<option value="9">9人</option>

					</select>
					<div class="on"></div>
				</div>

			</div>
			<div class="make-sel">
				<div class="l" style="width: 123px;">
					<div class="date-zi">旅客姓名</div>
					<input id="nickName" onkeyup="nickNameChange()" placeholder="旅客姓名"
						class="inText" value="" style="">
					<div id="nickResult" class=""></div>
				</div>
				<div class="l" style="float:right;">
					<div class="date-zi">联系电话</div>
					<input id="linkMobile" onkeyup="mobileChange()" placeholder="联系电话"
						class="inText" value="" style="">
					<div id="mobileResult" class=""></div>
				</div>

			</div>

			<div class="make-date" style="margin-top: 10px;">
				<div class="date-zi">优惠码</div>
				<input id="inviteCode" placeholder="优惠码" class=jcDate value=""
					style="background: #fff;" onkeyup="validateInviteCode()"
					onBlur="validateInviteCode()"> <input type="hidden"
					id="offPriceValue" value="0">
				<div id="inviteCodeResult" class=""></div>
			</div>
			<ul class="make-li">
				<li style="margin-top:5px;">
					<div class="l">
						<input onchange="priceChange()" type="radio" name="agreeShare"
							value="1" checked><span onclick="agreeChange(1)">同意拼房；</span><input
							onchange="priceChange()" type="radio" name="agreeShare" value="0"><span
							onclick="agreeChange(0)">不同意拼房；</span>
					</div>
					<div class="r" id="hotelSpan">房差:￥0</div>

				</li>
				<li  style="margin-top:5px;">
					<div class="l">
						<span style="color: red;" id="warnningText"></span>
					</div> <!-- 
					<div class="r">￥10880</div>
					 -->
				</li>

			</ul>
			<div class="make-btn">
				<div class="btn1">
					费用总计：<br> <em>￥<span id="totalPrice"></span></em>
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
<!-- 预订弹出窗 end -->
<script type="text/javascript">
window.onload = function() {document.title = '兔子旅行-${info.productName }';};
var aPrice = 0;
var cPrice = 0;
var hPrice = 0;
function nickNameChange()
{
	if(nickName.value.length>=2)
		nickResult.setAttribute("class", "on");
	else
		nickResult.setAttribute("class", "off");
}
function mobileChange()
{
	if(linkMobile.value.length==11)
		mobileResult.setAttribute("class", "on");
	else
		mobileResult.setAttribute("class", "off");
}
function agreeChange(a)
{
	var aObj = document.getElementsByName("agreeShare");
	for (var i = 0; i < aObj.length; i++) {
		if (aObj[i].value == a) {
			aObj[i].checked = true;
		}
		else{
			aObj[i].checked = false;
		}
	}
	priceChange();
}
function agreeValue() {
	var aObj = document.getElementsByName("agreeShare");
	for (var i = 0; i < aObj.length; i++) {
		if (aObj[i].checked) {
			if (aObj[i].value == 1)
				return false;
		}
	}
	return true;
}

function priceChange() {
	
	debugger;

	//计算标间数量
	var aCount = parseInt(selPersons.options[selPersons.selectedIndex].value);
	var cCount = parseInt(selChildren.options[selChildren.selectedIndex].value);
	
	//var h = adultCount.value / 2;
	//计算占用的床数量
	var beds = aCount;
	var s = 0;
	debugger;
	var selAgree = agreeValue();
	if (aCount % 2 != 0 && selAgree) {
		//占用了多余的标间
		s = hPrice;
	}
	//儿童房差，默认儿童不单独住
	//s += cCount * hPrice;

	//hotelSpan
	debugger;
	var total = aCount * aPrice + cCount * cPrice + s
			- parseInt(offPriceValue.value);
	totalPrice.innerHTML = total + "元";
}

function validateInviteCode() {
	if(inviteCode.value.length<3)
		return;
	//debugger;
	var url = "${context}/custom/inviteCodeValidate";
	$.post(url, {
		code : inviteCode.value
	}, function(result, status) {
		//debugger;
		if (status == 'success') {

			console.log(inviteCode.value + ":"+result);
			var codeObj = eval(result);

			if (codeObj != null && codeObj.length > 0) {

				if (codeObj[0].codeStatus == 0) {
					inviteCode.disabled = true;
					inviteCodeResult.setAttribute("class", "on")
					offPriceValue.value = codeObj[0].offPrice;
					warnningText.innerHTML = "优惠：￥" + codeObj[0].offPrice
							+ "元";
					priceChange();
				} else {
					warnningText.innerHTML = "对不起，此优惠码已经使用或者过期，请确认后重试！";
				}

			} else {
				//count.innerHTML = 0;
				offPriceValue.value = 0;
				warnningText.innerHTML = "对不起，此优惠码已经不存在，请确认后输入！";;

			}
		} else {
			offPriceValue.value = 0;
			warnningText.innerHTML = "对不起，访问失败，请刷新后重试！。";
		}

	});
}
function onPriceMouse(gDate, couchId, adultPrice, childrenPrice, hotelSpanPrice)
{
	debugger;
	aPrice = parseInt(adultPrice);
	cPrice = parseInt(childrenPrice);
	hPrice = parseInt(hotelSpanPrice);
	startDate.value = gDate;
	divUnitPrice.innerHTML = "成人:￥"+adultPrice+"<br>儿童:￥"+childrenPrice;
	hotelSpan.innerHTML = "单房差:￥"+hotelSpanPrice;
	//重新计算总价
	priceChange();
}
</script>