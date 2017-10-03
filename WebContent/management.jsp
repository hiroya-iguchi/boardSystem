<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理システム</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>ユーザー管理システム</h1>
<div class="main-contents">

<div class="header">
	<c:if test="${ empty loginUser }">
	<br>アクセス権限がありません。<br>
		<a href="settings">ユーザー管理</a>
		<a href="logout">ログアウト</a>
	</c:if>
	<c:if test="${ not empty loginUser }">
		<a href="signup">新規登録</a>
		<a href="./">ホーム</a>
	</c:if>
</div>

<c:if test="${ not empty loginUser }">
	<div class="profile">
		<div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
		<div class="login_id"></div>

		<div class="users">
			<c:forEach items="${users}" var="user">
			<div class="user">
				<form action="settings" method="GET" >
				<div class="user-profile">
					<span class="name"><c:out value="${user.name}" /></span><br>
					<span class="blanch"><c:out value="${user.branchName}" /></span><br>
					<span class="department"><c:out value="${user.departmentName}" /></span>
				<span class="id"><input type ="hidden" name="id" value="${user.id}" /></span>
				</div>
				<input type="submit" value="編集" /> <br />
				</form>
			<c:if test="${ user.id == loginUser.id }">
			ログイン中<br /><br />
			</c:if>

			<c:if test="${ user.id != loginUser.id }">
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
			</div>
			</c:forEach>
		</div>

	</div>
</c:if>

<div class="copyright">Copyright(c)Hiroya Iguchi</div>
</div>
</body>
</html>
