$(function(){
	/*-----------------------banner Str-----------------------*/
	$(".bannersy-bg li").fadeOut(0).eq(0).fadeIn(0);
	
	var i=0;
	//运动	
	BannerMove = function(){
		if($(".bannersy-bg li").length > 1){
			if($(".bannersy-bg li").length > (i+1)){
				$(".bannersy-bg li").eq(i).fadeOut(2000).next("li").fadeIn(2000);
				i++;
			}else{
				$(".bannersy-bg li").eq(i).fadeOut(2000).siblings("li").eq(0).fadeIn(2000);
				i = 0;
			};
		};
	};
	//定时器
	timer = setInterval(BannerMove,5000);
		
	//鼠标移上停止移出开始
	$(".bannersy span.prev, .bannersy span.next").hover(
		function(){
			clearInterval(timer);
		},function(){
			timer = setInterval(BannerMove,5000);
	});

	//点击右侧
	$(".banner span.next").click(function(){
		if($(".bannersy-bg li").length > (i+1)){
		$(".bannersy-bg li").eq(i).fadeOut(500).next("li").fadeIn(500);
		i++;
		}else{
		$(".bannersy-bg li").eq(i).fadeOut(500).siblings("li").eq(0).fadeIn(500);
		i = 0;
		};
	});
	
	//点击左侧
	$(".bannersy span.prev").click(function(){
		if(0 < i){
		$(".bannersy-bg li").eq(i).fadeOut(500).prev("li").fadeIn(500);
		i--;
		}else{
		$(".bannersy-bg li").eq(0).fadeOut(500).siblings("li").last().fadeIn(500);
		i = $(".bannersy-bg li").length - 1;
		};
	});
	/*-----------------------banner End-----------------------*/
});