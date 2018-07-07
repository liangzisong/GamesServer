<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<h3>
					个人信息
				</h3>
				<input type="button" value="查看" id="viewUser" data-toggle="modal" data-target="#UserModal"> &nbsp &nbsp 
				<input type="button" value="添加" id="addUser" data-toggle="modal" data-target="#UserModal"> &nbsp &nbsp 
				<input type="button" value="编辑" id="editUser" data-toggle="modal" data-target="#UserModal"> &nbsp &nbsp 
				<input type="button" value="删除" id="deleteUser"> &nbsp &nbsp 
				<br/>
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>
								选择
							</th>
							<th>
								名称
							</th>
							<th>
								职位
							</th>
							<th>
								部门
							</th>
							<th>
								入职时间
							</th>
							<th>
								权限设置
							</th>
						</tr>
					</thead>
					<!-- 动态填充 -->
					<tbody id="tBody">
						
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
		<!-- 权限管理模态框（Modal） -->
	<div class="modal fade" id="authorityModal" tabindex="-1" role="dialog" aria-labelledby="authorityModalH4" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="cancelAuthority1" data-dismiss="modal" aria-hidden="true" >
						&times;
					</button>
					<h4 class="modal-title">
						权限管理
					</h4>
				</div>
				<div class="modal-body">
				<!-- 开始 -->
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="span2">
							</div>
							<div class="span8">
								<div class="content_wrap">
									<div class="authorityBackground left">
										<ul id="authorityTree" class="ztree"></ul>
									</div>
								</div>
								<div class="span2">
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" id="cancelAuthority" >
							取消
						</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveAuthority" >
							保存
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
	</div>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="UserModal" tabindex="-1" role="dialog" aria-labelledby="UserModalH4" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="cancel1" >
						&times;
					</button>
					<h4 class="modal-title" id=UserModalH4>
						个人信息
					</h4>
				</div>
				<div class="modal-body">
				<!-- 开始 -->
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="span2">
							</div>
							<div class="span8">
								<form>
									<span>
										账号：
										<input type="text" class="form-control" id="userName" ></input>
									</span>
									<br>
									<span>
										职位：
										<input type="text" class="form-control" id="userJob"></input>
									</span>
									<br>
									<span>
										部门：
										<input type="text" class="form-control" id="userDepartment"></input>
									</span>
									<br>
									<span>
										入职时间：
										<input type="date" class="form-control required datepickers" id="userHiredate" name="finishTime1" >
									</span>
									<br>
									<span>
										个人手机：
										<input type="text" class="form-control" id="phone"></input>
									</span>
									<br>
									<span>
										入职状态：
										<select class="form-control" id="status" >
											<option value="0">实习</option>
											<option value="1">在职</option>
											<option value="2">离职</option>
										</select>
									</span>
									<br>
									<span>
										居住地：
										<input type="text" class="form-control" id="domicile" ></input>
									</span>
									<br>
									<span>
										性别：
										<select class="form-control" id="sex" >
											<option value="1">男</option>
											<option value="0">女</option>
										</select>
									</span>
									<br>
									<span>
										年龄：
										<input type="text" class="form-control" id="age" ></input>
									</span>
									<br>
									<button type="reset" style="display: none;" ></button>
								</form>
							</div>
							<div class="span2">
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="cancel" >
						取消
					</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveUser" >
						保存
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- tree追加数据 -->
<script type="text/javascript" src="${basePath }/yhmp/system/StffManage.js" ></script>
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
			},
			/* callback: {
				onClick: zTreeOnClick
			} */
			view:{
				showLine: false
			}
		};

		var zNodes =[
			{ id:0, pId:0, name:"山东元鸿", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/1_open.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/1_close.png"},
			{ id:1, pId:0, name:"项目申报", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:11, pId:1, name:"增加项目", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png", abc:"avc"},
			
			{ id:2, pId:0, name:"项目信息", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:12, pId:2, name:"项目编辑", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:13, pId:2, name:"项目删除", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:14, pId:2, name:"项目计划/进度", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:3, pId:0, name:"已完成项目", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:15, pId:3, name:"项目审核", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:16, pId:3, name:"项目存档", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:17, pId:3, name:"项目评价", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:18, pId:3, name:"项目总结", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:19, pId:3, name:"项目计分", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:4, pId:0, name:"已存档项目", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:20, pId:4, name:"存档项目查看", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:5, pId:0, name:"统计日志", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:21, pId:5, name:"统计日志查看", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			
			{ id:6, pId:0, name:"系统管理", open:true, iconOpen:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png", iconClose:"${basePath}/css/zTree/zTreeStyle/img/diy/3.png"},
			{ id:22, pId:6, name:"人员管理", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:23, pId:6, name:"权限管理", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
			{ id:24, pId:6, name:"登陆日志", icon:"${basePath}/css/zTree/zTreeStyle/img/diy/2.png"},
		];
		
		
		$(document).ready(function(){
			$.fn.zTree.init($("#authorityTree"), setting, zNodes);
		});
		//-->
	</SCRIPT>
</body>
</html>