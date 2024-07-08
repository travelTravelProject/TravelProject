<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="KO">
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/assets/css/mypage-feed.css">
    <link rel="stylesheet" href="/assets/css/mypage.css">
    <link rel="stylesheet" href="/assets/css/mypage-board.css">
</head>
<body>

<%@ include file="include/sub_header.jsp" %>

<div class="container">
    <div class="card-profile"></div>
    <%--    <h1>My Page</h1>--%>
    <section class="trip-card">
        <div class="profile-form">

            <div class="profile">
                <c:choose>
                    <c:when test="${not empty userDetail.profileImage && userDetail.profileImage != 'none'}">
                        <img src="${userDetail.profileImage}" alt="profile image">
                    </c:when>
                    <c:otherwise>
                        <img src="/assets/img/anonymous.jpg" alt="Anonymous Profile Image">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="trip-info">
            <h2>${user.name}</h2>
            <div class="trip-stats-info">
                <div>
                    <span>MBTI</span>
                    <p>${userDetail.mbti}</p>
                </div>
                <div>
                    <span>내 정보</span>
                    <p>${birthYear} · ${user.gender == 'F' ? '여자' : '남자'}</p>
                </div>
            </div>
        </div>
    </section>


    <section class="one-liner-profile">

        <div class="profile-box">
            <c:choose>
                <%--                    <c:when test="${login != null && userDetail.profileImage != null}">--%>
                <c:when test="${not empty userDetail.profileImage && userDetail.profileImage != 'none'}">
                    <img src="${userDetail.profileImage}" alt="profile image">
                </c:when>
                <c:otherwise>
                    <img src="/assets/img/anonymous.jpg" alt="Anonymous Profile Image">
                </c:otherwise>
            </c:choose>
            <div class="profile-nickname">
                <h2>${user.nickname}</h2>
                <p>${birthYear} · ${user.gender == 'F' ? '여자' : '남자'}</p>
            </div>
        </div>

        <div class="profile-info">
            <p>${userDetail.oneLiner != null ? userDetail.oneLiner : "자기소개를 입력하고 마음에 맞는 동행을 구해보세요!"}</p>
        </div>

    </section>
    <div class="profile-stats">
        <button type="button" class="modify">프로필 수정</button>
    </div>

    <div class="trip-info">
        <div class="trip-stats">
            <div id="my-board-btn" class="tab-btn" data-my-account="${user.account}">
                <span>동행</span>
            </div>
            <div id="my-feed-btn" class="tab-btn" data-my-account="${user.account}">
                <span>피드</span>
            </div>

<%--            <div id="my-like-btn" class="tab-btn">
<%--                <span>좋아요</span>
<%--            </div>--%>
        </div>
    </div>


    <div class="board-container">

    </div>
    <div class="board-container" id="my-feed-tab">
        <div class="my-tab-inner">

        </div>
<%--        <span class="close close-modal">x</span>--%>
    </div>
</div>


    <%-- 아이콘 --%>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script type="module" src="/assets/js/myPage.js" defer></script>
    <script type="module" src="/assets/js/feed/mypage-feed.js" defer></script>
    <script type="module" src="/assets/js/mypage-board.js" defer></script>
</body>
</html>



