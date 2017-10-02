<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>新規投稿</h1>
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

	<form action="message" method="post"><br />

		 <label for="title" >件名</label>
		 <input name="title" value="${title}" /> <br />
		 <label for="category">カテゴリー</label>
		 <input name="category" value="${category}" id="category"/> <br />
		 本文
		 <textarea name="text"  cols="100" rows="5" class="tweet-box">${text}</textarea>
		 <br />
		 <span class="id"><input type ="hidden" name="branchId" value="${editUser.branchId}" /></span>
		 <span class="id"><input type ="hidden" name="departmentId" value="${editUser.departmentId}" /></span>
		 <input type="submit" value="投稿する" /> <br />
	</form>
    </div>

	<a href="./">戻る</a>
<div class="copyright">Copyright(c)Iguchi Hiroya</div>
</body>
</html>
