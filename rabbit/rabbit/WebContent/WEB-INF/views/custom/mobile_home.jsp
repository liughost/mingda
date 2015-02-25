<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.left_td {
	width: 32%;
}

.left_img {
	width: auto;
	height: auto;
}

.right_text {
	width: 18%;
	padding: 5px;
	color: white;
}

a {
	color: white;
}

a:visited {
	color: white;
}

.table td {
	font-size: 20px;
	line-height: 30px;
	padding: 0;
	vertical-align: middle;
	text-align: center;
	border-top: 0;
}
</style>
<div style="height: 20px"></div>
<div class="container" style="max-width: 600px;">
	<table class="table" style="width: 100%">
		<tr>
			<td class="left_td"><a href="${context }/custom/product/search"><img
					src="${context }/resources/custom/images/mobile/ml4.jpg"
					class="left_img"></a></td>
			<td class="right_text" style="background-color: rgb(198, 210, 213);"><a
				href="${context }/custom/product/search">目的地</a></a></td>
			<td class="left_td"><a href="${context }/custom/tour/search"><img
					src="${context }/resources/custom/images/mobile/ml2.jpg"
					class="left_img"></a></td>
			<td class="right_text" style="background-color: rgb(213, 208, 105);"><a
				href="${context }/custom/tour/search">导游陪你</a></td>
		</tr>
		<tr>
			<td class="left_img"><a href="${context }/custom/book"><img
					src="${context }/resources/custom/images/mobile/ml3.jpg"
					class="left_img"></a></td>
			<td class="right_text" style="background-color: rgb(106, 205, 167);"><a
				href="${context }/custom/book">热门自驾推荐</a></td>
			<td class="left_img"><a href="${context }/custom/articles"><img
					src="${context }/resources/custom/images/mobile/f01.jpg"
					class="left_img"></a></td>
			<td class="right_text" style="background-color: rgb(228, 102, 160);"><a
				href="${context }/custom/articles">最新动态</a></td>
		</tr>
		<tr>
			<td class="left_img"><a href="${context }/custom/group/list"><img
					src="${context }/resources/custom/images/mobile/ml5.jpg"
					class="left_img"></a></td>
			<td class="right_text" style="background-color: rgb(219, 187, 137);"><a
				href="${context }/custom/group/list">近期发团信息</a></td>
			<td class="left_img"><a href="${context }/custom/private"><img
					src="${context }/resources/custom/images/mobile/ml6.jpg"
					class="left_img"></a></td>
			<td class="right_text" style="background-color: lightblue;"><a
				href="${context }/custom/private">私人订制</a></td>
		</tr>
	</table>
</div>