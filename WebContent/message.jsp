<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
	<link href="css/newMessage.css" rel="stylesheet" type="text/css">
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
			<c:remove var="errorMessages" scope="request"/>
		</c:if>

			<form action="message" method="post"><br />
				 <label for="title" >件名<br /></label>
				 <input name="title" value="${title}" /> <br />

				 カテゴリー<br /><input name="category" id="category" value="${category}" list="keywords" ><br />
					<datalist id="keywords">
						<c:forEach items="${categorys}" var="category">
							<option value="${category}">
			     		</c:forEach>
			     	</datalist>

				 本文<br />
				 <textarea name="text"  cols="65" rows="3" class="tweet-box">${text}</textarea>
				 <br />
				 <span class="id"><input type ="hidden" name="branch_id" value="${loginUser.branchId}" /></span>
				 <span class="id"><input type ="hidden" name="department_id" value="${loginUser.departmentId}" /></span>
				 <input type="submit" value="投稿する" />
			</form>

			<a href="./">戻る</a>
		<div class="copyright">Copyright(c)Hiroya Iguchi</div>
	</div>
</body>
</html>
