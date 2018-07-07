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
					<form class="navbar-form navbar-left" role="search">
						<div class="form-group text-center">
							<input type="text" class="form-control" placeholder="请输入要查询的项目" id="inputSearch" />
						</div> <button type="button" class="btn btn-default" id="search">搜索</button>
					</form>
					<br><br>
				<h3>
					项目信息
				</h3>
				<br/>
				<input type="button" value="查看" id="viewProject" data-toggle="modal" data-target="#projectModal"> &nbsp &nbsp 
				<input type="button" value="编辑" id="editProject" data-toggle="modal" data-target="#projectModal"> &nbsp &nbsp 
				<input type="button" value="删除" id="deleteProject"> &nbsp &nbsp 
				<input type="button" value="项目计划" id="projectPlan" data-toggle="modal" data-target="#planModal"/> &nbsp &nbsp 
				<input type="button" value="项目进度" id="load-schedule-id" /> &nbsp &nbsp 
				<br/>
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>
								选择
							</th>
							<th>
								项目名称
							</th>
							<th>
								项目规模
							</th>
							<th>
								合同签订情况
							</th>
							<th>
								完成时间
							</th>
							<th>
								是否需要招投标
							</th>
							<th>
								项目负责人
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
		<!-- 模态框（Modal） -->
	<div class="modal fade" id="projectModal" tabindex="-1" role="dialog" aria-labelledby="projectModalH4" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="cancel1" >
						&times;
					</button>
					<h4 class="modal-title" id=projectModalH4>
						项目信息
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
										项目名称：
										<input type="text" class="form-control" id="declareProject" ></input>
									</span>
									<br>
									<span>
										项目编号：
										<input type="text" class="form-control" id="declareNumber"></input>
									</span>
									<br>
									<span>
										项目标准分：
										<input type="text" class="form-control" id="declareFraction"></input>
									</span>
									<br>
									<span>
										项目规模：
										<input type="text" class="form-control" id="declareScale"></input>
									</span>
									<br>
									<span>
										项目类型-整理：
										<input type="text" class="form-control" id="declareArrange"></input>
									</span>
									<br>
									<span>
										项目类型-评估：
										<input type="text" class="form-control" id="declareAssess"></input>
									</span>
									<br>
									<span>
										项目类型-测绘：
										<input type="text" class="form-control" id="declareSurveying"></input>
									</span>
									<br>
									<span>
										项目类型-规划：
										<input type="text" class="form-control" id="declarePlan"></input>
									</span>
									<br>
									<span>
										合同签订情况：
										<select class="form-control" id="contract" >
											<option value="0">未签订</option>
											<option value="1">已签订</option>
										</select>
									</span>
									<br>
									<span>
										单位名称：
										<input type="text" class="form-control" id="declareCompany"></input>
									</span>
									<br>
									<span>
										联系人姓名：
										<input type="text" class="form-control" id="declareName"></input>
									</span>
									<br>
									<span>
										联系人电话：
										<input type="text" class="form-control" id="declarePhone"></input>
									</span>
									<br>
									<span>
										项目完成时间：
										<input type="date" class="form-control required datepickers" id="finishTime" name="finishTime1" >
									</span>
									<br>
									<span>
										项目质量要求：
										<select class="form-control" id="quality" >
											<option value="1">优秀</option>
											<option value="2">良好</option>
											<option value="3">合格</option>
										</select>
									</span>
									<br>
									<span>
										招投标情况：
										<select class="form-control" id="Bid" >
											<option value="1">需要</option>
											<option value="2">不需要</option>
											<option value="3">暂不确定</option>
										</select>
									</span>
									<br>
									<span>
										负责人姓名：
										<input type="text" class="form-control" id="responsibilityName"></input>
									</span>
									<br>
									<span>
										负责人电话：
										<input type="text" class="form-control" id="responsibilityPhone" ></input>
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
					<button type="button" class="btn btn-default" data-dismiss="modal" id="cancelProject" >
						取消
					</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveProject" >
						保存
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- 项目计划模态框 -->
	<div class="modal fade" id="planModal" tabindex="-1" role="dialog" aria-labelledby="planModalH4" aria-hidden="true" data-backdrop="static" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="cancel1" >
						&times;
					</button>
					<h4 class="modal-title" id=planModalH4>
						项目工作计划
					</h4>
				</div>
				<div class="modal-body">
				<!-- 开始 -->
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="span2">
							</div>
							<div class="span8">
								<form class="form-group" id="addFrom">
									<span>
									项目名称：
									<input type="text" class="form-control" id="projectName_1" disabled="disabled"></input>
									</span>
									<br>
									<span>
									项目负责人:
									<input type="text" class="form-control" id="projectLeadingOffcial_1" disabled="disabled"></input>
									</span>
									<br>
									<div class="form-group_1">
										<h4 >&nbsp; &nbsp;第1阶段计划</h4>
										<!-- <h4 onmouseout="Mouseout(1)" hidden onclick="onProjectPlan(1)" >&nbsp; &nbsp;项目进度</h4> -->
										<div id="falsePlan_1" >
											<span>
											计划进度:
												<span class="input-group" >
													<input type="text" class="form-control" id="planSchedule_1" placeholder="请输入计划进度" >
													<span class="input-group-addon" >%</span>
												</span>
											</span>
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
										</div>
										<div id="truePlan_1" hidden  >
											<span>
											计划进度:
												<span class="input-group" >
													<input type="text" class="form-control" placeholder="请输入计划进度" >
													<span class="input-group-addon" >%</span>
												</span>
											</span>
											<br>
											<span>
											实际工作：
											<input type="text" class="form-control" ></input>
											</span>
											<br>
											<span>
											实际完成时间:
											<input type="date" class="form-control required datepickers" >
											</span>
											<br>
											<span>
											调整工期理由:
											<input type="text" class="form-control" ></input>
											</span>
											<br>
										</div>
										<span>
											<input type="button" class="form-control_plan_1" onclick="onProjectPlan(1);" value="查看实际进度" ></input>
										</span>
									</div>
									<br>
									<button type="reset" style="display: none;" ></button>
								</form>
								<button type="button" class="btn btn-default" onclick="addPlan();" >添加阶段计划</button>
								<button type="button" class="btn btn-default" onclick="deletePlan();" >移除阶段计划</button>
							</div>
							<div class="span2">
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="cleanPlan">
						取消
					</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="addPlan" >
						保存
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<script type="text/javascript" src="${basePath }/yhmp/product/information.js" ></script>
	<!-- 项目工作计划 -->
	<script type="text/javascript" src="${basePath}/yhmp/product/plan.js" ></script>
</body>
</html>