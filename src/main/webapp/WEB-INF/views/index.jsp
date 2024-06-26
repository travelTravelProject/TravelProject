<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>WithTravel</title>

</head>
<body>

<h1>index</h1>
<h3>웨르컴 <span style="font-size: 40px; color: #000;">${user.nickname}</span></h3>
<%--<h4>${user.foundMember}</h4>--%>

<ul>
    <li><a href="/">메인홈</a></li>

    <c:if test="${user == null}">
        <li><a href="/sign-in">로그인</a></li>
        <li><a href="/sign-up">회원가입</a></li>
        <li><a href="/find-id">아이디 찾기</a></li>
        <li><a href="/find-pw">비밀번호 찾기? 변경?</a></li>
    </c:if>
    <c:if test="${user != null}">
        <li><a href="/sign-out">로그아웃</a></li>
        <li><a href="/mypage">마이페이지</a></li>
    </c:if>
</ul>


</body>
</html>