<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板システム</title>
	<link href="./css/top.css" rel="stylesheet" type="text/css">
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
		<c:remove var="errorComments" scope="request"/>
	</c:if>

	<div class="header"></div>
		<c:if test="${ not empty loginUser }">
			<c:if test="${loginUser.departmentId == 1 }">
				<a href="message">新規投稿</a>
				<a href="management">ユーザー管理</a>
				<a href="logout">ログアウト</a>
			</c:if>

			<c:if test="${loginUser.departmentId != 1 }">
				<a href="message">新規投稿</a>
				<a href="logout">ログアウト</a>
			</c:if>
		</c:if>
	</div>

	<c:if test="${ not empty loginUser }">
		<div class="profile">
			<div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
		</div>
	</c:if>

	<div class ="refine">
		<c:if test="${ not empty loginUser }">
			<form method="GET" action=".">

				カテゴリー<input name="category" id="category" value="${category}" list="keywords" ><br />
				<datalist id="keywords">
					<c:forEach items="${categorys}" var="category">
						<option value="${category}">
		     		</c:forEach>
		     	</datalist>

				<label for="startDate">投稿日時</label>
				<input type='date' name="startDate" id="date" value="${startDate}" /> ～
				<label for="endDate"></label>
				<input type='date' name="endDate" id="date" value="${endDate}"/> <br />
					<div class ="button-panel">
			  		<input type="submit" class ="button" value="検索">
			  		</div>
			 </form>
		</c:if>
	</div>


<c:if test="${ not empty loginUser }">
	<div class="messages">
		<c:forEach items="${messages}" var="message">
			<div class="message-view">
				<div class="message">
				<form action="delete" method="post"><br />
						件名：<span class="title"><c:out value="${message.title}" /></span><br>
						カテゴリー：<span class="category"><c:out value="${message.category}" /></span><br>

						<span class="id"><input type ="hidden" name="id" value="${message.id}" /></span>
						本文：<div class="text">
								<c:forEach var="s" items="${fn:split(message.text, '
								')}">
	    						<c:out  value="${s}" />
								</c:forEach></div>
							<div class="date"><fmt:formatDate value="${message.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>


							<c:if test="${ loginUser.id == message.userId || loginUser.departmentId == 2 }">
								<div class ="button-panel">
									<input type="submit" class ="delete-button" value="削除" onClick="return confirm('メッセージを削除しますか？')" />
								</div>
							</c:if>
							<c:if test="${ loginUser.departmentId == 3 && loginUser.branchId == message.branchId && loginUser.id != message.userId }">
								<div class ="button-panel">
									<input type="submit" class ="delete-button" value="削除" onClick="return confirm('メッセージを削除しますか？')"/>
								</div>
							</c:if>

				</form>
				</div>
			</div>


			<div class="comments">
				<c:forEach items="${comments}" var="comment">
					<c:if test="${message.id == comment.messageId}">
						<div class="comment-view">
							<div class="comment">
							<form action="commentDelete" method="post"><br />
							    <span class="id"><input type ="hidden" name="id" value="${comment.id}" /></span>
								<div class="text">
									<c:forEach var="s" items="${fn:split(comment.text, '
									')}">
		    						<c:out  value="${s}" /><br>
									</c:forEach></div>
								<div class="date"><fmt:formatDate value="${comment.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>

								<c:if test="${ loginUser.id == comment.userId || loginUser.departmentId == 2 }">
									<div class ="button-panel">
									<input type="submit" class ="delete-button" value="削除" onClick="return confirm('コメントを削除しますか？')"/> <br />
									</div>
								</c:if>

								<c:if test="${ loginUser.departmentId == 3 && loginUser.branchId == comment.branchId && loginUser.id != comment.userId }">
									<div class ="button-panel">
									<input type="submit" class ="delete-button" value="削除" onClick="return confirm('コメントを削除しますか？')"/> <br />
									</div>
								</c:if>

							</form>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>


			<form action="comment" method="post"><br />
			<div class="comment">
					<div class="comment">
						<div class="newComment">
				        <textarea name="text" cols="83" rows="3" class="tweet-box">${text}</textarea> <br />
				        <span class="id"><input type ="hidden" name="message_id" value="${message.id}" /></span>
						<div class="text">
						<c:out value="${comment.text}" escapeXml="false" /></div>
						<div class="date"><fmt:formatDate value="${comment.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
					<!-- 	<span class="id"><input type ="hidden" name="branch_id" value="${message.branchId}" /></span>
						<span class="id"><input type ="hidden" name="department_id" value="${message.departmentId}" /></span> -->

						<div class ="button-panel">
							<input type="submit"class ="comment-button" value="コメント" /> <br /><br /><br />
						</div>
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
