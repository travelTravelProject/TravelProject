<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>

    <%@ include file="include/static-head.jsp" %>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>WithTravel</title>

</head>
<body>

    <%@ include file="include/static-head.jsp" %>
    <%@ include file="include/header.jsp"%>
    <div class="section" id="section1">
        <video autoplay muted loop id="background-video">
            <source src="/assets/img/video1.mp4" type="video/mp4">
        </video>
        <h1>
            "모험이 당신을 기다립니다 - 지금 떠나보세요!"
        </h1>
    </div>
    <div class="section" id="section2">
        <h1>Section 2</h1>
    </div>
    <div class="section" id="section3">
        <h1>Section 3</h1>
    </div>
    <div class="section" id="section4">
        <h1>Section 4</h1>
    </div>
    <div class="nav-dots">
        <span class="dot" data-index="0"></span>
        <span class="dot" data-index="1"></span>
        <span class="dot" data-index="2"></span>
        <span class="dot" data-index="3"></span>
    </div>


</body>
</html>