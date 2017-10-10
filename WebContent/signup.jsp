<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー新規登録</title>
	<link href="./css/signup.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>ユーザー新規登録</h1>
	<div class="main-contents">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="request"/>
		</c:if>

		<form action="signup" method="post">
			<label for="login_id">ログインID</label>
			<input name="login_id" value = "${login_id}" id="login_id"/>（半角英数字で6文字以上20文字以下）<br />

			<label for="name">名前</label>
			<input name="name" value = "${name}" id="name"/><br />（10文字以下）

		    <label for="password">パスワード</label>
			<input name="password" type="password" id="password"/>（半角英数字（記号含む）6文字以上20文字以下） <br />

			<label for="password">パスワード(確認用)</label>
			<input name="password2" type="password" id="password"/><br />

			<div class="form-item">
					<label for="branch_id">支店</label>
					<select name="branch_id" class="selectpicker" >
						<c:forEach var="branch" items="${branches}">
							<c:if test="${branch.id == user.branchId}">
								<option value="${branch.id}" selected>${branch.name}</option>
							</c:if>
							<c:if test="${branch.id != user.branchId}">
								<option value="${branch.id}">${branch.name}</option>
							</c:if>
						</c:forEach>
					</select><br />
			</div>
				<div class="form-item">
					<label for="department_id">部署・役職</label>
					<select name="department_id" class="selectpicker">
			  			<c:forEach var="department" items="${departments}">
							<c:if test="${department.id == user.departmentId}">
								<option value="${department.id}" selected>${department.name}</option>
							</c:if>
							<c:if test="${department.id != user.departmentId}">
								<option value="${department.id}">${department.name}</option>
							</c:if>
						</c:forEach>
					</select><br />
				</div><br>


			<input type="submit" value="新規登録" /> <br />
			<a href="management">戻る</a>
		</form>

	<div class="copyright">Copyright(c)Iguchi Hiroya</div>
	</div>
</body>
</html>
