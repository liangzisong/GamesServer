<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  <div class="admin-sidebar am-offcanvas  am-padding-0" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
    	<!-- User -->
		<div class="user-box am-hide-sm-only">
                     <div class="user-img">
                         <img src="assets/img/avatar-1.jpg" alt="user-img" title="Mat Helme" class="img-circle img-thumbnail img-responsive">
                         <div class="user-status offline"><i class="am-icon-dot-circle-o" aria-hidden="true"></i></div>
                     </div>
                     <h5><a href="#">Mat Helme</a> </h5>
                     <ul class="list-inline">
                         <li>
                             <a href="#">
                                 <i class="fa fa-cog" aria-hidden="true"></i>
                             </a>
                         </li>

                         <li>
                             <a href="#" class="text-custom">
                                 <i class="fa fa-cog" aria-hidden="true"></i>
                             </a>
                         </li>
                     </ul>
                 </div>
                 <!-- End User -->
         
		 <ul class="am-list admin-sidebar-list">
		    <li><a href="index.html"><span class="am-icon-home"></span> 首页</a></li>
		    <li class="admin-parent">
		      <a class="am-cf" data-am-collapse="{target: '#collapse-nav1'}"><span class="am-icon-table"></span> 项目管理 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
		      <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav1">
		        <li><a class="am-cf" id="Information-id"> 项目信息</span></a></li>
		        <li><a id="load-declare-id">项目申报</a></li>
		        <li><a id="load-plan-id">项目计划</a></li>
		        <li><a id="load-schedule-id">项目进度</a></li>
		        <li><a href="#">项目审核</a></li>
		        <li><a href="#">项目存档</a></li>
		        <li><a href="#">项目评价</a></li>
		        <li><a href="#">项目总结</a></li>
		        <li><a href="#">以存档项目</a></li>
		        <li><a href="#">统计计分</a></li>
		        <li><a href="#">统计日志</a></li>
		      </ul>
		    </li>
		    <li class="admin-parent">
		      <a class="am-cf" data-am-collapse="{target: '#collapse-nav1'}"><span class="am-icon-table"></span> 表格 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
		      <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav1">
		        <li><a href="html/table_basic.html" class="am-cf"> 基本表格</span></a></li>
		        <li><a href="html/table_complete.html">完整表格</a></li>
		      </ul>
		    </li>
		    <li class="admin-parent">
		      <a class="am-cf" data-am-collapse="{target: '#collapse-nav2'}"><i class="am-icon-line-chart" aria-hidden="true"></i> 统计图表 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
		      <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav2">
		        <li><a href="html/chart_line.html" class="am-cf"> 折线图</span></a></li>
		        <li><a href="html/chart_columnar.html" class="am-cf"> 柱状图</span></a></li>
		        <li><a href="html/chart_pie.html" class="am-cf"> 饼状图</span></a></li>
		      </ul>
		    </li>
		    <li class="admin-parent">
		      <a class="am-cf" data-am-collapse="{target: '#collapse-nav5'}"><span class="am-icon-file"></span> 表单 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
		      <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav5">
		        <li><a href="html/form_basic.html" class="am-cf"> 基本表单</a></li>
		        <li><a href="html/form_validate.html">表单验证</a></li>   
				<li><a>  </a></li>   
		      </ul>
		    </li>
		  </ul>
</div>
  </div>
</body>
</html>