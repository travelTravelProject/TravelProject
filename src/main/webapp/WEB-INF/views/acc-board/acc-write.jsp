<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>동행 게시판 글쓰기</title>

    <!-- reset -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">

    <!-- fontawesome css: https://fontawesome.com -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">

    <!-- https://linearicons.com/free#cdn -->
    <link rel="stylesheet" href="https://cdn.linearicons.com/free/1.0.0/icon-font.min.css">

    <!-- bootstrap css -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- bootstrap js -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }
        #wrap {
            width: 60%;
            max-width: 800px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            margin-top: 10px;
            display: block;
        }
        input[type="text"], input[type="date"], textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f8f8f8;
        }
        textarea {
            min-height: 100px;
        }
        .buttons {
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
        }
        .buttons button {
            background-color: #00CE7B;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .buttons button:hover {
            background-color: #00b56a;
        }
        .list-btn {
            background-color: #6c757d;
        }
        .list-btn:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<div id="wrap" class="form-container">
    <h1>동행 게시판 글쓰기</h1>
    <form action="/acc-board/write" method="post">

        <label for="location">장소</label>
        <input type="text" id="location" name="location" required>

        <label for="startDate">시작일</label>
        <input type="date" id="startDate" name="startDate" required>

        <label for="endDate">종료일</label>
        <input type="date" id="endDate" name="endDate" required>

        <label for="title">제목</label>
        <input type="text" id="title" name="title" required>

        <label for="content">내용</label>
        <textarea id="content" name="content" maxlength="200" required></textarea>

        <div class="buttons">
            <button class="list-btn" type="button" onclick="window.location.href='/acc-board/list'">목록</button>
            <button type="submit">글쓰기</button>
        </div>
    </form>
</div>

<script>
    CKEDITOR.replace('content');
</script>

</body>
</html>
