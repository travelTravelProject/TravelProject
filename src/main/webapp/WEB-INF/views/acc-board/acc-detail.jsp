<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>

    <%@ include file="../include/static-head.jsp" %>

    <link rel="stylesheet" href="/assets/css/detail.css">

</head>
<body>

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
                </button>
            </div>

            <button class="edit-btn" type="button" onclick="window.location.href='/acc-board/modify?bno=${abd.boardId}'">수정</button>

            <button
                    class="list-btn"
                    type="button"
<%--                    onclick="window.location.href='/acc-board/list'"--%>
                    onclick="window.location.href='${ref}'"

            >
                목록
            </button>
        </div>

        <!-- 댓글 영역 -->
        <div id="replies" class="row">
            <div class="offset-md-1 col-md-10">
            <!-- 댓글 쓰기 영역 -->
            <div class="card">
                <div class="card-body">
                <div class="row">
                    <div class="col-md-9">
                    <div class="form-group">
                        <label for="newReplyText" hidden>댓글 내용</label>
                        <textarea
                        rows="3"
                        id="newReplyText"
                        name="replyText"
                        class="form-control"
                        placeholder="댓글을 입력해주세요."
                        ></textarea>
                    </div>
                    </div>
                    <div class="col-md-3">
                    <div class="form-group">
                        <label for="newReplyWriter" hidden>댓글 작성자</label>
                        <input
                        id="newReplyWriter"
                        name="replyWriter"
                        type="text"
                        class="form-control"
                        placeholder="작성자 이름"
                        style="margin-bottom: 6px"
                        />
                        <button
                        id="replyAddBtn"
                        type="button"
                        class="btn btn-dark form-control"
                        >
                        등록
                        </button>
                    </div>
                    </div>
                </div>
                </div>
            </div>
            <!-- end reply write -->

            <!--댓글 내용 영역-->
            <div class="card">
                <!-- 댓글 내용 헤더 -->
                <div class="card-header text-white m-0" style="background: #343a40">
                <div class="float-left">댓글 (<span id="replyCnt">0</span>)</div>
                </div>

                <!-- 댓글 내용 바디 -->
                <div id="replyCollapse" class="card">
                <div id="replyData">
                    <!--
                    < JS로 댓글 정보 DIV삽입 >
                    -->
                    <!-- 대댓글 내용 바디 -->
                    <div id="nestedReplyCollapse" class="card">
                        <div id="nestedReplyData">
                        </div>
                    </div>
                </div>
                </div>


            </div>

            <!-- 대댓글 쓰기 영역 -->
            <!-- <div id="nestedReplyWriteSection" class="Nestedcard hidden">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="form-group">
                                <label for="newNestedReplyText" hidden>대댓글 내용</label>
                                <textarea
                                rows="3"
                                id="newNestedReplyText"
                                name="nestedReplyText"
                                class="form-control"
                                placeholder="대댓글을 입력해주세요."
                                ></textarea>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="newNestedReplyWriter" hidden>대댓글 작성자</label>
                                <input
                                id="newNestedReplyWriter"
                                name="nestedReplyWriter"
                                type="text"
                                class="form-control"
                                placeholder="작성자 이름"
                                style="margin-bottom: 6px"
                                />
                                <button
                                id="nestedReplyAddBtn"
                                type="button"
                                class="btn btn-dark form-control"
                                >
                                등록
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div> -->
            <!-- end nested reply write -->

            <!-- 댓글 페이징 영역 -->
            <ul class="pagination justify-content-center">
                <!--
                < JS로 댓글 페이징 DIV삽입 >
                -->
            </ul>
            </div>
        </div>
        <!-- end reply content -->
        </div>
        <!-- end replies row -->

        <!-- 로딩 스피너 -->
        <div class="spinner-container" id="loadingSpinner">
        <div class="spinner-border text-light" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>  
        </div>
    </div>

    

    <script type="module" src="/assets/js/acc-reply.js"></script>

    <script>

        <%--const userReaction = '${b.userReaction}';--%>
        // updateReactionButtons(userReaction);

        // 서버에 좋아요 요청을 보내는 함수
        async function sendLike() {
            console.log();

            const bno = document.getElementById('wrap').dataset.bno;
            const res = await fetch(`/acc-board/like?bno=\${bno}`);

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