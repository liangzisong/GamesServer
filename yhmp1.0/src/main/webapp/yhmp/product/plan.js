$(function(){
	//项目计划转跳
	$('#projectPlan').on("click",projectPlan);
	//保存项目计划
	$("#addPlan").on("click",addPrjectPlan);
	//变换到项目进度
//	$("#plan_1").on("click",varyPrjectSchedule);
	//点击项目进度
//	$(".projectPlanClass").on("click",projectPlanClass);
	//重置
	$("#cleanPlan").on("click",function(){
		planReset();
	});
	//重置
	$(".modal-header .close").on("click",function(){
		planReset();
	});
});
var j = 0;
//未完成
function onProjectPlan(i){
	//debugger
	//alert("点击了");
//	var id = $(".projectPlan123132").attr("id");
//	var lenth = id.lastIndexOf("_");
//	var newId = id.substring(lenth+1);
//	console.log(newId);
//	console.log("点击项目进度");
	j++;
	//debugger
	if(j==1){
		$("#truePlan_"+i).show();
		$("#falsePlan_"+i).hide();
		$(".form-control_plan_"+i).val("查看计划进度");
	}else if(j==2){
		$("#truePlan_"+i).hide();
		$("#falsePlan_"+i).show();
		$(".form-control_plan_"+i).val("查看实际进度");
	}
	if(j==2)j=0;
}
function varyPrjectSchedule(){
	
}
/*function MouseOver(obj){
	debugger
	console.log("MouseOver_________"+obj);
	$("#plan_"+obj).hide();
	$("#schedule_"+obj).show();
	
}*/
/*function Mouseout(obj){
	console.log("Mouseout_________"+obj);
	$("#plan_"+obj).show();
	$("#schedule_"+obj).hide();
}*/
function projectPlan(){
	//debugger
	//增加上面两条项目名称、项目负责人信息
	viewProject();
	var params ={"id" : $("#tBody input[name='projectId']:checked").val()};
	var url="plan/selectPrjectPlan.oo";
	$.post(url,params,function(result){
		debugger
		var list = result.data;
		for(var j=0;j<list.length;j++){
			console.log(j);
			addPlan();
			var z = j+1;
			$("#planSchedule_"+z).val(list[j].planSchedule);
			$("#projectContent_"+z).val(list[j].projectContent);
			$("#finishTime_"+z).val(list[j].finishTime);
			$("#personLiable_"+z).val(list[j].personLiable);
		}
		//放完之后多一条，删掉
		deletePlan();
	});
}
function addPrjectPlan(){
	console.log("===obtainTable2()===")
	//debugger;
//	viewProject();
	//获取所有计划内容
	var params = getEditFormDataPlan();
	//清空
	var deleteUrl = "plan/deletePrjectPlan.oo";
	$.post(deleteUrl,params,function(result){
		console.log(result.state);
		if(result.state==1){
			//alert(result.data);
		}else{		//http://10.10.10.172:6080/arcgis/rest/services
			alert("提交失败");
		}
	});
	//添加
	var addUrl = "plan/addPrjectPlan.oo";
	
	//输入敏感退出
	if("exception" == params)return;
	console.log("params="+JSON.stringify(params))
//	if(params == "exception"){
		//alert("请检查您的输入，不能包含##或&&");
//		return;
//	}

	console.log("===========继续进行了===========");
	$.post(addUrl,params,function(result){
		console.log(result.state);
		if(result.state==1){
			alert(result.data);
		}else{		//http://10.10.10.172:6080/arcgis/rest/services
			alert("提交失败");
		}
	});
	//重置
	planReset();
}
//重置
function planReset(){
	$("button[type='reset']").trigger("click");
	for(var j=2;j<=i;j++){
//		console.log("j="+j)
		$(".form-group_"+j).remove();
	}
	i=1;
}

//删除阶段计划
function deletePlan(){
	console.log("删除");
	if(i==1) return;
	$(".form-group_"+i).remove();
	i--;
}
//第几阶段计划
var i=1;

