function clickTypeSearch() {
	type_zone.style.display = "";
}
function clickCitySearch() {
	city_zone.style.display = "";
}
function sel_a_city(cityName) {
	var sel_city = "";
	var cities = document.getElementsByName("cityZone");
	for ( var i = 0; i < cities.length; i++) {
		if (cities[i].checked)
			sel_city += cities[i].value + ",";
	}
	city_zone.style.display = "none";
	txtSearchCity.value = sel_city;
}
function closeCityZone() {
	city_zone.style.display = "none";
}
function searchProduct() {
	// type
	var typeObj = document.getElementsByName("typeZone");
	var type_str = "";
	for ( var i = 0; i < typeObj.length; i++) {
		if (typeObj[i].checked)
			type_str += typeObj[i].value + ",";
	}
	// city
	var cityObj = document.getElementsByName("cityZone");
	var city_str = "";
	for ( var i = 0; i < cityObj.length; i++) {
		if (cityObj[i].checked)
			city_str += cityObj[i].value + ",";
	}
	// fee
	// city
	var feeObj = document.getElementsByName("sel_fee");
	var fee_str = "";
	for ( var i = 0; i < feeObj.length; i++) {
		if (feeObj[i].checked) {
			fee_str = feeObj[i].value + ",";
			break;
		}
	}

	var url = "${context}/custom/product/smart";
	$
			.post(
					url,
					{
						startDate : startDate.value,
						city : city_str,
						fee : fee_str,
						type : type_str,
					},
					function(result, status) {

						if (status == 'success') {
							// debugger;
							var ps = eval(result);
							fillPage(ps);
						}

					});
}
// 初始化时间
var now = new Date();
var year = now.getFullYear(); // 年
var month = now.getMonth() + 1; // 月
// 初始化日历
$(".form_datetime").datetimepicker({
	initialDate : year + '-' + month,
	// viewSelect : 'year',
	format : 'yyyy-mm',
	forceParse : 0,
	todayHighlight : 1,
	startView : 3,
	minView : 3,
	todayBtn : 1,
	language : 'zh-CN',
	autoclose : 1
});
function fillPage(ps)
{
	var product_str = "";
	var guideTmpl = document.getElementById("guideTmpl").innerHTML;
	var productTmpl = document
			.getElementById("productTmpl").innerHTML;
	for ( var i = 0; i < ps.length; i++) {
		// 导游列表
		var guideTmpl_str = "";

		for ( var j = 0; ps[i].tour != null
				&& j < ps[i].tour.length; j++) {
			debugger;
			var gTmpl_str = guideTmpl;
			gTmpl_str = gTmpl_str.replace("$headImg",
					ps[i].tour[j].headImg);
			gTmpl_str = gTmpl_str.replace("$id",
					ps[i].tour[j].custId);
			gTmpl_str = gTmpl_str
					.replace(
							"$nickName",
							decodeURIComponent(ps[i].tour[j].nickName));
			guideTmpl_str += gTmpl_str;
		}
		// 产品列表
		var productTmpl_str = productTmpl;
		productTmpl_str = productTmpl_str.replace('$img',
				ps[i].img);
		productTmpl_str = productTmpl_str.replace('$name',
				decodeURIComponent(ps[i].name));
		// 线路类型
		var types = "";
		for ( var j = 0; ps[i].productType != null
				&& j < ps[i].productType.length; j++) {
			types += decodeURIComponent(ps[i].productType[j].catalogName)
					+ "&nbsp;";
		}
		productTmpl_str = productTmpl_str.replace('$types',
				decodeURIComponent(types));
		// 城市
		var cities = "";
		for ( var j = 0; ps[i].cities != null
				&& j < ps[i].cities.length; j++) {
			cities += decodeURIComponent(ps[i].cities[j].cityName)
					+ "&nbsp;";
		}

		productTmpl_str = productTmpl_str.replace(
				'$cities', decodeURIComponent(cities));

		productTmpl_str = productTmpl_str.replace(
				'$detailId', ps[i].MediaID);
		productTmpl_str = productTmpl_str.replace(
				'$introduce',
				decodeURIComponent(ps[i].introduce));
		productTmpl_str = productTmpl_str.replace('$days',
				ps[i].days);
		productTmpl_str = productTmpl_str.replace(
				'$groupDate', ps[i].groupDate);
		productTmpl_str = productTmpl_str.replace(
				'$guides', guideTmpl_str);
		productTmpl_str = productTmpl_str.replace(
				'$lowPrice', ps[i].lowPrice);
		productTmpl_str = productTmpl_str.replace(
				'$productId', ps[i].productID);

		product_str += productTmpl_str;
	}
	productList.innerHTML = product_str;
}
// 默认加载全部产品

var url = "${context}/custom/product/smart";
$
		.post(
				url,
				{
					startDate : null,
					city : null,
					fee : null,
					type : null,
				},
				function(result, status) {

					if (status == 'success') {
						// debugger;
						var ps = eval(result);
						fillPage(ps);
					}

				});