<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fake Board</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="script.js"></script>
</head>
<body>
    <h1>Fake Board</h1>
    <div id="board">
        <h2>게시글 제목</h2>
        <p>게시글 내용...</p>
    </div>
    <div id="reply-section">
        <h3>댓글</h3>
        <textarea id="replyText" placeholder="댓글을 입력하세요"></textarea>
        <input type="text" id="replyAuthor" placeholder="작성자">
        <button onclick="postReply()">댓글 등록</button>
        <div id="replies"></div>
    </div>
</body>
</html>
