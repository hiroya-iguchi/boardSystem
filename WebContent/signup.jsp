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
			<label for="login_id">ログインID （半角英数字で6～20文字）<br></label>
			<input name="login_id" value = "${login_id}" id="login_id"/><br />

			<label for="name">名前 （10文字以下）<br></label>
			<input name="name" value = "${name}" id="name"/><br />

		    <label for="password">パスワード （半角英数字（記号含む）で6～20文字）<br></label>
			<input name="password" type="password" id="password"/> <br />

			<label for="password">パスワード（確認用）<br></label>
			<input name="password2" type="password" id="password"/><br />

			<div class="form-item">
					<label for="branch_id">支店<br></label>
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
					<label for="department_id">部署・役職<br></label>
					<select name="department_id" class="selectpicker">
			  			<c:forEach var="department" items="${departments}">
							<c:if test="${department.id == user.departmentId}">
								<option value="${department.id}" selected>${department.name}</option>
							</c:if>
							<c:if test="${department.id != user.departmentId}">
								<option value="${department.id}">${department.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			<div class ="button-panel">
			<input type="submit" class ="button" value="新規登録" /> <br />
			</div>
		</form>
		<a href="management" style="text-decoration: underline">戻る</a>

	</div>
</body>
</html>
