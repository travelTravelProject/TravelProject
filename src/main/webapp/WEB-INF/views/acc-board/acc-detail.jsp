<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 상세보기</title>

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
            background-color: #fff;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            height: 100vh;
        }
        #wrap p {
            font-weight: bold;
            margin-top: 10px;
            display: block;
        }
        #wrap input[type="text"], #wrap #content {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f8f8f8;
        }
        #inner-wrapper {
            width: 100%;
            padding: 20px;
        }
        #inner-wrapper .content {
            font-size: 0.9em;
        }
        #inner-wrapper .text {
            margin-bottom: 30px;
        }
        .card-img {
            height: 150px;
            background-color: #00CE7B;
        }
        #title .main-title{
            font-size: 1.5em;
            font-weight: bold;
        }
        #title .sub-title{
            font-size: 0.9em;
            color: #999;
            padding: 10px 0px 30px;
        }
        #detail-travel .title {
            font-size: 0.9em;
            font-weight: bold;
        }
        #detail-travel .travel-info {
            margin-top: 10px;
            font-size: 0.9em;
            background-color: #f8f8f8;
            padding: 15px 10px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        #detail-travel {
            font-size: 0.9em;
        }
        .buttons {
            margin-top: 20px;
        }
        .buttons .edit-btn, .buttons .list-btn {
            margin-right: 10px;
            margin-top: 10px;
        }
        .reaction-buttons button {
            background-color: #00CE7B;
            color: #ddd;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .reaction-buttons button:hover {
            background-color: #00b56a;
        }
    </style>

</head>
<body>

    <div id="wrap" class="form-container" data-bno="${abd.boardId}">
        <div class="card-img">
            <img src="#" alt="대표이미지">
        </div>
<%--        <h1>${abd.boardId}번 게시물</h1>--%>
<%--        <h2># 작성일자: ${abd.createdAt}</h2>--%>
<%--        <label for="writer">작성자</label>--%>
<%--        <input type="text" id="writer" name="writer" value="${abd.writer}" readonly>--%>
        <div id="inner-wrapper">
            <div id="title">
                <div class="main-title">
                    ${abd.title}
                </div>
                <div class="sub-title">
                    <scan class="view-count">조회수 ${abd.viewCount}</scan>
                </div>
            </div>
            <%--    <div id="detail-title">--%>
            <%--        <scan>#{abd.title}</scan>--%>
            <%--    </div>--%>
            <div id="detail-travel">
                <p class="title">여행 일정</p>
                <div class="travel-info">
                    <div class="travel-period">
                        <span class="lnr lnr-calendar-full"></span> ${abd.startDate} - ${abd.endDate}
                    </div>
                    <div class="travel-destination">
                        <span class="lnr lnr-map-marker"></span> 장소
                    </div>

                </div>
<%--                <input type="text" id="travel-period" name="travel-period" value="${abd.startDate} - ${abd.endDate}" readonly>--%>
                <p class="content">여행 소개</p>
                <div class="text">
                    ${abd.content}
                </div>
            </div>

            <div class="buttons">
<%--                <div class="reaction-buttons">--%>
<%--                    <button id="like-btn">--%>
<%--                        <i class="fas fa-thumbs-up"></i> 좋아요--%>
<%--                    </button>--%>
<%--                </div>--%>

                <button class="edit-btn btn btn-secondary" type="button"
                        onclick="window.location.href='/acc-board/modify?bno=${abd.boardId}'">수정
                </button>

                <button class="list-btn btn btn-secondary" type="button" onclick="window.location.href='${ref}'">목록
                </button>
        </div>
        </div>

    <script>
        // 서버에 좋아요 요청을 보내는 함수
        async function sendLike() {
            const bno = document.getElementById('wrap').dataset.bno;
            const res = await fetch(`/acc-board/like?bno=${bno}`);

            if (res.status === 403) {
                const msg = await res.text();
                alert(msg);
                return;
            }

            const { likeCount, userReaction } = await res.json();
            const likeCountElement = document.getElementById('like-count');
            if (likeCountElement) {
                likeCountElement.textContent = likeCount;
            }

            // 버튼 활성화 스타일 처리
            updateReactionButtons(userReaction);
        }

        // 좋아요 버튼 배경색 변경
        function updateReactionButtons(userReaction) {
            const $likeBtn = document.getElementById('like-btn');

            const ACTIVE = 'active';
            // 좋아요 버튼이 눌렸을 경우
            if(userReaction === 'LIKE') {
                $likeBtn.classList.add(ACTIVE);
            } else { // 안눌렀을 경우
                $likeBtn.classList.remove(ACTIVE);
            }
        }

        // 좋아요 클릭 이벤트
        document.getElementById('like-btn').addEventListener('click', sendLike);
    </script>


</body>
</html>