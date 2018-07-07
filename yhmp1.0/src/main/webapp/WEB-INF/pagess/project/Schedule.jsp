<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h3>
				您好！
			</h3>
			<nav class="navbar navbar-default navbar-static-top" role="navigation">
				<div class="navbar-header">
					 <p class="navbar-brand" >请输入要查询的项目进度</p>
				</div>
				
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

					<form class="navbar-form navbar-left" role="search">
						<div class="form-group">
							<input type="text" class="form-control" />
						</div> <button type="submit" class="btn btn-default">搜索</button>
					</form>
					
				</div>
				
			</nav>
			<ul id="project" >
				<li>
					<a class="projectSchedule1">项目一</a>
				</li>
				<li class="projectSchedule2">
					项目二
				</li>
				<li class="projectSchedule3">
					项目三
				</li>
				<li class="projectSchedule4">
					项目四
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript" src="${basePath }/yhmp/product/schedule.js" ></script>
</body>
</html>