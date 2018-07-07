//预加载
$(function(){
	//查看所有项目
	selectProjectAll();
	//点击查看
	$("#viewProject").on("click",viewProject);
	//编辑项目
	$("#editProject").on("click",editProject);
	//点击编辑保存按钮
	$("#saveProject").on("click",saveProject);
	//点击删除项目
	$("#deleteProject").on("click",deleteProject);
	//点击搜索
	$("#search").on("click",searchProject);
	//清空模态框内容
	$("#cancel").on("click",function(){
		$("button[type='reset']").trigger("click");
	});
	$("#cancel1").on("click",function(){
		$("button[type='reset']").trigger("click");
	});
});
//查询所有项目
function selectProjectAll(){
	console.log("进入单js");
	var url = "information/selectInformation.oo";
	$.post(url,function(result){
		console.log("====回调函数单调====");
		var list = result.data;
		fillingProject(list);
	});
}
//将所有项目输出到浏览器上
function fillingProject(list){
	var tBody = $("#tBody");
	//清空
	tBody.empty();
	console.log(list);
	console.log(list[1]);
	console.log(list[1].declareProject);
	for(var i in list){
		console.log("list.length="+list.length)
		//console.log("list.length()="+list.length())
		console.log("list[i].id="+list[i].id);
		var tr = $("<tr></tr>");
		tr.append("<td><input type='radio' name='projectId' value="+list[i].id+"></td>");
		tr.append('<td>'+list[i].declareProject+'</td>');
		tr.append('<td>'+list[i].declareScale+'</td>');
		tr.append('<td>'+(list[i].contract==1 ? '以签订':'未签订')+'</td>');
		tr.append('<td>'+list[i].finishTime+'</td>');
		tr.append('<td>'+(list[i].bid==1 ? '需要':(list[i].bid==2 ? '不需要':'暂不确定'))+'</td>');
		tr.append('<td>'+list[i].responsibilityName+'</td>');
		tBody.append(tr);
	}

}

