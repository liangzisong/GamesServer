//查询所有人员
$(function(){
	//查看所有人员
	selectUserAll();
	//查看人员信息
	$("#viewUser").on("click",viewUser);
	//点击添加人员
	$("#addUser").on("click",addUser);
	//编辑人员
	$("#editUser").on("click",editUser);
	//debugger;
	//点击(添加/编辑)保存按钮
	$("#saveUser").on("click",saveUser);
	//点击权限保存按钮
	$("#saveAuthority").on("click",saveAuthority);
	//删除人员
	$("#deleteUser").on("click",deleteUser);

	
	
	
	//清空模态框内容
	$("#cancel").on("click",function(){
		$("button[type='reset']").trigger("click");
	});
	$("#cancel1").on("click",function(){
		$("button[type='reset']").trigger("click");
	});
	
});
function selectUserAll(){
	var url = "staffManage/selectUser.oo";
	$.post(url,function(result){
		var tBody = $("#tBody");
		//清空
		tBody.empty();
		console.log(result);
		var list = result.data;
		//debugger;
		for(var i in list){
			var tr = $("<tr></tr>");
			tr.append("<td><input type='radio' name='userId' value="+list[i].id+"></td>");
			tr.append("<td>"+list[i].username+"</td>");
			tr.append("<td>"+list[i].job+"</td>");
			tr.append("<td>"+list[i].department+"</td>");
			tr.append("<td>"+list[i].hiredate+"</td>");
			tr.append('<td><button data-toggle="modal" data-target="#authorityModal" class="userAuthorityClass" userAuthorityId="'+list[i].id+'" >权限设置</button></td>');
			tBody.append(tr);
		}
		//点击权限设置        放在$(function(){});内会导致查询不到类
		$(".userAuthorityClass").on("click",userAuthority);
	});
}
//权限用户id
var userAuthorityId;
function userAuthority(){
//	debugger;
	console.log("============userAuthorityId============");
	userAuthorityId = $(this).attr("userAuthorityId");
	
	console.log(userAuthorityId);
//	  treeObj.checkNode(treeObj.getNodeByParam("id", "13", null), true, true);
	var url = "staffManage/selectOneUser.oo";
	var params = {
		"id":userAuthorityId
	};
	$.post(url,params,function(result){
		console.log(result);
		console.log(result.data.authority);
		var arr=new Array();  
		var newArr=new Array();  
		arr=result.data.authority.split(',');//注split可以用字符或字符串分割 
		var j = 0;
		for(var i=0;i<arr.length;i++){
			if(arr[i]>10){
				newArr[j];
				j++;
			}
		}
		
		for(var i=0;i<arr.length;i++){
			var treeObj = $.fn.zTree.getZTreeObj("authorityTree");
			console.log(arr[i]);
			treeObj.checkNode(treeObj.getNodeByParam("id", arr[i]+"", null), false, false);//未完成
		}
		
		
	//  var nodes = treeObj.getNodes();
		
	});

}
function viewUser(){
	debugger
	$("#UserModalH4").empty();
	$("#UserModalH4").append("人员信息");
	var params ={"id" : $("#tBody input[name='userId']:checked").val()};
	console.log("params="+JSON.stringify(params));
	if(params.id == undefined){
		alert("选择人员");
		//没有选择人员消除模态框
		$("#viewUser").attr("data-target","");
		$("#editUser").attr("data-target","");
		return null;
	}
	//选择人员后恢复模态框显示
	$("#viewUser").attr("data-target","#UserModal");
	$("#editUser").attr("data-target","#UserModal");
	var url = "staffManage/selectOneUser.oo";
	//隐藏取消、保存按钮
	$("#cancel").hide();
	$("#saveUser").hide();
	$.post(url,params,function(result){
		var data = result.data;
//		console.log("(data.hiredate).toLocaleString()="+(data.hiredate).toLocaleString());
//		console.log("new Date(data.hiredate)="+new Date(data.hiredate));
//		console.log("data.hiredate="+data.hiredate);
		$("#userName").val(data.username);
		$("#userJob").val(data.job);
		$("#userDepartment").val(data.department);
		$("#userHiredate").val(data.hiredate);
		$("#phone").val(data.phone);
		$("#status").val(data.status);
		$("#domicile").val(data.domicile);
		$("#sex").val(data.sex);
		$("#age").val(data.age);
	});
}
function addUser(){
	$("#UserModalH4").empty();
	$("#UserModalH4").append("添加人员");
	$("#saveUser").attr("UserState","");
	$("#saveUser").attr("UserState","add");
	//显示取消、保存按钮
	$("#cancel").show();
	$("#saveUser").show();
}
function editUser(){
	//先查询
	viewUser();
	$("#UserModalH4").empty();
	$("#UserModalH4").append("编辑人员");
	$("#saveUser").attr("UserState","");
	$("#saveUser").attr("UserState","edit");
	//显示取消、保存按钮
	$("#cancel").show();
	$("#saveUser").show();
}
function saveUser(){
	var url;
	debugger;
	if($("#saveUser").attr("UserState") == "add"){
		url = "staffManage/addUser.oo";
	}
	if($("#saveUser").attr("UserState") == "edit"){
		
		var id ={"id" : $("#tBody input[name='userId']:checked").val()};
		//console.log("params.id="+params.id);
		if(id.id == undefined){
			alert("选择人员");
			//没有选择人员消除模态框
			$("#editUser").attr("data-target","");
			return;
		}
		console.log("显示模态框");
		//选择人员后恢复模态框显示
		$("#editUser").attr("data-target","#UserModal");
		url = "staffManage/editUser.oo";
	}

	
	//清除
	$("#saveUser").attr("UserState","");
//	//不包含id
	var params = getUser();
//	//合并json，包含id
//	console.log(JSON.stringify(id));
//	console.log("qian")
//	//合并未成功
//	var params = extend({},[id,param]);
//	console.log("hou");
//	console.log(params);
	$.post(url,params,function(result){
		//console.log(result);
		alert(result.data);
		//查看所有人员
		selectUserAll();
	});
	
}
function saveAuthority(){
	//var nodes = $(".checkbox_true_full").next().attr("title");
	console.log("================");
	var id = userAuthorityId;
	console.log(id)
	let ztreeObj = $.fn.zTree.getZTreeObj("authorityTree");
	let list = ztreeObj.getCheckedNodes(true);
	let idArr = [];
	list.map(val => idArr.push(val.id));
	console.log(idArr)
	var url = "authorityManage/saveAuthority.oo";
	var params = {
			"id":id,
			"authority":idArr.toString()
	}
	$.post(url,params,function(result){
		selectUserAll();
		alert(result.data);
	});
	//console.log(zTreeOnClick());
	
}
//function authorityConvert(authority){
//	if(authority=="增加项目")
//}
function deleteUser(){
	//先查询
//	var select = viewUser();
//	if(select == null) return;
	var params = {"id":$("#tBody input[name='userId']:checked").val()};
	//alert(userId);
	var onDelete = confirm("确定删除人员？");
	if(onDelete == false)return;
	var url = "staffManage/deleteUser.oo";
	console.log("userId.id="+params);
	$.post(url,params,function(result){
		selectUserAll();
		alert(result.data);
	});
}
function getUser(){
	var data = {
			"id":$("#tBody input[name='userId']:checked").val(),
			"username":$("#userName").val(),
			"job":$("#userJob").val(),
			"department":$("#userDepartment").val(),
			"hiredate":$("#userHiredate").val(),
			"phone":$("#phone").val(),
			"status":parseInt($("#status").val()),
			"sex":parseInt($("#sex").val()),
			"domicile":$("#domicile").val(),
			"age":$("#age").val(),
	}
	return data;
}