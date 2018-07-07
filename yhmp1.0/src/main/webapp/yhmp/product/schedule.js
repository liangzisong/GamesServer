$('#project').click(function(){
	var url="schedule/listUI_1.oo?t="+Math.random(1000);
	//在类选择器.content的位置异步加载对应的url
	$(".content").load(url);
});