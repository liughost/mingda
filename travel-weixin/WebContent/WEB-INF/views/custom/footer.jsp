<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
h4 {
	font-size: 18px;
	font-weight: normal;
	color: white;
}

a:hover {
	text-decoration: none;
}
</style>
<div style="height: 10px;"></div>
<footer style="background-color: #333; color: white;" id="footer">
	<div class="row-fluid" style="">
		<div class="span1"></div>
		<div class="span4">
			<h4>我们的服务</h4>
			<p>冠行是北京青年旅行社股份有限公司国际一部的海外自驾游品牌,我们出行的方式是私家小团或订制小团的自驾游。行程具有自由、个性、灵活与可定制等特点。我们的客户是追求人格独立和心性自由的旅行者。
			</p>
		</div>

		<div class="span4">
			<h4>联系冠行</h4>

			<p>
				微信号：zwxzjz
				<!-- (<a href="javascript:showQR();" style="color: white;">点击扫二维码</a>)  -->
			</p>
			<img id="QR"
				style="display: none; position: absolute; z-index: 5; width: 150px;"
				src="http://guantravel.com/resources/media/201410/566c75560d6e19fcf33b480bf0f030c1.jpg">
			<p>客户电话：400-6910-800</p>
			<p>地址：北京市朝阳区潘家园南里12号潘家园大厦北区319室</p>

		</div>
		<div class="span2">
			<h4>关注微信</h4>
			<img id="QR" style="width: 100px;"
				src="http://guantravel.com/resources/media/201410/566c75560d6e19fcf33b480bf0f030c1.jpg">
		</div>
		<div class="span1"></div>
	</div>

</footer>
<footer
	style="background-color: black; color: white; padding: 20px 0 10px 0;"
	id="footer">
	<div class="row-fluid">
		<div class="span12">
			<p class="text-center" style="color: white;">
				<span style="color: lightgray;">友情链接：</span><a
					href="http://usa-home.cn/">美国房产精英</a>
			</p>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<p class="text-center">技术支持 ©北京智网明达信息技术有限公司
				2014&nbsp;&nbsp;京ICP备11001537号-3</p>
			<p class="text-center">
				<script type="text/javascript">
					var cnzz_protocol = (("https:" == document.location.protocol) ? " https://"
							: " http://");
					document
							.write(unescape("%3Cspan id='cnzz_stat_icon_1253273033'%3E%3C/span%3E%3Cscript src='"
									+ cnzz_protocol
									+ "s9.cnzz.com/z_stat.php%3Fid%3D1253273033%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));
				</script>
			</p>
		</div>
	</div>
</footer>
<script>
	function showQR() {
		var imgObj = document.getElementById("QR");
		if (imgObj.style.display == "none")
			imgObj.style.display = "block";
		else
			imgObj.style.display = "none";

	}

	
</script>