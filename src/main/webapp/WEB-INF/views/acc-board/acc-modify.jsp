<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <%@ include file="../include/static-head.jsp" %>

    <!-- ck editor -->
    <script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>

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
    <h1>게시글 수정</h1>
    <form action="/acc-board/modify" method="post">
<%--        <input type="hidden" name="boardId" value="${abm.boardId}">--%>
<%--        <label for="account">계정명</label>--%>
<%--        <input type="text" id="account" name="account" value="${abm.account}" required>--%>
<%--        <label for="categoryId">게시판유형</label>--%>
<%--        <input type="text" id="categoryId" name="categoryId" value="${abm.categoryId}" required>--%>
<%--        <label for="writer">작성자</label>--%>
<%--        <input type="text" id="writer" name="writer" value="${abm.writer}" required>--%>
        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${abm.title}" required>
        <label for="content">내용</label>
        <textarea id="content" name="content" required>${abm.content}</textarea>
        <label for="location">장소</label>
        <input type="text" id="location" name="location" value="${abm.location}" required>
        <label for="startDate">시작일</label>
        <input type="date" id="startDate" name="startDate" value="${abm.startDate}" required>
        <label for="endDate">종료일</label>
        <input type="date" id="endDate" name="endDate" value="${abm.endDate}" required>

        <div class="buttons">
            <button class="list-btn" type="button" onclick="cancelModify(${abm.boardId})">취소</button>
            <button type="submit">수정하기</button>
        </div>
    </form>
</div>

<script>
    CKEDITOR.replace('content');

    // 취소버튼 클릭 시 돌아갈 url 주소
    function cancelModify(boardId) {
        window.location.href = '/acc-board/detail?bno=' + boardId;
    }
</script>

</body>
</html>
