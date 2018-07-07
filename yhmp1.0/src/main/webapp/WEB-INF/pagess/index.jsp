<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>元鸿管理系统</title>
    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">
    <link href="${basePath}/css/bootstrap-datepicker.min.css" rel="stylesheet" />
    <link href="${basePath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${basePath}/css/style.css" rel="stylesheet" />
    <link href="${basePath}/css/bootstrap-theme.min.css" rel="stylesheet" />
    <!--  -->
    <link href="${basePath}/assets/css/amazeui.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/core.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/menu.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/index.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/admin.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/page/typography.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/page/form.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/component.css" rel="stylesheet" />
    
    <link href="${basePath}/assets/css/amazeui.jqgrid.min.css" rel="stylesheet" />
    <link href="${basePath}/assets/css/font-awesome.css" rel="stylesheet" />
	<script type="text/javascript" src="${basePath}/js/jquery.min.js" ></script>
	<script type="text/javascript" src="${basePath}/js/scripts.js" ></script>
	<script type="text/javascript" src="${basePath}/js/bootstrap-datepicker.min.js" ></script>
	<script type="text/javascript" src="${basePath}/js/bootstrap.min.js" ></script>
	<!--  -->
	<script type="text/javascript" src="${basePath}/assets/js/amazeui.min.js" ></script>
	<script type="text/javascript" src="${basePath}/assets/js/app.js" ></script>
	<script type="text/javascript" src="${basePath}/assets/js/blockUI.js" ></script>
	<script type="text/javascript" src="${basePath}/assets/js/charts/echarts.min.js" ></script>
	<script type="text/javascript" src="${basePath}/assets/js/charts/indexChart.js" ></script>
	<script type="text/javascript" src="${basePath}/assets/js/charts/columnarChart.js" ></script>
	<script type="text/javascript" src="${basePath}/assets/js/charts/lineChart.js" ></script>
	<script type="text/javascript" src="${basePath}/assets/js/charts/pieChart.js" ></script>
</head>
	<body>
		<!-- Begin page -->
		<header class="am-topbar am-topbar-fixed-top">		
			<%@ include file="UT/up.jsp"%>
		</header>
		<!-- end page -->
		
		
		<div class="admin">
			<!--<div class="am-g">-->
		<!-- ========== Left Sidebar Start ========== -->
		<!--<div class="left side-menu am-hide-sm-only am-u-md-1 am-padding-0">
			<div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 548px;">
				<div class="sidebar-inner slimscrollleft" style="overflow: hidden; width: auto; height: 548px;">-->
                  <!-- sidebar start -->
					<%@ include file="UT/top.jsp"%>
				  <!-- sidebar end -->
    
				<!--</div>
			</div>
		</div>-->
		<!-- ========== Left Sidebar end ========== -->
		<!-- 设置左边模态框大小 -->
			<div class="content-page">
				<!-- Start content -->
				<div class="content">
					<!-- 上下滑动 -->
					<div class="am-g">
					</div>
				</div>
			</div>
		</div>
		<div class="am-u-sm-8">
			<!-- 动态加载加载 -->	
			
			<div class="content">
			
			</div>
		</div>
		<script type="text/javascript" src="${basePath}/yhmp/common/index.js" ></script>
	<!--	<div class="am-g">-->
		<!-- ============================================================== -->
		<!-- Start right Content here -->
		<!-- end right Content here -->
		<!--</div>-->
		<!-- navbar -->
		<a href="admin-offcanvas" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"><!--<i class="fa fa-bars" aria-hidden="true"></i>--></a>
						
		<!-- navbar -->
	</body>
</html>