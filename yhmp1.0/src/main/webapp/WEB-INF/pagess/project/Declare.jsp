<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>天鸿项目测绘管理系统</title>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-2 column"></div>
			<div class="col-md-8 column">
				<!-- 表头 -->
				<h3 class="text-center">项目申报交接</h3>
				<form role="form">
	
					<h4 class="text-left ">
						<b>1. 业务基本情况</b>
					</h4>
					<div class="form-group">
						<label for="nameId" class="col-sm-2 control-label">项目名称：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="declareProject"/>
						</div>
					</div>
					<div class="form-group">
						<label for="nameId" class="col-sm-2 control-label">项目编号：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="declareNumber" />
						</div>
					</div>
					<div class="form-group">
						<label for="nameId" class="col-sm-2 control-label">项目标准分:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control required" id="declareFraction">
						</div>
					</div>
					<div class="form-group">
						<label for="nameId" class="col-sm-2 control-label">项目规模：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="declareScale" />
						</div>
					</div>
					
					<div class="form-group">
						<br>
						<label for="nameId" class="col-sm-2 control-label">项目类型：</label>
						<div class="col-sm-10">
							<br>
							<div class="col-sm-2"><small class="control-label">整理：</small></div>
							<div class="col-sm-10">
							<input type="text" class="form-control"  id="declareArrange"  placeholder="请输入整理类型"/><br>
							</div>
							<div class="col-sm-2"><small class="control-label">评估：</small></div>
							<div class="col-sm-10">
							<input type="text" class="form-control"  id="declareAssess" placeholder="请输入评估类型"/><br>
							</div>
							<div class="col-sm-2"><small class="control-label">测绘：</small></div>
							<div class="col-sm-10">
							<input type="text" class="form-control"  id="declareSurveying" placeholder="请输入测绘类型"/><br>
							</div>
							<div class="col-sm-2"><small class="control-label">规划：</small></div>
							<div class="col-sm-10">
							<input type="text" class="form-control" id="declarePlan" placeholder="请输入规划类型"/><br>
							</div>
						</div>
						
					</div>
					
	
					<div class="form-group" id="contract">
						<label for="nameId" class="col-sm-2 control-label">合同签订情况:</label>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="contract" value="已签订">已签订 
						&nbsp;&nbsp;
						<input type="radio" name="contract" value="未签订">未签订
					</div>
	
	
	
					<h4 class="text-left ">
						<b>2. 委托方相关信息</b>
					</h4>
					<div class="form-group">
						<div class="form-group">
							<label for="nameId" class="col-sm-2 control-label">单位名称：</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="declareCompany" />
							</div>
						</div>
						<div class="form-group">
							<label for="nameId" class="col-sm-2 control-label">联系人姓名:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control required" id="declareName">
							</div>
						</div>
						<div class="form-group">
							<label for="nameId" class="col-sm-2 control-label">联系人电话：</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="declarePhone" />
							</div>
						</div>
					</div>
	
	
					<h4 class="text-left ">
						<b>3. 业务要求</b>
					</h4>
					<div class="form-group">
						<label for="nameId" class="col-sm-2 control-label">完成时间：</label>
						<div class="col-sm-10">
							<input type="date" class="form-control required datepickers" id="declare-finishTime" name="finishTime1" >
						</div>
					</div>
					
				  <div class="form-group" id="quality" >
					&nbsp;&nbsp;&nbsp;
					<label for="exampleInputEmail1">项目质量要求:</label> 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="quality" value="优秀" checked>优秀
						&nbsp;&nbsp;
						<input type="radio" name="quality" value="良好" checked>良好 
						&nbsp;&nbsp;
						<input type="radio" name="quality" value="合格" checked>合格
					</div>
					<div class="form-group" id="Bid" >
						<p />
						&nbsp;&nbsp;&nbsp;
						<label for="exampleInputEmail1">是否需要招投标:</label> 
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="Bid" value="需要">需要 
						&nbsp;&nbsp;
						<input type="radio" name="Bid" value="不需要">不需要
						&nbsp;&nbsp;
						<input type="radio" name="Bid" value="暂不确定">暂不确定
					</div>
	
					<h4 class="text-left ">
						<b>4. 项目负责人</b>
					</h4>
					<div class="form-group">
						<label for="nameId" class="col-sm-2 control-label">负责人姓名：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="responsibilityName" />
						</div>
					</div>
					<div class="form-group">
						<label for="nameId" class="col-sm-2 control-label">负责人电话：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="responsibilityPhone" />
						</div>
					</div>
					<div class="col-md-9 column">
						<div class="row clearfix">
							<div class="col-md-3 column"></div>
							<div class="col-md-9 column">
								<br> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-default" id="table1-submit" >提交</button>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>
	<script type="text/javascript" src="${basePath}/yhmp/product/declare.js" ></script>
</body>
</html>