//获取用户填入的数据
function getEditFormDataPlan(){
	var format = "";
		var projectId =$("#tBody input[name='projectId']:checked").val();
//		字符串转数字
//		parseInt($("#tBody input[name='projectId']:checked").val());
		var name = $("#projectName_1").val();
		var leadingOffcial = $("#projectLeadingOffcial_1").val();
		var data="";
//		var arr = new Array();
	for(var v=1;v<=i;v++){
		var planSchedule = $("#planSchedule_"+v).val();
		var projectContent = $("#projectContent_"+v).val();
		var finishTime = $("#finishTime_"+v).val();
		var personLiable = $("#personLiable_"+v).val();
		if(-1 != projectContent.indexOf("|")
				||-1 != finishTime.indexOf("|")
				||-1 != personLiable.indexOf("|")
				||-1 != projectContent.indexOf("^")
				||-1 != finishTime.indexOf("^")
				||-1 != personLiable.indexOf("^")){
			alert("输入不允许包含^或者|");
//			$("#planModal").attr("id","");
//			$("#projectPlan").onclick();
			return "exception";
		}
		if(isNaN(planSchedule)){
			alert("在计划进度内输入数字")
			return "exception";
		}
		if(planSchedule>=100){
			planSchedule = 100;
		}
//		$("#planModal").attr("id","planModal");
		data += v+"^"+planSchedule+"^"+projectContent+"^"+finishTime+"^"+personLiable+"|";
//		arr.push(v+"");
//		arr.push(one);
//		arr.push(two);
//		arr.push(three);
//		var a = {
//			plan:v,
//			content:one,
//			finishTime:two,
//			member:three
//		}
		//formats.push(format);
	}
	//截取最后一个|
	var len = data.length-1;
	plan = data.substr(0,len);
	console.log("plan="+plan)
	var json = {
			"id":projectId,
			"declareProject":name,
			"responsibilityName":leadingOffcial,
			"periodicPlan":plan
	}
	return json;
}

//添加阶段计划
function addPlan(){
	console.log("点击添加阶段计划");
	i++; 
	var addFrom = $("#addFrom");
	var newAdd ='<div class="form-group_'+i+'">'+
					'<h4 >&nbsp; &nbsp;第'+i+'阶段计划</h4>'+
					'<!--<h4 onmouseover="MouseOver('+i+')" id="plan_'+i+'" >&nbsp; &nbsp;第'+i+'阶段计划</h4>-->'+
					'<!--<h4 onmouseout="Mouseout('+i+')" hidden id="schedule_'+i+'" >&nbsp; &nbsp;项目进度</h4>-->'+
					'<div id="falsePlan_'+i+'" >'+
						'<span>'+
						'计划进度:'+
							'<div class="input-group" >'+
								'<input type="text" class="form-control" id="planSchedule_'+i+'" placeholder="请输入计划进度" >'+
								'<div class="input-group-addon" >%</div>'+
							'</div>'+
						'</span>'+
						'<br>'+
						'<span>'+
							'计划内容:'+
							'<input type="text" class="form-control" id="projectContent_'+i+'" ></input>'+
						'</span>'+
						'<br>'+
						'<span>'+
							'完成时间:'+
							'<input type="date" class="form-control required datepickers" id="finishTime_'+i+'" name="finishTime_'+i+'" >'+
						'</span>'+
						'<br>'+
						'<span>'+
							'项目成员:'+
							'<input type="text" class="form-control" id="personLiable_'+i+'" ></input>'+
						'</span>'+
						'<br>'+
					'</div>'+
					'<div id="truePlan_'+i+'"  hidden >'+
						'<span>'+
						'计划进度:'+
							'<span class="input-group" >'+
								'<input type="text" class="form-control" placeholder="请输入计划进度" >'+
								'<span class="input-group-addon" >%</span>'+
							'</span>'+
						'</span>'+
						'<br>'+
						'<span>'+
							'实际工作：'+
							'<input type="text" class="form-control" ></input>'+
						'</span>'+
						'<br>'+
						'<span>'+
							'实际完成时间:'+
							'<input type="date" class="form-control required datepickers" >'+
						'</span>'+
						'<br>'+
						'<span>'+
							'调整工期理由:'+
							'<input type="text" class="form-control" ></input>'+
						'</span>'+
						'<br>'+
					'</div>'+
					'<span>'+
					'<input type="button" class="form-control_plan_'+i+'" onclick="onProjectPlan('+i+');" value="查看实际进度" ></input>'+
				'</span>'+
				'</div>'; 
				//console.log(newAdd);
				//console.log(a)
	console.log(newAdd);
	addFrom.append(newAdd);
}	
