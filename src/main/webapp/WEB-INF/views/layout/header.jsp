<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-expand-md bg-dark navbar-dark">
        <a class="navbar-brand" href="/">ホーム</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <c:choose>
                <c:when test="${empty SessionScope.principle}">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="/loginForm">ログイン</a></li>
                        <li class="nav-item"><a class="nav-link" href="/joinForm">会員登録</a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="/board/form">書き込み</a></li>
                        <li class="nav-item"><a class="nav-link" href="/user/form">会員情報</a></li>
                        <li class="nav-item"><a class="nav-link" href="/logout">ログアウト</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
    <br />
</body>
</html>
