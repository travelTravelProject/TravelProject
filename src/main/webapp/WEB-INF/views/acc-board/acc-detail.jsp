<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${abd.title}</title>

    <%@ include file="../include/static-head.jsp" %>

    <!--     <link rel="stylesheet" href="/assets/css/detail.css"> -->
    <!-- reset -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">

    <!-- fontawesome css: https://fontawesome.com -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">

    <!-- https://linearicons.com/free#cdn -->
    <link rel="stylesheet" href="https://cdn.linearicons.com/free/1.0.0/icon-font.min.css">

    <%--    폰트--%>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">

    <!-- bootstrap css -->
    <!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"> -->

    <!-- bootstrap js -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script> -->

    <style>
        * {
            box-sizing: border-box;
        }
        body {
            font-family: "Noto Sans KR", sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }
        #wrap {
            width: 60%;
            /*max-width: 800px;*/
            background-color: #fff;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
            margin-top: 10px;
            margin-bottom: 20px;
            font-size: 0.9em;
            line-height: 1.5;
        }
        .card-img {
            width: 100%;
            padding-bottom: 30%;
            position: relative;
            /*overflow: hidden;*/
            cursor: pointer;
        }
        .card-img img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover; /* 이미지를 잘라서 채움 */
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
        #detail-travel .travel-info .travel-destination {
            margin-bottom: 5px;
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
        .buttons .action-buttons .btn {
            background-color: #4facfe;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .buttons .action-buttons .btn:hover {
            background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
        }

        .buttons .action-buttons #del-btn {
            background-image: linear-gradient(to right, #ff6b6b 0%, #ff4757 100%);
        }
        .buttons .action-buttons #del-btn:hover {
            background-image: linear-gradient(to right, #e63946 0%, #d90429 100%);
        }

        .reaction-buttons button.active {
            background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            color: #fff;
            animation: bookmark-animation 0.5s ease-in-out;
            border: transparent;
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

        /* 여행장 정보 스타일 */
        .acc-master {
            font-size: 0.81em;
        }
        .acc-master-section {
            background-color: #f8f8f8;
            font-size: 0.9em;
            padding: 15px 10px;
            border-radius: 10px;
            margin-top: 10px;
            margin-bottom: 20px;
        }
        .info-card {
            display: flex;
            align-items: center;
            /*margin-bottom: 10px;*/
        }
        .profile-img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            overflow: hidden;
            margin-right: 10px;
        }
        .profile-img img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .simple-info {
            display: flex;
            flex-direction: column;
        }
        .writer {
            font-size: 0.9em;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .simple-info-detail {
            font-size: 0.9em;
            color: #999;
        }
        .location-description {
            margin-top: 15px;
            font-size: 0.9em;
            color: #666;
            border-top: 1px solid #ccc;
        }



        /* 모달 스타일 */
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
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 30px 30px 20px 30px;
            border: 1px solid #888;
            width: 80%;
            max-width: 400px;
            position: relative;
            text-align: center;
        }
        .close {
            color: #aaa;
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .modal .btn {
            margin: 10px;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .modal-buttons {
            display: flex;
            justify-content: center;
            gap: 10px; /* 버튼 간의 간격을 설정 */
        }
        #deleteModal .modal-content p {
            font-size: 1.5em;
            font-weight: bold;
            margin-top: 5px;
            margin-bottom: 20px;
        }
        #deleteModal .confirmDelete {
            background-image: linear-gradient(to right, #ff6b6b 0%, #ff4757 100%);
        }
        #deleteModal .confirmDelete:hover {
            background-image: linear-gradient(to right, #e63946 0%, #d90429 100%);
        }
        #deleteModal .cancelDelete {
            background: #999999;
        }
        #deleteModal .cancelDelete:hover {
            background: #888888;
        }

        /* 이미지 모달 스타일 */
        .img-modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.9);
            justify-content: center;
            align-items: center;
        }
        .img-modal-content {
            max-width: 80%;
            max-height: 80%;
        }

        /* 댓글 css 영역 */

        /* 댓글 프로필 */
        .profile-box {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            overflow: hidden;
            margin: 10px 0 0 0;
        }

        .profile-box img {
            width: 60px;
            height: 50px;
        }


        .reply-reply-button:hover,
        a.btn:hover,
        .nested-reply-add-btn:hover,
        .nestedReplyModifyBtn:hover,
        .nestedReplyCancelBtn:hover,
        .form-control1:hover{
        background-color: #4facfe;
        background-image: linear-gradient(45deg, #4facfe 0%, #00f2fe 100%);
        }

        #replyContent {
            padding: 0;
        }

        #replies {
            /* border: 1px solid blue; */
            padding: 0 20px;
        }

        .card .float-left {
            color: black;
            font-weight: 700;
            font-size: 16px;
            padding: 10px 0;
        }


        .user-block {
            display: flex;
            align-items: center;
        }

        .reply-head {
            display: flex;
            align-items: center;
            margin: 20px 0 0 20px;
        }

        .reply-body {
            margin-left: 15px;
            line-height: 20px;
            margin-top: 5px;
        }

        .reply-body .col-md-3 {
            width: 100px;
            font-weight: 700;
        }

        .reply-body .text-right {
            width: 100%;
            margin-top: 2px;
            opacity: 0.8;
        }
        .row .text-right {
            /* display: flex; */
            width: 100%;
        }

        .row .col-md-9 {
            margin: 5px 0 20px 80px ;
        }

        /* .col-md-9 .form-control {
            width: 100px;
        } */

        .modDelBtn {
            position: relative;
            top: -36px;
            left: 880px;
            width: 590px;
        }

        .modDelBtn .btn-sm {
            font-size: 16px;
            border: none;
            display: inline-block;
            text-decoration: none;
            color: black;
            width: 50px;
            height: 35px;
            text-align: center;
            padding-top: 10px;
            border-radius: 5px;
        
        }

        .reply-reply-write {
            opacity: 0.8;
            margin-left: 90px;
        }
        .reply-reply-write .fa-comment {
            padding-right: 5px;
        }

        .reply-reply-button {
            display: inline-block;
            width: 65px;
            height: 30px;
            border: none;
            outline: none;
            background-color: inherit ;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            margin-top: 6px;
        }
        .rows .col-md-3 {
            display: flex;
        }
        .rows .col-md-90 {
            display: flex;
        }
        .rows .col-md-90 .form-group .form-control {
            width: 600px;
        }
        .rows .col-md-3 .form-group {
            display: flex;
        }
        .rows .col-md-3 .form-group .form-control {
            margin: 6px 0;
            width: 600px;
        }
        .rows .col-md-3 .col-md-90 .form-group .form-control1 {
            width: 50px;
        }



        /* 대댓글 css 영역 */
        .nested-reply-data {
            width: 100%;
        }
        .nestReply-head {
            align-items: center;
            line-height: 20px;
            margin-top: 5px;
            padding-left: 0;
            margin-left: 20px;
        }
        .nestReply-body .col-md-3 {
            font-weight: 700;
            width: 100px;
        }
        .Nestedcard .card-body .row .col-md-3 {
            padding-left: 70px;
        }

        .Nestedcard .card-body .row .col-md-3 .col-md-9 {
            display: flex;
            margin: 5px 0 20px 0;
        }
        .Nestedcard .card-body .row .col-md-3 .col-md-9 .form-group {
            display: flex;
        }
        .Nestedcard .card-body .row .col-md-3 .col-md-9 .form-group .form-control {
            width: 530px;
            height: 38px;
        }
        /* 대댓글 카드 스타일 */
        .nested-reply-card {
            width: 88%;
            border-radius: 5px;
            margin: 10px 0 10px 70px; /* 왼쪽 여백을 추가하여 대댓글을 안쪽으로 이동 */
            padding: 10px;
        }

        /* 대댓글 작성자 및 시간 스타일 */
        .nested-reply-card .user-block {
            display: flex;
            justify-content: space-between;
        }

        /* 대댓글 내용 스타일 */
        .nested-reply-card .reply-content {
            margin-top: 5px;
        }

        .nestReply-head {
            display: flex;
        }

        .reply-content .nestModDel {
            position: relative;
            left: 803px;
            justify-content: end;
            margin-right: 9px;
        }
        .reply-content .text-right .btn-sm {
            font-size: 16px;
            border: none;
            display: inline-block;
            width: 50px;
            height: 35px;
            padding-top: 10px;
            margin-right: 1px;
            text-align: center;
            text-decoration: none;
            color: black;
            font-weight: 500;
            border-radius: 5px;
        }
        .nestedReplyModBtn {
            margin-right: 8px;
        }

        .hidden {
            display: none;
        }

        /* 버튼 클릭 시 애니메이션 효과 */
        button:active {
            transform: scale(0.95); /* 버튼을 약간 줄어들게 함 */
        }

    </style>

