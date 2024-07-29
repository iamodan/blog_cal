<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--"container"시작-->
<div class="container">

	<button class="btn btn-secondary" onclick="history.back()">戻る</button>

	<c:if test="${board.user.id == principal.user.id}">
		<!-- 		<button id="btn-update" class="btn btn-warning">修正</button> -->
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">修正</a>
		<button id="btn-delete" class="btn btn-danger">削除</button>
	</c:if>
	<br /> <br />
	<div>
		投稿番号：<span id="id"><i>${board.id}</i></span>&nbsp;&nbsp;/&nbsp; 投稿者：<span
			id="id"><i>${board.user.username} </i></span> <br />
	</div>
	<div class="form-group">
		<h3>${board.title}</h3>
	</div>
	<hr />
	<div class="form-group">
		<div>${board.content}</div>
	</div>
	<hr />

</div>
<!--"container"끝-->
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>
