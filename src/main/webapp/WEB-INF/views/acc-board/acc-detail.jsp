<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>

    <%@ include file="../include/static-head.jsp" %>

    <link rel="stylesheet" href="/assets/css/detail.css">

</head>
<body>

<%--    <h2>${abd.title}</h2>--%>
<%--    <p>${abd.content}</p>--%>
<%--    <p>Writer: ${abd.writer}</p>--%>
<%--    --%>
<%--    <p>작성일자: ${abd.createdAt}</p>--%>
<%--    <p>수정일자: ${abd.updatedAt}</p>--%>
<%--    <p>동행시작일 : ${abd.startDate}</p>--%>
<%--    <p>동행종료일: ${abd.endDate}</p>--%>
<%--    <p>Views: ${abd.viewCount}</p>--%>

    <div id="wrap" class="form-container" data-bno="${abd.boardId}">
        <h1>${abd.boardId}번 게시물</h1>
        <h2># 작성일자: ${abd.createdAt}</h2>
        <label for="writer">작성자</label>
        <input type="text" id="writer" name="writer" value="${abd.writer}" readonly>
        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${abd.title}" readonly>
        <label for="title">동행시작일</label>
        <input type="text" id="start-date" name="title" value="${abd.startDate}" readonly>
        <label for="title">동행종료일</label>
        <input type="text" id="end-date" name="title" value="${abd.endDate}" readonly>
        <label for="content">내용</label>
        <div id="content">${abd.content}</div>


        <div class="buttons">
            <div class="reaction-buttons">
                <button id="like-btn">
                    <i class="fas fa-thumbs-up"></i> 좋아요
<%--                    <span id="like-count">${abd.likeCount}</span>--%>
                </button>
            </div>

            <button class="edit-btn" type="button" onclick="window.location.href='/acc-board/modify?bno=${abd.boardId}'">수정</button>

            <button
                    class="list-btn"
                    type="button"
                    onclick="window.location.href=${ref}"
            >
                목록
            </button>
        </div>
    </div>


    <script type="module" src="/assets/js/reply.js"></script>

    <script>


        const userReaction = '${b.userReaction}';
        updateReactionButtons(userReaction);

        // 서버에 좋아요, 싫어요 요청을 보내는 함수
        async function sendReaction(reactionType) {
            console.log(reactionType);

            const bno = document.getElementById('wrap').dataset.bno;
            const res = await fetch(`/board/\${reactionType}?bno=\${bno}`);

            if (res.status === 403) {
                const msg = await res.text();
                alert(msg);
                return;
            }

            const { likeCount, dislikeCount, userReaction } = await res.json();

            document.getElementById('like-count').textContent = likeCount;
            document.getElementById('dislike-count').textContent = dislikeCount;


            // console.log(json);
            // 버튼 활성화 스타일 처리
            updateReactionButtons(userReaction);
        }

        // 좋아요, 싫어요 버튼 배경색 변경
        function updateReactionButtons(userReaction) {
            const $likeBtn = document.getElementById('like-btn');
            const $dislikeBtn = document.getElementById('dislike-btn');

            const ACTIVE = 'active';
            // 좋아요 버튼이 눌렸을 경우
            if(userReaction === 'LIKE') {
                $likeBtn.classList.add(ACTIVE);
                $dislikeBtn.classList.remove(ACTIVE);
            } else if (userReaction === 'DISLIKE') { // 싫어요 버튼이 눌렸을 경우
                $likeBtn.classList.remove(ACTIVE);
                $dislikeBtn.classList.add(ACTIVE);
            } else { // 둘 다 안눌렀을 경우
                $likeBtn.classList.remove(ACTIVE);
                $dislikeBtn.classList.remove(ACTIVE);
            }
        }

        // 좋아요 클릭 이벤트
        document.getElementById('like-btn').addEventListener('click', e => {
            console.log('like!');
            sendReaction('like');
        });

        // 싫어요 클릭 이벤트
        document.getElementById('dislike-btn').addEventListener('click', e => {
            console.log('dislike!');
            sendReaction('dislike');
        });
    </script>


</body>
</html>