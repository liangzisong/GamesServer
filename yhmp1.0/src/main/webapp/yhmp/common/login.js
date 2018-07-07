$(document).ready(function(){
	$("#onLogin").on("click",login);
	console.log("0");
});
$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
        //此处编写用户敲回车后的代码  
    	console.log("监听成功");
    	login();
    }  
});  
	function login(){
		console.log("1");
		var params = {
				"username":$("#inputUsername").val(),
				"password":$("#inputPassword").val()	
		}
		var url="onLogin.oo";
		console.log("2");
		$.post(url,params,function(result){
			console.log("3");
			if(result.state == 1){
				//alert("登录成功");
				//debugger;
				console.log("登陆成功")
				console.log(result);
				window.location.href="http://localhost:8080/yhmp1.0/indexUI.oo?date="+new Date().getTime()+"&username="+result.data.username+"&id="+result.data.id;
				console.log("转跳后")
				/*var data = {
					"date":new Date().getTime(),
					"id":result.data.id,
					"username":result.data.username,
					"password":result.data.password,
					"job":result.data.job,
					"department":result.data.department
				}
				$.post("http://localhost:8080/yhmp1.0/indexUI.oo",data,function(){
					console.log("转跳成功");
				});*/
			}else{
				console.log("走了异常");
				alert(result.message);
			}
			
		});
	}

