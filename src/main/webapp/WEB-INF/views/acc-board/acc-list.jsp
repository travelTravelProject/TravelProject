<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>

    <%@ include file="../include/static-head.jsp" %>

    <link rel="stylesheet" href="/assets/css/list.css">

    <style>
        .card-container .card .card-title-wrapper .time-view-wrapper>div.hit {
            background: yellow;
        }
        .card-container .card .card-title-wrapper .time-view-wrapper>div.new {
            background: red;
            color: white;
        }
    </style>

</head>
<body>

<%--<%@ include file="../include/header.jsp" %>--%>

<div id="wrap">

    <div class="main-title-wrapper">
        <h1 class="main-title">동행 게시판</h1>
        <button class="add-btn">글 쓰기</button>
    </div>


    <div class="top-section">
        <!-- 검색창 영역 -->
        <div class="search">
            <form action="/acc-board/acc-list" method="get">

                <select class="form-select" name="type" id="search-type">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="writer">작성자</option>
                    <option value="tc">제목+내용</option>
                </select>

                <input type="text" class="form-control" name="keyword" value="${s.keyword}">

                <button class="btn btn-primary" type="submit">
                    <i class="fas fa-search"></i>
                </button>

            </form>
        </div>

        <div class="amount">
            <div><a href="#">6</a></div>
            <div><a href="#">18</a></div>
            <div><a href="#">30</a></div>
        </div>
    </div>

    <div class="card-container">

        <c:if test="${abList.size() == 0}">
            <div class="empty">
                게시물이 존재하지 않습니다.
            </div>
        </c:if>

        <c:if test="${abList.size() > 0}">
            <c:forEach var="ab" items="${abList}">
                <div class="card-wrapper">
                    <section class="card" data-bno="${ab.boardId}">
                        <div class="card-title-wrapper">
                            <h2 class="card-title">${ab.shortTitle} </h2>
                            <div class="time-view-wrapper">
                                <div class="time">
                                    <i class="far fa-clock"></i>
                                        ${ab.date}
                                </div>

<%--                                <c:if test="${ab.hit}">--%>
<%--                                    <div class="hit">HIT</div>--%>
<%--                                </c:if>--%>

<%--                                <c:if test="${ab.newArticle}">--%>
<%--                                    <div class="new">NEW!</div>--%>
<%--                                </c:if>--%>

                                <div class="view">
                                    <i class="fas fa-eye"></i>
                                    <span class="view-count">${ab.view}</span>
                                </div>
                            </div>
                        </div>
                        <div class="card-content">

                                ${ab.shortContent}

                        </div>
                    </section>
<%--                    <!-- 관리자이거나 본인이 쓴 글에만 렌더링 되도록 -->--%>
<%--                    <c:if test="${login.auth == 'ADMIN' || login.account == b.account}">--%>
                    <div class="card-btn-group">
                        <button class="del-btn" data-href="/acc-board/delete?boardId=${ab.boardId}">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
<%--                    </c:if>--%>
                </div>
                <%-- .end card-wrapper --%>
            </c:forEach>
        </c:if>


    </div>
    <%-- end .card-container --%>

    <!-- 게시글 목록 하단 영역 -->
    <div class="bottom-section">

        <!-- 페이지 버튼 영역 -->
        <nav aria-label="Page navigation example">
            <ul class="pagination pagination-lg pagination-custom">

                <c:if test="${maker.pageInfo.pageNo != 1}">
                    <li class="page-item">
                        <a class="page-link" href="/board/list?pageNo=1&type=${s.type}&keyword=${s.keyword}">&lt;&lt;</a>
                    </li>
                </c:if>

                <c:if test="${maker.prev}">
                    <li class="page-item">
                        <a class="page-link" href="/board/list?pageNo=${maker.begin - 1}&type=${s.type}&keyword=${s.keyword}">prev</a>
                    </li>
                </c:if>

                <c:forEach var="i" begin="${maker.begin}" end="${maker.end}">
                    <li data-page-num="${i}" class="page-item">
                        <a class="page-link" href="/board/list?pageNo=${i}&type=${s.type}&keyword=${s.keyword}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${maker.next}">
                    <li class="page-item">
                        <a class="page-link" href="/board/list?pageNo=${maker.end + 1}&type=${s.type}&keyword=${s.keyword}">next</a>
                    </li>
                </c:if>

                <c:if test="${maker.pageInfo.pageNo != maker.finalPage}">
                    <li class="page-item">
                        <a class="page-link" href="/board/list?pageNo=${maker.finalPage}&type=${s.type}&keyword=${s.keyword}">&gt;&gt;</a>
                    </li>
                </c:if>

                </li>
            </ul>
        </nav>

    </div>
    <!-- end div.bottom-section -->

</div>
<%-- end .wrap --%>

<!-- 모달 창 -->
<div class="modal" id="modal">
    <div class="modal-content">
        <p>정말로 삭제할까요?</p>
        <div class="modal-buttons">
            <button class="confirm" id="confirmDelete"><i class="fas fa-check"></i> 예</button>
            <button class="cancel" id="cancelDelete"><i class="fas fa-times"></i> 아니오</button>
        </div>
    </div>
</div>



<script>

    const $cardContainer = document.querySelector('.card-container');

    //================= 삭제버튼 스크립트 =================//
    const modal = document.getElementById('modal'); // 모달창 얻기
    const confirmDelete = document.getElementById('confirmDelete'); // 모달 삭제 확인버튼
    const cancelDelete = document.getElementById('cancelDelete'); // 모달 삭제 취소 버튼

    $cardContainer?.addEventListener('click', e => {
        // 삭제 버튼을 눌렀다면~
        if (e.target.matches('.card-btn-group *')) {
            console.log('삭제버튼 클릭');
            modal.style.display = 'flex'; // 모달 창 띄움

            const $delBtn = e.target.closest('.del-btn');
            const deleteLocation = $delBtn.dataset.href;

            // 확인 버튼 이벤트
            confirmDelete.onclick = e => {
                // 삭제 처리 로직
                window.location.href = deleteLocation;

                modal.style.display = 'none'; // 모달 창 닫기
            };


            // 취소 버튼 이벤트
            cancelDelete.onclick = e => {
                modal.style.display = 'none'; // 모달 창 닫기
            };
        } else { // 삭제 버튼 제외한 부분은 글 상세조회 요청

            // section태그에 붙은 글번호 읽기
            const bno = e.target.closest('section.card').dataset.bno;
            // 요청 보내기
            window.location.href= '/acc-board/detail?bno=' + bno;
        }
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

    <%--document.querySelector("[data-page-num='${maker.pageInfo.pageNo}']").classList.add('active');--%>

    <%--function appendActivePage () {--%>
    <%--    const currentPage = ${maker.pageInfo.pageNo};--%>
    <%--    const $li = document.querySelector(`li[data-page-num="\${currentPage}"]`);--%>
    <%--    $li?.classList.add('active');--%>
    <%--}--%>

    // 기존 검색 조건 option태그 고정하기
    function fixSearchOption() {

        // 1. 어떤 조건을 검색했는지 값을 알아 옴
        const type = '${s.type}';
        // console.log('type: ' + type)

        // 2. 해당 조건을 가진 option 태그를 검색
        const $option = document.querySelector(`#search-type option[value='\${type}']`);

        // 3. 해당 태그에 selected 속성 부여
        $option?.setAttribute('selected', 'selected')

    }

    // appendActivePage();
    fixSearchOption();

</script>


</body>
</html>