//单个项目详情
function viewProject(){
	//debugger
	$("#projectModalH4").empty();
	$("#projectModalH4").append("项目信息");
	
	var params ={"id" : $("#tBody input[name='projectId']:checked").val()};
	console.log("params="+JSON.stringify(params));
	if(params.id == undefined){
		alert("选择项目");
		//没有选择项目消除模态框
		$("#viewProject").attr("data-target","");
		$("#editProject").attr("data-target","");
		$("#projectPlan").attr("data-target","");
		return "无项目";
	}
	//选择项目后恢复模态框显示

	$("#projectPlan").attr("data-target","#planModal");
	$("#editProject").attr("data-target","#projectModal");
	$("#viewProject").attr("data-target","#projectModal");
	var url = "information/selectInformation.oo";
	//隐藏取消、保存按钮
	$("#cancelProject").hide();
	$("#saveProject").hide();
	console.log("params="+params);  
	console.log("JSON.stringify(params))="+JSON.stringify(params));  
	$.post(url,params,function(result){
		var data = result.data;
//		console.log("(data.hiredate).toLocaleString()="+(data.hiredate).toLocaleString());
//		console.log("new Date(data.hiredate)="+new Date(data.hiredate));
//		console.log("data.hiredate="+data.hiredate);
		$("#declareProject").val(data.declareProject);
		$("#declareNumber").val(data.declareNumber);
		$("#declareFraction").val(data.declareFraction);
		$("#declareScale").val(data.declareScale);
		$("#declareArrange").val(data.declareArrange);
		$("#declareAssess").val(data.declareAssess);
		$("#declareSurveying").val(data.declareSurveying);
		$("#declarePlan").val(data.declarePlan);
		$("#contract").val(data.contract);
		$("#declareCompany").val(data.declareCompany);
		$("#declareName").val(data.declareName);
		$("#declarePhone").val(data.declarePhone);
		$("#finishTime").val(data.finishTime);
		$("#quality").val(data.quality);
		$("#bid").val(data.bid);
		$("#responsibilityName").val(data.responsibilityName);
		$("#responsibilityPhone").val(data.responsibilityPhone);
		//项目计划内两个输入框
		$("#projectName_1").val(data.declareProject);
		$("#projectLeadingOffcial_1").val(data.responsibilityName);
	});
}
function editProject(){
	
	console.log("editUser()");
	//先查询
//	if(== null){
//		
//		return;
//	}
	viewProject();
	
	$("#projectModalH4").empty();
	$("#projectModalH4").append("编辑项目");
	$("#saveProject").attr("projectState","");
	$("#saveProject").attr("projectState","edit");
	//显示取消、保存按钮
	$("#cancelProject").show();
	$("#saveProject").show();
}
function saveProject(){
	//debugger;
	if($("#saveProject").attr("projectState") == "edit"){
		
		var id ={"id" : $("#tBody input[name='projectId']:checked").val()};
		//console.log("params.id="+params.id);
		if(id.id == undefined){
			alert("选择项目");
			//没有选择人员消除模态框
			$("#editProject").attr("data-target","");
			return;
		}
		console.log("显示模态框");
		//选择人员后恢复模态框显示
		$("#editProject").attr("data-target","#projectModal");
		var url = "information/editProject.oo";
	}
	//清除
	$("#saveProject").attr("projectState","");
//	//不包含id
	var params = getPorjectPlan();
	console.log("params="+JSON.stringify(params));
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
		searchProject();
	});
	
}
//删除项目
function deleteProject(){
	//debugger
	//先查询
	var select = viewProject();
	if(select == "无项目") return;
	var params = {"id":$("#tBody input[name='projectId']:checked").val()};
	//alert(userId);
	var onDelete = confirm("确定删除项目？");
	if(onDelete == false)return;
	var url = "information/deleteProject.oo";
	console.log("projectId.id="+params);
	$.post(url,params,function(result){
		//删除之后重新查询
		searchProject();
		alert(result.data);
	});
}
//搜索项目
function searchProject(){
	var params ={
			"declareProject":$("#inputSearch").val()
	}
	var url = "information/selectInformation.oo";
	$.post(url,params,function(result){
		//debugger;
		console.log(result)
		console.log(result.data.length)
		if(result.data.length == 0){
			alert("未查询到");
			return;
		}
		fillingProject(result.data);
	});
}
//function loadPlan(){
//	debugger;
//	console.log("loadPlan()");
//	//隐藏模态框
////	$("#tBody").slideToggle();
////	$("#cancelProject").trigger("click");
////	$("#projectModal").modal("hide");
////	location.reload();
//	$("#load-plan-id").trigger("click");
////	var url="plan/listUI.oo?t="+Math.random(1000);
//////	//在类选择器.content的位置异步加载对应的url
////	$("#asdf").load(url);
//	console.log("完成");
//}
function getPorjectPlan(){
	var data = {
			"id":$("#tBody input[name='projectId']:checked").val(),
			"declareProject":$("#declareProject").val(),
			"declareNumber":$("#declareNumber").val(),
			"declareFraction":$("#declareFraction").val(),
			"declareScale":$("#declareScale").val(),
			"declareArrange":$("#declareArrange").val(),
			"declareAssess":$("#declareAssess").val(),
			"declareSurveying":parseInt($("#declareSurveying").val()),
			"declarePlan":parseInt($("#declarePlan").val()),
			"contract":$("#contract").val(),
			"declareCompany":$("#declareCompany").val(),
			"declareName":$("#declareName").val(),
			"declarePhone":$("#declarePhone").val(),
			"finishTime":$("#finishTime").val(),
			"quality":$("#quality").val(),
			"bid":$("#Bid").val(),
			"responsibilityName":$("#responsibilityName").val(),
			"responsibilityPhone":$("#responsibilityPhone").val(),
	}
	return data;
}