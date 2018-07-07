<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html >
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>元鸿项目测绘管理系统</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">
    <link href="${basePath}/css/bootstrap-datepicker.min.css" rel="stylesheet" />
    <link href="${basePath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${basePath}/css/style.css" rel="stylesheet" />
    <link href="${basePath}/css/bootstrap-theme.min.css" rel="stylesheet" />
    <link href="${basePath}/css/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
	<script type="text/javascript" src="${basePath}/js/jquery.min.js" ></script>
	<script type="text/javascript" src="${basePath}/js/zTree/jquery.ztree.core.js" ></script>
	<script type="text/javascript" src="${basePath}/js/zTree/jquery.ztree.excheck.js" ></script>
	<script type="text/javascript" src="${basePath}/js/scripts.js" ></script>
	<script type="text/javascript" src="${basePath}/js/bootstrap-datepicker.min.js" ></script>
	<script type="text/javascript" src="${basePath}/js/bootstrap.min.js" ></script>
	
	<style>
	</style>
  </head>
  <body>

    <div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-4">
				</div>
				<div class="col-md-8">
					<h2 class="text-primary">
						欢迎登陆元鸿测绘管理系统
					</h2>
				</div>
			</div>
			<nav class="navbar navbar-default" role="navigation">
				<div class="navbar-header">
					 
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						 <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
					</button> <a class="navbar-brand" id="index" href="#" >主页</a>
				</div>
				
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="dropdown">
							<a data-toggle="dropdown" class="dropdown-toggle" id="load-declare-id" href="#">项目申报</a>
						</li>
						<li class="dropdown">
							<a data-toggle="dropdown" class="dropdown-toggle" id="Information-id" href="#">项目信息</a>
								<!-- <li>
									<a id="load-plan-id">项目计划</a>
								</li>
								<li>
									<a id="load-schedule-id">项目进度</a>
								</li> -->
						</li>
						<li class="dropdown">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">已完成项目<strong class="caret"></strong></a>
							<ul class="dropdown-menu">
								<li>
									<a href="#">项目审核</a>
								</li>
								<li>
									<a href="#">项目存档</a>
								</li>
								<li>
									<a href="#">项目评价</a>
								</li>
								<li>
									<a href="#">项目总结</a>
								</li>
								<li>
									<a href="#">项目计分</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">已存档项目</a>
						</li>
						<li>
							<a>统计日志</a>
						</li>
						<li class="dropdown">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">系统管理<strong class="caret"></strong></a>
							<ul class="dropdown-menu">
								<li>
									<a id="load-staffManage-id">人员管理</a>
								</li>
								<li>
									<a id="load-authorityManage-id">权限管理</a>
								</li>
								<li>
									<a href="#">登陆日志</a>
								</li>
							</ul>
						</li>
					</ul>
					<ul class="nav pull-right" >
						<li class="dropdown">
							 <a data-toggle="dropdown" class="dropdown-toggle" href="#">欢迎：<strong id="daoshanjiao" class="caret"></strong></a>
							<ul class="dropdown-menu">
								<li>
									<!-- 按钮触发模态框 -->
									<a data-toggle="modal" data-target="#personalInformation" id="iInformation">
										个人信息
									</a>
								</li>
								<li>
									<a data-toggle="modal" data-target="#updatePassword" id="upPassword" >
										修改密码
									</a>
								</li>
								<li class="divider">
								</li>
								<li>
									<a id="userExit">退出</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="updatePassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" >
					修改密码
				</h4>
			</div>
			<div class="modal-body">
			<!-- 开始 -->
			<form class="form-horizontal">
				<span>
					原密码：
					<input type="password" class="form-control" id="password" ></input>
				</span>
				<span>
					新密码：
					<input type="password" class="form-control" id="newPassword" ></input>
				</span>
				<span>
					再次输入：
					<input type="password" class="form-control" id="1newPassword" ></input>
				</span>
				<br>
				<p id="buyizi" style="color: red;">两次输入新密码不一致！</p>
			</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" id="uppasswordSBM">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="personalInformation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" >
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
								<fieldset>
									<legend>表单项</legend>
										<span>
											账号：
											<input type="text" class="form-control" id="username" ></input>
										</span>
										<br>
										<span>
											部门：
											<input type="text" class="form-control" id="department" disabled></input>
										</span>
										<br>
										<span>
											职务：
											<input type="text" class="form-control" id="job" disabled></input>
										</span>
										<br>
										<span>
											入职时间：
											<input type="text" class="form-control" id="hiredate" disabled></input>
										</span>
										<br>
								</fieldset>
							</form>
						</div>
						<div class="span2">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" id="upInformation" data-dismiss="modal">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
			</nav>
			<!-- 此处异步加载其他功能窗口 -->
			<div class="content">
						<h2>
							天鸿简介
						</h2>
						<p>
							This is a template for a simple marketing or informational website. 
							It includes a large callout called the hero unit and three supporting pieces
							 of content. Use it as a starting point to create something more unique.
						</p>
						
					</div>
			</div>
</div>
</div>
	<!-- 公共方法 -->
    <script type="text/javascript" src="${basePath}/yhmp/common/common.js" ></script>
	<!-- 点击按钮转跳 -->
    <script type="text/javascript" src="${basePath}/yhmp/common/index.js" ></script>
    <!-- 个人中心 -->
    <script type="text/javascript" src="${basePath}/yhmp/system/personal.js" ></script>
  </body>
</html>