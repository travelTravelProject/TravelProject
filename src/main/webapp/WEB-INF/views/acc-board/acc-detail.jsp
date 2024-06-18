<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Accompany Board Detail</title>

    <link rel="stylesheet" href="/assets/css/detail.css">

</head>
<body>

    <div class="container">
        <h1>Accompany Board Detail</h1>

        <div class="card">
            <h2>${board.title}</h2>
            <p>${board.content}</p>
            <p>Writer: ${board.writer}</p>
            <p>Reg Date: ${board.regDateTime}</p>
            <p>Views: ${board.viewCount}</p>
            <!-- 추가적인 필드들 -->
        </div>

        <!-- 추가적인 상세 정보 표시 -->
    </div>

</body>
</html>