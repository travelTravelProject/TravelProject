<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>

    <%@ include file="../include/static-head.jsp" %>

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


    <title>동행게시판</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
        }
        a {
            color: inherit;
            text-decoration: none;
        }
        .wrap {
            width: 60%;
            margin: 0 auto;
            padding: 20px 20px 60px 20px;
            background-color: #fff;
        }
        .search input {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
        }
        .card-wrapper {
            border: 1px solid #ddd;
            border-radius: 10px;
            margin-bottom: 20px;
            padding: 10px;
        }
        .card-post {
            display: flex;
            width: 100%;
            cursor: pointer;
        }
        .card-content-wrapper {
            flex: 1;
        }
        .card-details-top {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
        }
        .card-text {
            display: flex;
            flex-direction: column;
            flex: 1;
            margin-right: 20px;
            word-break: break-word;
        }
        .card-title {
            font-size: 1.2em;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .card-content {
            margin-top: 10px;
            font-size: 0.9em;
            color: #666;
            margin-bottom: 10px;
        }
        .card-img {
            width: 100px;
            height: 100px;
            flex-shrink: 0;
            /*background-color: #00CE7B;*/
            background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            border-radius: 10px;
        }
        .card-img img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 10px;
        }
        .card-details-bot1 {
            font-size: 0.9em;
            color: #666;
            margin-top: 10px;
        }
        .card-details-bot2 {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 0.9em;
            color: #666;
        }
        .card-details-bot2 .counter {
            margin-left: auto;
        }
        .add-btn-box {
            width: 60%;
            position: fixed;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            display: flex;
            justify-content: center;
            background-color: #fff;
            padding: 10px 20px;
        }
        .add-btn {
            width: 100%;
            text-align: center;
            padding: 10px 0;
            /*background-color: #00CE7B;*/
            background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            /*background-color: #00f2fe;*/
            color: white;
            text-decoration: none;
            border-radius: 10px;
        }
        .add-btn:hover {
            color: #fff;
        }
        .filters {
            margin-bottom: 20px;
        }
        .filters button {
            margin-right: 10px;
            padding: 10px;
            background-color: #f0f0f0;
            border: 1px solid #ddd;
            border-radius: 10px;
            cursor: pointer;
        }
        .filters button:hover {
            background-color: #e0e0e0;
        }
        .paging {
            display: flex;
            width: fit-content;
            margin: 0 auto;
        }
        .paging nav {
            flex: 1;
            justify-content: center;
        }
        .pagination-custom .page-link {
            /*color: #00CE7B;*/
            color: #000;
        }
        .paging .pagination .page-item.active .page-link {
            background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%) !important;
            border-color: #DEE2E6 !important;
            color: #fff !important;
        }

    </style>
</head>
<body>

<%@ include file="../include/sub_header.jsp" %>
<div class="wrap">
    <%--  검색창 영역  --%>
    <div class="search">
        <form id="searchForm" action="/acc-board/list" method="get">
            <input type="hidden" name="type" value="tc">
            <input type="text" class="form-control" name="keyword" placeholder="동행을 찾아보세요." >
        </form>
    </div>

    <%--    <div class="filters">--%>
    <%--        <button>날짜</button>--%>
    <%--        <button>나이/성별</button>--%>
    <%--        <button>유형</button>--%>
    <%--    </div>--%>

    <c:if test="${abList.size() == 0}">
        <div class="empty">
            게시물이 존재하지 않습니다.
        </div>
    </c:if>

    <div class="card-container">
        <c:if test="${abList.size() > 0}">
            <c:forEach var="ab" items="${abList}">
                <div class="card-wrapper">
                    <section class="card-post" data-bno="${ab.boardId}">
                        <div class="card-content-wrapper">
                            <div class="card-details-top">
                                <div class="card-text">
                                    <div class="card-title">${ab.shortTitle}</div>
                                    <div class="card-content">
                                            ${ab.shortContent}
                                    </div>
                                </div>
                                <div class="card-img">
                                    <c:choose>
                                        <c:when test="${ab.imagePath != null}">
                                            <img src="${ab.imagePath}" alt="대표이미지">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/assets/img/accBoardDefaultImg.webp" alt="기본이미지">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="card-details-bot1">
                                    <span>${ab.writer} ·
                                        <c:choose>
                                            <c:when test="${ab.gender == 'M'}">남자</c:when>
                                            <c:when test="${ab.gender == 'F'}">여자</c:when>
                                        </c:choose>
                                    </span>
                            </div>
                            <div class="card-details-bot2">
                                <span class="lnr lnr-calendar-full">&nbsp;${ab.startDate} - ${ab.endDate}</span>
                                <span class=""></span>
                                <span class="counter">댓글 ${ab.replyCount} · 조회수 ${ab.view}</span>
                            </div>
                        </div>
                    </section>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <!-- 페이지 버튼 영역 -->
    <div class="paging">
        <nav aria-label="Page navigation example">
            <ul class="pagination pagination-sm pagination-custom">
                <c:if test="${maker.pageInfo.pageNo != 1}">
                    <li class="page-item">
                        <a class="page-link" href="/acc-board/list?pageNo=1&type=${s.type}&keyword=${s.keyword}">&lt;&lt;</a>
                    </li>
                </c:if>
                <c:if test="${maker.prev}">
                    <li class="page-item">
                        <a class="page-link" href="/acc-board/list?pageNo=${maker.begin - 1}&type=${s.type}&keyword=${s.keyword}">prev</a>
                    </li>
                </c:if>
                <c:forEach var="i" begin="${maker.begin}" end="${maker.end}">
                    <li data-page-num="${i}" class="page-item">
                        <a class="page-link" href="/acc-board/list?pageNo=${i}&type=${s.type}&keyword=${s.keyword}">${i}</a>
                    </li>
                </c:forEach>
                <c:if test="${maker.next}">
                    <li class="page-item">
                        <a class="page-link" href="/acc-board/list?pageNo=${maker.end + 1}&type=${s.type}&keyword=${s.keyword}">next</a>
                    </li>
                </c:if>
                <c:if test="${maker.pageInfo.pageNo != maker.finalPage}">
                    <li class="page-item">
                        <a class="page-link" href="/acc-board/list?pageNo=${maker.finalPage}&type=${s.type}&keyword=${s.keyword}">&gt;&gt;</a>
                    </li>
                </c:if>
                </li>
            </ul>
        </nav>
    </div>

    <!-- 게시글 추가 -->
    <div class="add-btn-box">
        <a href="#" class="add-btn">동행글 작성</a>
    </div>
