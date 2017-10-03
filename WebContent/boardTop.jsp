<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板システム</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">

<c:if test="${ not empty errorComments }">
				<div class="errorComments">
				<ul>
				<c:forEach items="${errorComments}" var="comment">
				<li><c:out value="${comment}" />
				</c:forEach>
		</ul>
	</div>
	<c:remove var="errorComments" scope="session"/>
</c:if>

<div class="header">
	<c:if test="${ empty loginUser }">

	<form action="login" method="post"><br />
	<label for="login_id">ログインID</label>
	<input name="login_id" id="login_id" value = "${login_id}"/> <br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br />

	<input type="submit" value="ログイン" /> <br />

</form>
</c:if>
</div>
	<c:if test="${ not empty loginUser }">
		<a href="message">新規投稿</a>
		<a href="management">ユーザー管理</a>
		<a href="logout">ログアウト</a>
	</c:if>
</div>
	<c:if test="${ not empty loginUser }">
	<div class="profile">
		<div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
		<div class="login_id">
		</div>

	</div>
	</c:if>

	<c:if test="${ not empty loginUser }">
	<form method="GET" action=".">
 	 <label for="category">カテゴリー</label>
		<input name="category"  /> <br />
		<label for="startDate">投稿日時（例：2018-01-01）</label>
		<input name="startDate"  /> ～
		<label for="endDate"></label>
		<input name="endDate"  /> <br />
  		<input type="submit" value="検索">
	 </form>
	</c:if><br><br>


<c:if test="${ not empty loginUser }">
<div class="messages">
	<c:forEach items="${messages}" var="message">
		<div class="message-view">
			<div class="message">
			<form action="delete" method="post"><br />
				<div class="account-name">
					件名：<span class="title"><c:out value="${message.title}" /></span><br>
					カテゴリー：<span class="category"><c:out value="${message.category}" /></span><br>
					<span class="id"><input type ="hidden" name="id" value="${message.id}" /></span>
				</div>
					本文：<span class="text"><c:out value="${message.text}" /></span>
				<div class="date"><fmt:formatDate value="${message.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>

					<c:if test="${ loginUser.id == message.userId || loginUser.departmentId == 2 }">
						<input type="submit"  value="削除" /> <br />
						</c:if>
						<c:if test="${ loginUser.departmentId == 3 && loginUser.branchId == comment.branchId && loginUser.id != comment.userId }">
						<input type="submit"  value="削除" /> <br />
					</c:if>
			</form>
			</div>
		</div>


			<div class="comment">
				<c:forEach items="${comments}" var="comment">
				<c:if test="${message.id == comment.messageId}">
					<div class="comment-view">
						<div class="comment">
						<form action="commentDelete" method="post"><br />
						    <span class="id"><input type ="hidden" name="id" value="${comment.id}" /></span>
							<div class="text"><c:out  value="${comment.text}" /></div>
							<div class="date"><fmt:formatDate value="${comment.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
							<c:if test="${ loginUser.id == comment.userId || loginUser.departmentId == 2 }">
							<input type="submit"  value="削除" /> <br />
							</c:if>
							<c:if test="${ loginUser.departmentId == 3 && loginUser.branchId == comment.branchId && loginUser.id != comment.userId }">
							<input type="submit"  value="削除" /> <br />
							</c:if>

						</form>
						</div>
					</div>
				</c:if>
				</c:forEach>
			</div>


			<form action="comment" method="post"><br />
			<div class="comments">
						<div class="comment">
							<div class="newComment">
					        <textarea name="text" cols="100" rows="3" class="tweet-box">${text}</textarea>
					        <br />
					        <span class="id"><input type ="hidden" name="message_id" value="${message.id}" /></span><br>
							<div class="text"><c:out value="${comment.text}" /></div>
							<div class="date"><fmt:formatDate value="${comment.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
							<span class="id"><input type ="hidden" name="branch_id" value="${message.branchId}" /></span>
							<span class="id"><input type ="hidden" name="department_id" value="${message.departmentId}" /></span>
							<input type="submit" value="コメントする" /> <br /><br /><br />
						    </div>
						</div>
			</div>
			</form>


	</c:forEach>

</div>
</c:if>

<div class="copyright">Copyright(c)Hiroya Iguchi</div>
</body>
</html>
