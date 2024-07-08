<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <style>

        body, html {
            margin: 0;
            padding: 0;
            overflow: hidden;
            height: 100%;
            scroll-behavior: smooth;
        }

    </style>
    <!-- 파비콘 넣기 -->
<%--    <link rel="icon" href="assets/img/favicon.ico">--%>
<%--    <link rel="apple-touch-icon" href="assets/img/favicon.ico">--%>
    <title>With Trip</title>
</head>
<body>

    <header>
      <!-- <p style="    position: absolute;
      z-index: 2;">임시 : ${user.nickname} 반갑다</p> -->
      <div class="logo">
        <a href="/">
          <img src="../assets/img/logo_blue.png" alt="">
        </a>
      </div>
      <ul class="menu">
        <li><a href="/acc-board/list">동행게시판</a></li>
        <li><a href="/feed/list">피드</a></li>
        <c:if test="${user == null}">
            <li><a href="/sign-in">로그인</a></li>
            <li><a href="/sign-up">회원가입</a></li>
        </c:if>
        <c:if test="${user != null}">
            <li><a href="/mypage">마이페이지</a></li>
            <li><a href="/sign-out">로그아웃</a></li>
        </c:if>
      </ul>
    </header>

</body>
</html>