</head>
<body>
<%@ include file="../include/sub_header.jsp" %>

<div id="wrap" class="form-container" data-bno="${abd.boardId}">
    <div class="card-img">
        <c:choose>
            <c:when test="${abd.imagePath != null}">
                <img src="${abd.imagePath}" alt="대표이미지">
            </c:when>
            <c:otherwise>
                <img src="/assets/img/accBoardDefaultImg.webp" alt="기본이미지">
            </c:otherwise>
        </c:choose>
    </div>
    <div id="inner-wrapper">
        <div id="title">
            <div class="main-title">
                ${abd.title}
            </div>
            <div class="sub-title">
                <scan class="counter">조회수 ${abd.viewCount} · 댓글 ${abd.replyCount}</scan>
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
            <div class="text" style="white-space: pre-wrap;">${abd.content}</div>
        </div>

        <!-- 여행장 정보 -->
        <p class="acc-master">여행장</p>
        <div class="acc-master-section">
            <div class="info-card">
                <div class="profile-img">
                    <img src="${abd.profileImage}" alt="여행장 대표 이미지">
                </div>
                <div class="simple-info">
                    <div class="writer">
                        <strong>${abd.writer}</strong>
                    </div>
                    <div class="simple-info-detail">
                        ${abd.ageGroup} ·
                        <c:choose>
                            <c:when test="${abd.gender == 'M'}">남자</c:when>
                            <c:when test="${abd.gender == 'F'}">여자</c:when>
                        </c:choose>
                            <c:if test="${not empty abd.mbti}"> · ${abd.mbti}
                            </c:if>
                    </div>
                </div>
            </div>
                <div class="location-description">
                    <br> ${abd.oneLiner}
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
                    <button id="del-btn" class="btn del-btn" type="button">삭제</button>
                    <button class="btn edit-btn" type="button"
                            onclick="window.location.href='/acc-board/modify?boardId=${abd.boardId}'">수정
                    </button>
                </c:if>
                <button class="btn list-btn" type="button" onclick="window.location.href='${ref}'">목록
                </button>
            </div>
        </div>

    </div>

    <!-- 댓글 영역 -->

    <div id="replies" class="row">
        <div class="offset-md-1 col-md-10" style="margin: 0;width: 100%;">

            <!--댓글 내용 영역-->
            <div class="card">
                <!-- 댓글 내용 헤더 -->
                <div class="card-header text-white m-0" style="color: black;">
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


            <!-- 댓글 쓰기 영역 -->
            <div class="card">
            <div class="card-body">
                <c:if test="${user == null}">
                    <a href="/sign-in" style="text-decoration-line: none;font-weight: 700;">댓글은 로그인 이후에 작성해주세요! :)</a>
                </c:if>
                <c:if test="${user != null}">
                <div class="rows"> 
                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="newReplyWriter" hidden>댓글 작성자</label>
                            <input
                            id="newReplyWriter"
                            name="replyWriter"
                            type="text"
                            value="${user.nickname}"
                            class="form-control"
                            placeholder="작성자 이름"
                            style="width: 100px;margin-bottom: 6px;"
                            readonly
                            />
                        </div>
                        <div class="col-md-90">
                            <div class="form-group">
                                <label for="newReplyText" hidden>댓글 내용</label>
                                <input
                                rows="3"
                                id="newReplyText"
                                name="replyText"
                                class="form-control"
                                placeholder="댓글을 입력해주세요."
                                style="width: 800px;"
                                />
                            </div>
                            <button
                            id="replyAddBtn"
                            type="button"
                            class="btn btn-dark form-control1"
                            style="width: 60px;
                                    margin: 6px 0;
                                    background-color: #d9d9d9;
                                    border: none;"
                            >
                            등록
                            </button>
                        </div>
                    </div>
                </div>
                </c:if>
            </div>
            </div>
            <!-- end reply write -->

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

        <!-- 댓글 삭제모달 -->
        <div id="deleteConfirmModal" class="modal">
            <div class="modal-content">
            <span class="close">&times;</span>
            <p>정말 삭제할까요?</p>
            <button id="confirmDeleteBtn">삭제</button>
            <button id="cancelDeleteBtn">취소</button>
            </div>
        </div>

        <div class="spinner-container" id="loadingSpinner">
            <div class="spinner-border text-light" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
        </div>
  
    </div>
