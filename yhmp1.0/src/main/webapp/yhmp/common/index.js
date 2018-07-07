//预加载，截取字符
$(document).ready(function(){
	//获取url
	function GetRequest() {   
		   var url = location.search; //获取url中"?"符后的字串   
		   var theRequest = new Object();   
		   if (url.indexOf("?") != -1) {   
		      var str = url.substr(1);   
		      strs = str.split("&");   
		      for(var i = 0; i < strs.length; i ++) {   
		         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
		      }   
		   }   
		   return theRequest;   
		}   
	var theRequest = GetRequest();
	$("#daoshanjiao").before(theRequest.username);
	$("#iInformation").attr("userid",theRequest.id)
});
//项目管理
//项目查看
$('#Information-id').click(function(){
		console.log("点击了");
		//Information();
		var url="information/listUI.oo?t="+Math.random(1000);
		//在类选择器.content的位置异步加载对应的url
		$(".content").load(url);
});
$('#index').click(function(){
	console.log("点击主页")
	//刷新当前页，相当于重新加载
	window.location.reload();
});
$('#load-declare-id').click(function(){
	console.log("点击了");
	var url="declare/listUI.oo?t="+Math.random(1000);
	//在类选择器.content的位置异步加载对应的url
	$(".content").load(url);
});
$('#load-schedule-id').click(function(){
	var url="schedule/listUI.oo?t="+Math.random(1000);
	//在类选择器.content的位置异步加载对应的url
	$(".content").load(url);
});
//系统管理
//点击人员管理
$('#load-staffManage-id').click(function(){
	var url="staffManage/listUI.oo?t="+Math.random(1000);
	//在类选择器.content的位置异步加载对应的url
	$(".content").load(url);
});
//点击权限管理
$('#load-authorityManage-id').click(function(){
	var url="authorityManage/listUI.oo?t="+Math.random(1000);
	//在类选择器.content的位置异步加载对应的url
	$(".content").load(url);
});