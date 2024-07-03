<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>${abd.title}</title>

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
        word-break: break-word;
    }
    #inner-wrapper .content {
        font-size: 0.9em;
    }
    #inner-wrapper .text {
        margin-bottom: 20px;
        font-size: 0.9em;
    }
    .card-img {
        height: 150px;
        background-color: #00CE7B;
    }
    #title .main-title{
        font-size: 1.2em;
        font-weight: bold;
    }
    #title .sub-title{
        font-size: 0.8em;
        color: #999;
        padding: 10px 0px 20px;
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
        margin-bottom: 20px;
    }
    #detail-travel .travel-info .fas {
        color: #999;
    }
    #detail-travel {
        font-size: 0.9em;
    }
    .buttons {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 20px auto;
    }
    .reaction-buttons {
        flex: 1;
    }
    .action-buttons {
        display: flex;
        gap: 10px;
    }
    .reaction-buttons button {
        background-color: transparent;
        color: #888;
        border: 2px solid transparent;
        padding: 10px 20px;
        border-radius: 10px;
        cursor: pointer;
    }
    .reaction-buttons button.active {
        background-color: #00CE7B;
        color: #fff;
        animation: bookmark-animation 0.5s ease-in-out;

    }

    @keyframes bookmark-animation {
        0% {
            transform: scale(1);
            opacity: 0.8;
        }
        50% {
            transform: scale(1.2);
            opacity: 1;
        }
        100% {
            transform: scale(1);
            opacity: 1;
        }
    }

    /* 여기부터 모달 스타일 */
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0, 0, 0);
        background-color: rgba(0, 0, 0, 0.4);
        padding-top: 60px;
    }
    .modal-content {
        background-color: #fefefe;
        margin: 5% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        max-width: 400px;
        text-align: center;
    }
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }
    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
    .modal .btn {
        margin: 10px;
    }

</style>

</head>
<body>

<div id="wrap" class="form-container" data-bno="${abd.boardId}">
    <div class="card-img">
        <img src="#" alt="대표이미지">
    </div>
    <div id="inner-wrapper">
        <div id="title">
            <div class="main-title">
                ${abd.title}
            </div>
            <div class="sub-title">
                <scan class="view-count">조회수 ${abd.viewCount}</scan>
            </div>
        </div>
        <div id="detail-travel">
            <p class="title">여행 일정</p>
            <div class="travel-info">
                <div class="travel-destination">
                    <i class="fas fa-map-marker-alt"></i> &nbsp;${abd.location}
                </div>
                <div class="travel-period">
                    <i class="fas fa-calendar"></i> &nbsp;${abd.startDate} - ${abd.endDate} (${period}일)
                </div>

            </div>
            <p class="content">여행 소개</p>
            <div class="text">
                ${abd.content}
            </div>
        </div>

        <div class="buttons">
            <div class="reaction-buttons">
                <button id="bookmark-btn" class="bookmark-button ${bookmark?active:''}}">
                    <i class="far fa-bookmark"></i>
                    <i class="fas fa-bookmark" style="display: none;"></i>
                </button>
            </div>
            <div class="action-buttons">
                <c:if test="${isOwnerOrAdmin}">
                    <button class="del-btn btn btn-danger" type="button">삭제</button>
                    <button class="edit-btn btn btn-secondary" type="button"
                            onclick="window.location.href='/acc-board/modify?bno=${abd.boardId}'">수정
                    </button>
                </c:if>
                <button class="list-btn btn btn-secondary" type="button" onclick="window.location.href='${ref}'">목록
                </button>
            </div>
        </div>


        <%--  댓글영역  --%>
        <div>댓글</div>

    </div>
</div>

<!-- 삭제 확인 모달 -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>정말로 삭제하시겠습니까?</p>
        <button id="confirmDelete" class="btn btn-danger">삭제</button>
        <button id="cancelDelete" class="btn btn-secondary">취소</button>
    </div>
</div>

    <script>
        // 페이지 로드 시 북마크 상태 확인 및 버튼 업데이트
        document.addEventListener('DOMContentLoaded', async function () {
            const bno = document.getElementById('wrap').dataset.bno;

            const res = await fetch(`/acc-board/bookmark/status?boardId=\${bno}`);
            const isBookmarked = await res.json();
            updateBookmarkButton(isBookmarked);
        });

        // 북마크 요청을 보내는 함수
        async function toggleBookmark() {
            const bno = document.getElementById('wrap').dataset.bno;
            console.log("bno: ", bno);
            const res = await fetch(`/acc-board/bookmark?boardId=\${bno}`, {
                method: 'GET'
            });

            if (res.status === 403) {
                window.location.href = '/sign-in?message=login-required&redirect='+ window.location.pathname + `?bno=\${bno}`;  // 로그인 페이지로 리다이렉트
                return;
            }

            if (!res.ok) {
                const errorMsg = await res.text();
                alert(errorMsg);
                return;
            }

            const { bookmarkCount, userBookmark } = await res.json();
            updateBookmarkButton(userBookmark);
        }

        // 북마크 버튼 스타일 업데이트 함수
        function updateBookmarkButton(userBookmark) {
            const bookmarkBtn = document.getElementById('bookmark-btn');
            const farIcon = bookmarkBtn.querySelector('.far.fa-bookmark');
            const fasIcon = bookmarkBtn.querySelector('.fas.fa-bookmark');

            // 애니메이션을 위한 클래스 초기화
            bookmarkBtn.classList.remove('bookmark-animation');

            if (userBookmark) {
                bookmarkBtn.classList.add('active');
                farIcon.style.display = 'none';
                fasIcon.style.display = 'inline';
            } else {
                bookmarkBtn.classList.remove('active');
                farIcon.style.display = 'inline';
                fasIcon.style.display = 'none';
            }

            // 애니메이션 효과를 위해 약간의 지연 후 클래스 추가
            setTimeout(() => {
                bookmarkBtn.classList.add('bookmark-animation');
            }, 10);
        }

        // 북마크 버튼 클릭 이벤트 리스너 추가
        document.getElementById('bookmark-btn').addEventListener('click', toggleBookmark);

        // 삭제 스크립트
        const modal = document.getElementById('deleteModal'); // 모달창
        const span = document.getElementsByClassName('close')[0];
        const confirmDelete = document.getElementById('confirmDelete'); // 모달 삭제 확인 버튼
        const cancelDelete = document.getElementById('cancelDelete'); // 모달 삭제 취소 버튼

        // 삭제버튼 클릭이벤트 - 모달창
        document.querySelector('.del-btn').addEventListener('click', function () {
            modal.style.display = 'block';
        });

        // 모달 창 닫기 이벤트 (X 버튼)
        span.onclick = function () {
            modal.style.display = 'none';
        };

        // 모달 창 닫기 이벤트 (취소 버튼)
        cancelDelete.onclick = function () {
            modal.style.display = 'none';
        };

        // 모달 창 - 게시글 삭제 이벤트
        confirmDelete.addEventListener('click', function() {
            const bno = document.getElementById('wrap').dataset.bno;
            window.location.href = `/acc-board/delete?boardId=\${bno}`;
        });

    </script>




</body>
</html>