<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--"container"시작-->
<div class="container">

	<button class="btn btn-secondary" onclick="history.back()">戻る</button>
	<button id="btn-update" class="btn btn-warning">修正</button>
	<button id="btn-delete" class="btn btn-danger">削除</button>
	<br/><br/>
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
