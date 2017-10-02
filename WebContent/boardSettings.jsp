<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー情報を編集する</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
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
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="boardSettings" method="post" enctype="multipart/form-data"><br />

	<label for="login_id">ログインID</label>
	<input name="login_id" value="${editUser.loginId}" />（半角英数字で6～20文字） <br />

	<label for="name">名前</label>
	<input name="name" value="${editUser.name}" id="name"/><br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> （半角英数字（記号含む）で6～20文字）<br />

	<label for="password">パスワード(確認用)</label>
	<input name="password2" type="password" id="password"/>

<c:if test="${ editUser.id == loginUser.id }">
	<label for="branch_id">支店</label>
		<select name="branch_id">
    	<option value="1">本社</option>
    	</select><br />

    <label for="department_id">部署・役職</label>
		<select name="department_id">
    	<option value="1">総務人事担当</option>
    	</select>
</c:if><br />

<c:if test="${ editUser.id != loginUser.id }">
	<label for="branch_id">支店</label>
	<select name="branch_id">
    <option value="1">本社</option>
    <option value="2">支店A</option>
    <option value="3">支店B</option>
    <option value="4">支店C</option>
  	</select>
  	<br>

    <label for="department_id">部署・役職</label>
	<select name="department_id">
    <option value="1">総務人事担当</option>
    <option value="2">情報管理担当</option>
    <option value="3">店長</option>
    <option value="4">社員</option>

  	</select>
  	<br>
</c:if>
	<input type="submit" value="変更を保存" /> <br />

</form>


<a href="management">戻る</a>
<div class="copyright">Copyright(c)Iguchi Hiroya</div>
</div>
</body>
</html>
