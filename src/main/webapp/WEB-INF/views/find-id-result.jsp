<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>아이디 찾기</title>

</head>
<body>

    <h2>Find ID Result</h2>
    
    <c:if test="${not empty result}">
        <p>Found ID: ${result.id}</p>
    </c:if>
    
    <c:if test="${not empty error}">
        <p>${error}</p>
    </c:if>
    
    <a href="find-id.jsp">Back to Find ID</a>
</body>
</html>