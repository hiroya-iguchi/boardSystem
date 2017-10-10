<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー情報を編集する</title>
	<link href="css/settings.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>ユーザー情報の編集</h1>
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

		<form action="settings" method="post" enctype="multipart/form-data">

			<span class="id"><input type ="hidden" name="id" value="${editUser.id}" /></span>
			<span class="id"><input type ="hidden" name="login_id" value="${editUser.loginId}" /></span>

			<label for="login_id">ログインID</label>
			<input name="login_id" value="${editUser.loginId}" />（半角英数字で6～20文字） <br />

			<label for="name">名前</label>
			<input name="name" value="${editUser.name}" id="name"/><br />

			<label for="password">パスワード</label>
			<input name="password" type="password" id="password"/> （半角英数字（記号含む）で6～20文字）<br />

			<label for="password">パスワード(確認用)</label>
			<input name="password2" type="password" id="password"/>

				<div class="form-item">
						<c:if test="${editUser.id == loginUser.id}">
							<label for="branch_id">支店</label>
							<select name="branch_id" >
								<option value="${editUser.branchId}" selected>本社</option>
							</select><br />
						</c:if>

						<c:if test="${editUser.id != loginUser.id}">
							<label for="branch_id">支店</label>
							<select name="branch_id" >
								<c:forEach var="s" items="${branches}">
									<c:if test="${s.id == editUser.branchId}">
										<option value="${s.id}" selected>${s.name}</option>
									</c:if>
									<c:if test="${s.id != editUser.branchId}">
										<option value="${s.id}">${s.name}</option>
									</c:if>
								</c:forEach>
							</select><br />
						</c:if>
				</div>

				<div class="form-item">
						<c:if test="${editUser.id == loginUser.id}">
							<label for="department_id">部署・役職</label>
							<select name="department_id">
								<option value="${editUser.departmentId}" selected>総務人事担当</option>
							</select><br />
						</c:if>

						<c:if test="${editUser.id != loginUser.id}">
							<label for="department_id">部署・役職</label>
							<select name="department_id">
					  			<c:forEach var="s" items="${departments}">
									<c:if test="${s.id == editUser.departmentId }">
										<option value="${s.id}" selected>${s.name}</option>
									</c:if>
									<c:if test="${s.id != editUser.departmentId }">
										<option value="${s.id}">${s.name}</option>
									</c:if>
								</c:forEach>
							</select><br />
						</c:if>
				</div>


			<input type="submit" value="変更を保存" /> <br />

		</form>

	<a href="management">戻る</a>
	</div>
<div class="copyright">Copyright(c)Iguchi Hiroya</div>
</body>
</html>