</div>

<script>

    // 검색
    document.querySelector('.search input[name="keyword"]').addEventListener('keydown', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            document.getElementById('searchForm').submit();
        }
    });

    const $cardContainer = document.querySelector('.card-container');

    //================= 삭제버튼 스크립트 =================//
    const modal = document.getElementById('modal'); // 모달창 얻기
    const confirmDelete = document.getElementById('confirmDelete'); // 모달 삭제 확인버튼
    const cancelDelete = document.getElementById('cancelDelete'); // 모달 삭제 취소 버튼

    $cardContainer?.addEventListener('click', e => {
        console.log(e.target);

        // section태그에 붙은 글번호 읽기
        const bno = e.target.closest('section.card-post').dataset.bno;
        // 요청 보내기
        window.location.href= '/acc-board/detail?bno=' + bno;
        console.log(window.location.href);
    });

    // 전역 이벤트로 모달창 닫기
    window.addEventListener('click', e => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    //========== 게시물 목록 스크립트 ============//

    function removeDown(e) {
        if (!e.target.matches('.card-container *')) return;
        const $targetCard = e.target.closest('.card-wrapper');
        $targetCard?.removeAttribute('id', 'card-down');
    }

    function removeHover(e) {
        if (!e.target.matches('.card-container *')) return;
        const $targetCard = e.target.closest('.card');
        $targetCard?.classList.remove('card-hover');

        const $delBtn = e.target.closest('.card-wrapper')?.querySelector('.del-btn');
        if ($delBtn !== null) {
            $delBtn.style.opacity = '0';
        }
    }

    $cardContainer.onmouseover = e => {
        if (!e.target.matches('.card-container *')) return;
        const $targetCard = e.target.closest('.card');
        $targetCard?.classList.add('card-hover');

        const $delBtn = e.target.closest('.card-wrapper')?.querySelector('.del-btn');
        if ($delBtn !== null) {
            $delBtn.style.opacity = '1';
        }
    }

    $cardContainer.onmousedown = e => {
        if (e.target.matches('.card-container .card-btn-group *')) return;
        const $targetCard = e.target.closest('.card-wrapper');
        $targetCard?.setAttribute('id', 'card-down');
    };

    $cardContainer.onmouseup = removeDown;

    $cardContainer.addEventListener('mouseout', removeDown);
    $cardContainer.addEventListener('mouseout', removeHover);

    // write button event
    document.querySelector('.add-btn').onclick = e => {
        window.location.href = '/acc-board/write';
    };

    document.querySelector("[data-page-num='${maker.pageInfo.pageNo}']").classList.add('active');

    function appendActivePage () {
        const currentPage = ${maker.pageInfo.pageNo};
        const $li = document.querySelector(`li[data-page-num="\${currentPage}"]`);
        $li?.classList.add('active');
    }

    // 기존 검색 조건 option태그 고정하기
    function fixSearchOption() {
        // 1. 어떤 조건을 검색했는지 값을 알아 옴
        const type = '${s.type}';
        // 2. 해당 조건을 가진 option 태그를 검색
        const $option = document.querySelector(`#search-type option[value='\${type}']`);
        // 3. 해당 태그에 selected 속성 부여
        $option?.setAttribute('selected', 'selected')
    }

    appendActivePage();
    fixSearchOption();
</script>

</body>
</html>
