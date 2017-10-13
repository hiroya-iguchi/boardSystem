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
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
 	<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
 	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>

	<script>
		$(function() {
		$.datepicker.setDefaults( $.datepicker.regional[ "ja" ] );
		$.datepicker.setDefaults({
		});
		$( ".datepicker" ).datepicker();
	});
	</script>
</head>
<body>


<div class="main-contents">

	<c:if test="${ not empty errorComments }">
				<div class="errorMessages">
				<ul>
				<c:forEach items="${errorComments}" var="comment">
				<li><c:out value="${comment}" />
				</c:forEach>
				</ul>
				</div>
		<c:remove var="errorComments" scope="session"/>
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


	<c:if test="${ not empty loginUser }">
			<h3><c:out value="${loginUser.name}" /></h3>
	</c:if>

	<div class ="refine">
		<c:if test="${ not empty loginUser }">
			<form method="GET" action=".">

				カテゴリー<input name="category" id="category" value="${strCategory}" list="keywords" >（スペースでOR検索 ※2つまで）<br />
				<datalist id="keywords">
					<c:forEach items="${categorys}" var="category">
						<option value="${category}">
		     		</c:forEach>
		     	</datalist>

				投稿日時
				<input type='text' name="startDate" class="datepicker" id="datepicker" value="${startDate}" readonly="readonly" /> ～

				<input type='text' name="endDate" class="datepicker" id="datepicker" value="${endDate}" readonly="readonly"/>
					<div class ="button-panel">
			  		<input type="submit" class ="button" value="検索">
			  		</div>
			 </form>

			 <form action="./" method="get">
					<input type="hidden" name="category"  >
					<input type="hidden" name="start_date" >
					<input type="hidden" name="end_date" >
					<div class="button-panel">
						<input type="submit" class="reset-button" value="検索リセット">
					</div>
			 </form>
		</c:if>
	</div>


<c:if test="${ not empty loginUser }">
	<div class="messages">
		<c:forEach items="${messages}" var="message">
			<div class="message">
				<form action="delete" method="post">
						件名：<span class="title"><c:out value="${message.title}" /></span><br>
						カテゴリー：<span class="category"><c:out value="${message.category}" /></span><br>

						<span class="id"><input type ="hidden" name="id" value="${message.id}" /></span>
						本文：<div class="text">
								<c:forEach var="s" items="${fn:split(message.text, '
								')}">
	    						<c:out  value="${s}" /><br>
								</c:forEach></div>
								<span class="name">投稿者： <c:out value="${message.name}" /></span>
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


			<div class="comments">
				<c:forEach items="${comments}" var="comment">
					<c:if test="${message.id == comment.messageId}">
							<div class="comment">
							<form action="commentDelete" method="post">
							    <span class="id"><input type ="hidden" name="id" value="${comment.id}" /></span>
									<div class="text">
										<c:forEach var="s" items="${fn:split(comment.text, '
										')}">
			    						<c:out  value="${s}" /><br>
										</c:forEach></div>
									<span class="name">投稿者： <c:out value="${comment.name}" /></span>
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
					</c:if>
				</c:forEach>
			</div>


			<form action="comment" method="post"><br />
			<div class="NewComment">
					<div class="NewComment">
						<div class="newComment">
						コメント（500文字以内）<br>
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

</div>
</body>
</html>
