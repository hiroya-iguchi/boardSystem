<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理画面</title>
	<link href="./css/users.css" rel="stylesheet" type="text/css">
</head>
<body>

<h1>ユーザー管理画面</h1>
<div class="main-contents">

	<div class="header">
		<c:if test="${ not empty loginUser }">
			<a href="./">ホーム</a>
			<a href="signup">新規登録　</a>
		</c:if>
	</div>

<c:if test="${ not empty loginUser }">

	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul>
				<c:forEach items="${errorMessages}" var="message">
					<li><c:out value="${message}" />
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session"/>
	</c:if>

		<div class="profile">
			<div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
			<div class="login_id"></div>

		<table border=1>
		 <tr><th>ログインID</th><th>名前</th><th>支店</th><th>部署・役職</th><th>編集</th><th>停止・復活</th></tr>
			<tbody>

				<c:forEach items="${users}" var="user">
				<tr>

					<td><div class="name"><c:out value="${user.loginId}" /></div></td>
					<td><div class="name"><c:out value="${user.name}" /></div></td>
					<td><div class="blanch"><c:out value="${user.branchName}" /></div></td>
					<td><div class="department"><c:out value="${user.departmentName}" /></div></td>

					<td>
						<form action="settings" method="GET">
							<div class="id">
								<input type="hidden" name="id" value="${user.id}" />
							</div>
							<div class="name"></div>
							<div class="name"></div>
							<div class="blanch"></div>
							<div class="department"></div>
							　<input type="submit" value="編集" />　
						</form>
					</td>

					<td><c:if test="${ user.id != loginUser.id }">
							<c:if test="${ user.isWorking == 1 }">
								<form action="account" method="post" ><br />
								<span class="id"><input type ="hidden" name="working" value="${0}" /></span>
								<span class="id"><input type ="hidden" name="id" value="${user.id}" /></span>
								　<input type="submit" value="停止" onClick="return confirm('ユーザーを停止しますか？')"/> <br />
								</form><br>
							</c:if>

							<c:if test="${ user.isWorking == 0 }">
								<form action="account" method="post" ><br />
								<span class="id"><input type ="hidden" name="working" value="${1}" /></span>
								<span class="id"><input type ="hidden" name="id" value="${user.id}" /></span>
								　<input type="submit" value="復活" onClick="return confirm('ユーザーを復活しますか？')" /> <br />
								</form><br>
							</c:if>
						</c:if>
					<c:if test="${ user.id == loginUser.id }">
						ログイン中
					</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
</c:if>

<div class="copyright">Copyright(c)Hiroya Iguchi</div>
</div>
</body>
</html>
