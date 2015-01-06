<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function imgcheckAll() {
		var imgChecks = document.getElementsByName("imgCheck");
		for ( var i = 0; i < imgChecks.length; i++) {
			imgChecks[i].checked = checkImgAll.checked;
		}
	}
	
	function createArticle()
	{
		if (title.value.length == 0) {
			alert("请输入资料标题");
			return;
		}
		var imgChecks = document.getElementsByName("imgCheck");
		var imgUrls = "";
		for ( var i = 0; i < imgChecks.length; i++) {
			if (imgChecks[i].checked) {
				imgUrls += imgChecks[i].attributes["imgsrc"].value;
				imgUrls += ",";
			}
		}
		imgUrls = imgUrls.substring(0, imgUrls.length - 1);
		//imgUrls += "]";
		var url = "${context}/media/createArticle";
		//debugger;
		$.post(url, {
			urls : imgUrls,
			title : title.value
		}, function(result, status) {

			if (status == 'success') {
				alert(result);
			}

		});
	}

	function uploadImage() {
		if (title.value.length == 0) {
			alert("请输入标题");
			return;
		}
		var imgChecks = document.getElementsByName("imgCheck");
		var imgUrls = "";
		for ( var i = 0; i < imgChecks.length; i++) {
			if (imgChecks[i].checked) {
				imgUrls += imgChecks[i].attributes["imgsrc"].value;
				imgUrls += ",";
			}
		}
		imgUrls = imgUrls.substring(0, imgUrls.length - 1);
		//imgUrls += "]";
		var url = "${context}/media/createArticle";
		//debugger;
		$.post(url, {
			urls : imgUrls,
			title : title.value
		}, function(result, status) {

			if (status == 'success') {
				alert(result);
			}

		});

	}
	function ImgFromPage() {
		var url = "${context}/media/getImg";
		//debugger;
		$
				.post(
						url,
						{
							url : imgPage.value
						},
						function(result, status) {

							if (status == 'success') {
								//debugger;
								var imgList = eval(result);
								var img_str = "<table class='table'><tr><td><input type='checkbox' onchange='imgcheckAll()' id='checkImgAll' checked >全选 </td><td></td></tr>";
								for ( var i = 0; i < imgList.length; i++) {
									img_str += "<tr><td><input type='checkbox' name='imgCheck' imgsrc='"+imgList[i]+"' checked ></td><td>";
									img_str += "<img src='"+imgList[i]+"' style='max-width:300px;'>";
									img_str += "</td></tr>";
								}
								//debugger;
								divImgList.innerHTML = img_str + "</table>";
							}

						});
	}
</script>
<div class="row row-offcanvas row-offcanvas-right">
	<s:url var="add_media" value="/media/upload" />
	<div class="form-horizontal">
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<h3>从网页获取图片</h3>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">获取图片的网页地址</label>
			<div class="controls">
				<input type="text" class="form-control" id="imgPage"
					placeholder="输入获取图片的地址" value="" />
				<button class="btn btn-primary" onclick="javascript:ImgFromPage()">预览</button>
			</div>
		</div>
		<div class="control-group" id="divImgList"></div>
		<div class="control-group">
			<label class="control-label">媒体资料标题</label>
			<div class="controls">
				<input type="text" class="form-control" id="title" name="title"
					placeholder="资料标题" value="${title}" /> <br />标题为必填项。
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<button type="button" class="btn btn-primary"
					onclick="uploadImage()" style="display:none;">下载到服务器</button><button type="button" class="btn btn-warnning"
					onclick="createArticle()">生成新文章</button>
			</div>
		</div>
	</div>
</div>
