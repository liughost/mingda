(function(){
	var time = null;
	var navlist = $("#navlist");
	var box = $("#navbox");
	var navlista = navlist.find("a.boxlist");
	
	for(var i=0,j=navlista.length;i<j;i++){
		if(navlista[i].className == "now"){
			var olda = i;
		}
	}
	
	var box_show = function(hei){
		box.stop().animate({
			height:hei,
			opacity:1
		},400);
	}
	
	var box_hide = function(){
		box.stop().animate({
			height:0,
			opacity:0
		},400);
	}
	
	navlista.hover(function(){
		navlista.removeClass("now");
		$(this).addClass("now");
		clearTimeout(time);
		var index = navlist.find("a.boxlist").index($(this));
		box.find(".cont").hide().eq(index).show();
		var _height = box.find(".cont").eq(index).height()+0;
		box_show(_height)
	},function(){
		time = setTimeout(function(){	
			box.find(".cont").hide();
			box_hide();
		},50);
		navlista.removeClass("now");
		navlista.eq(olda).addClass("now");
	});
	
	box.find(".cont").hover(function(){
		var _index = box.find(".cont").index($(this));
		navlista.removeClass("now");
		navlista.eq(_index).addClass("now");
		clearTimeout(time);
		$(this).show();
		var _height = $(this).height()+0;
		box_show(_height);
	},function(){
		time = setTimeout(function(){		
			$(this).hide();
			box_hide();
		},50);
		navlista.removeClass("now");
		navlista.eq(olda).addClass("now");
	});
})();