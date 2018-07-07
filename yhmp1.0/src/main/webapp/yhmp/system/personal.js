//点击个人信息
$("#iInformation").on("click",iInformation);
//个人信息内点击保存
$("#upInformation").on("click",upInformation);
//修改密码保存
$("#uppasswordSBM").on("click",uppasswordSBM);
//点击退出
$("#userExit").on("click",userExit);
$(document).ready(function(){
	//设置字体隐藏
	$("#buyizi").hide();
	//密码输入不同检测
	$("[id$=newPassword]").blur(function(){

		if($("#newPassword").val() != $("#1newPassword").val()){
			$("#buyizi").show(500);
			$("#uppasswordSBM").attr("disabled",true);
		}else{
			$("#buyizi").hide(500);
			$("#uppasswordSBM").attr("disabled",false);
		}
	});

});
//保存原username
var beforeName;

function iInformation(){
	var id = $("#iInformation").attr("userid");
	console.log("id=  "+id);
	var url = "personal/onPersonal.oo";
	$.post(url,{"id":id},function(result){
		console.log("回调函数以走");
		//console.log(result);
		beforeName = result.data.username;
		$("#username").val(result.data.username);
		$("#department").val(result.data.department);
		$("#job").val(result.data.job);
		$("#hiredate").val((new Date(result.data.hiredate)).toLocaleString());
	});
}
function upInformation(){
	var id = $("#iInformation").attr("userid");
	var username = $("#username").val();
	if(username != beforeName){
		var url="personal/personal.oo";
		$.post(url,{"id":id,"username":username},function(result){
			//console.log("回调函数已走，保存成功");
			//提示
			alert(result.data);
		});
	}
}
function uppasswordSBM(){
	console.log("点击修改密码");
	if($("#newPassword").val() == "" || $("#newPassword").val() == null){
		console.log("进入修改密码");
		alert("密码不能为空");
		return;
	}
	var params = {
			"id":$("#iInformation").attr("userid"),
			"password":$("#password").val(),
			"newPassword":$("#newPassword").val()
	}
	var url = "personal/uppassword.oo";
	$.post(url,params,function(result){
		//提示
		alert(result.data);
	})
}
//退出账号
function userExit(){
	console.log("退出");
	window.location.href="http://localhost:8080/yhmp1.0/login.oo";
}