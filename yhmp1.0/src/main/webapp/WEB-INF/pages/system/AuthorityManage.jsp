<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
<%-- <link href="${basePath}/css/zTree/demo.css" rel="stylesheet" /> --%>
	<SCRIPT type="text/javascript">
		//<!--
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =[
			{ id:0, pId:0, name:"山东元鸿", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/1_open.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/1_close.png"},
			{ id:1, pId:0, name:"项目申报", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:11, pId:1, name:"增加项目", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:2, pId:0, name:"项目信息", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:11, pId:2, name:"项目编辑", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:12, pId:2, name:"项目删除", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:13, pId:2, name:"项目计划/进度", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:3, pId:0, name:"已完成项目", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:11, pId:3, name:"项目审核", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:12, pId:3, name:"项目存档", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:13, pId:3, name:"项目评价", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:13, pId:3, name:"项目总结", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:13, pId:3, name:"项目计分", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:4, pId:0, name:"已存档项目", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:11, pId:4, name:"查看", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:5, pId:0, name:"统计日志", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:11, pId:5, name:"查看", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:6, pId:0, name:"系统管理", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:11, pId:6, name:"人员管理", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:12, pId:6, name:"登陆日志", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
		];
		
		/* var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = type; 
			showCode('setting.check.chkboxType = { "Y" : "s", "N" : "ps" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		} */
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			//setCheck();
			//setting.check.chkboxType = { "Y" : "s", "N" : "ps" };
			//setCheck();
			/* $("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck); */
		});
		//-->
	</SCRIPT>
</head>
<body>
<h1>Checkbox 勾选操作</h1>
<h6>[ 文件路径: excheck/checkbox.html ]</h6>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">
		<ul class="info">
			<li class="title"><h2>1、setting 配置信息说明</h2>
				<ul class="list">
				<li class="highlight_red">使用 checkbox，必须设置 setting.check 中的各个属性，详细请参见 API 文档中的相关内容</li>
				<li><p>父子关联关系：<br/>
						被勾选时：<input type="checkbox" id="py" class="checkbox first" checked /><span>关联父</span>
						<input type="checkbox" id="sy" class="checkbox first" checked /><span>关联子</span><br/>
						取消勾选时：<input type="checkbox" id="pn" class="checkbox first" checked /><span>关联父</span>
						<input type="checkbox" id="sn" class="checkbox first" checked /><span>关联子</span><br/>
						<ul id="code" class="log" style="height:20px;"></ul></p>
				</li>
				</ul>
			</li>
			<li class="title"><h2>2、treeNode 节点数据说明</h2>
				<ul class="list">
				<li class="highlight_red">1)、如果需要初始化默认节点被勾选，请设置 treeNode.checked 属性，详细请参见 API 文档中的相关内容</li>
				<li class="highlight_red">2)、如果某节点禁用 checkbox，请设置 treeNode.chkDisabled 属性，详细请参见 API 文档中的相关内容 和 'chkDisabled 演示'</li>
				<li class="highlight_red">3)、如果某节点不显示 checkbox，请设置 treeNode.nocheck 属性，详细请参见 API 文档中的相关内容 和 'nocheck 演示'</li>
				<li class="highlight_red">4)、如果更换 checked 属性，请参考 API 文档中 setting.data.key.checked 的详细说明</li>
				<li>5)、其他请参考 API 文档中 treeNode.checkedOld / getCheckStatus / check_Child_State / check_Focus 的详细说明</li>
				</ul>
			</li>
		</ul>
	</div>
</div>
</body>
</html>