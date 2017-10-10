<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
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


			<form action="login" method="post"><br />
				<label for="login_id">ログインID</label>
				<input name="login_id" id="login_id" value = "${login_id}"/> <br />

				<label for="password">パスワード</label>
				<input name="password" type="password" id="password"/> <br />

				<input type="submit" value="ログイン" /> <br />

			</form>

		<div class="copyright">Copyright(c)Hiroya Iguchi</div>
	</div>
</body>
</html>
