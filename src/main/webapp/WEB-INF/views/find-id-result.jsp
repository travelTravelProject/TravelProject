<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="KO">
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/assets/css/login.css">
</head>
<body>
    <%@ include file="include/sub_header.jsp" %>
    <div class="login_wrap">
        <div class="login">
            <h1>너의 아이디는</h1>
            <c:if test="${not empty result}">
        <p>${result.id}</p>
    </c:if>
    
    <c:if test="${not empty error}">
        <p>${error}</p>
    </c:if>
            <button 
                type="submit" 
                class="button"
                onclick="location.href='/sign-in'"
            > 
                로그인하기
            </button>
        </div>
    </div>
    
</body>
</html>