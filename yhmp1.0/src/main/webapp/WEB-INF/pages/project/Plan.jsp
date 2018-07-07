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
				
				<div class="form-group_1">
					<h4 >&nbsp; &nbsp;第1阶段计划</h4>
					<!-- <h4 onmouseover="MouseOver('1')" id="plan_1" >&nbsp; &nbsp;第1阶段计划</h4> -->
					<!-- <h4 onmouseout="Mouseout('1')" style="display:none" id="schedule_1" >&nbsp; &nbsp;项目进度</h4> -->
					<div class="varySchedule_1">
						<div>
						计划进度:
							<div class="input-group" >
								<input type="text" class="form-control" id="planSchedule_1" placeholder="请输入计划进度" >
								<div class="input-group-addon" >%</div>
							</div>
						</div>
						<br>
						<span>
						计划内容:
						<input type="text" class="form-control" id="projectContent_1" ></input>
						</span>
						<br>
						<span>
						完成时间:
						<input type="date" class="form-control required datepickers" id="finishTime_1" name="finishTime_1" >
						</span>
						<br>
						<span>
						项目成员:
						<input type="text" class="form-control" id="personLiable_1" ></input>
						</span>
						<br>
						<span>
						<input type="button" class="form-control" id="plan_1" value="查看实际进度" ></input>
						</span>
						<br>
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