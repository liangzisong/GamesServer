<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>天鸿项目测绘管理系统</title>
    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">
  </head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-2 column">
		</div>
		<div class="col-md-8 column">
			<h3 class="text-center">
				项目工作计划
			</h3>
			<form class="form-horizontal" role="form" id="addFrom">
				<div class="form-group">
					 <label for="nameId" class="col-sm-2 control-label">项目名称：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control required1" id="table2-projectName" name="projectName" />
					</div>
				</div>
				<div class="form-group">
					<label for="nameId" class="col-sm-2 control-label">项目负责人:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control required2" id="table2-projectLeadingOffcial" name="projectLeadingOffcial" >
					</div>
				</div>
				<div class="form-group">
					<h4><b> 项目第1阶段计划</b></h4>
					<label for="nameId" class="col-sm-2 control-label">计划内容:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control required3" id="table2-projectContent1" name="projectContent1" >
					</div>
					<label for="nameId" class="col-sm-2 control-label">完成时间:</label>
					<div class="col-sm-10">
						<input type="date" class="form-control required datepickers" id="table2-finishTime1" name="finishTime1" >
					</div>
					<label for="nameId" class="col-sm-2 control-label" >项目成员:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control required4" id="table2-personLiable1" placeholder="多个人名以逗号隔开" name="personLiable1">
					</div>

				</div>


		</form>
					<button type="button" class="btn btn-default" onclick="add();" >点击添加阶段计划</button>
			<p class="text-center">
				<button type="button" class="btn btn-default" id="table2-submit" >提交</button>
			</p>
		</div>
		<div class="col-md-2 column">
		</div>
	</div>
</div>
	<script type="text/javascript" src="${basePath}/yhmp/product/plan.js" ></script>

</body>
</html>