</div>  



<!-- 이미지 모달 -->
<div id="imgModal" class="img-modal">
    <img src="/assets/img/accBoardDefaultImg.webp" class="img-modal-content" id="modalImage">
</div>
  
<!-- 게시글 삭제 확인 모달 -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>정말로 삭제하시겠습니까?</p>
        <div class="modal-buttons">
            <button class="btn confirmDelete">삭제</button>
            <button class="btn cancelDelete">취소</button>
        </div>
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
    const $modal = document.getElementById('deleteModal'); // 모달창
    const $span = document.getElementsByClassName('close')[0];
    const $confirmDelete = document.querySelector('.confirmDelete'); // 모달 삭제 확인 버튼
    const $cancelDelete = document.querySelector('.cancelDelete'); // 모달 삭제 취소 버튼

    // 삭제버튼 클릭이벤트 - 모달창
    document.querySelector('.del-btn')?.addEventListener('click', function () {
        $modal.style.display = 'flex';
    });

    // 모달 창 닫기 이벤트 (X 버튼)
    $span.onclick = function () {
        $modal.style.display = 'none';
    };

    // 모달 창 닫기 이벤트 (취소 버튼)
    $cancelDelete.onclick = function () {
        $modal.style.display = 'none';
    };

    // 모달 창 - 게시글 삭제 이벤트
    $confirmDelete.addEventListener('click', function() {
        const bno = document.getElementById('wrap').dataset.bno;
        // console.log('bno: ', bno)
        window.location.href = `/acc-board/delete?boardId=\${bno}`;
    });

    // 이미지 클릭 시 전체 화면 모달 열기
    // 이미지 모달창
    const $imgModal = document.querySelector('.img-modal');
    // 모달창 내부 img태그
    const $modalImg = document.getElementById('modalImage');
    // 타겟 이미지 태그
    const $targetImg = document.querySelector('.card-img img');

    $targetImg.addEventListener('click', () => {
        console.log('사진클릭');
        $imgModal.style.display = 'flex';
        $modalImg.src = $targetImg.src;
    });

    // 모달 사진 클릭 시 모달창 닫기
    $imgModal.addEventListener('click', e => {
        $imgModal.style.display = 'none';
        $modalImg.src = "";
    });

</script>
<script type="module" src="/assets/js/acc-reply.js"></script>


</body>
</html>

