<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="KO">
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="/assets/css/mypage.css">
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
            <div class="trip-stats">
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
            <div>
                <span>동행</span>
            </div>
            <div>
                <span>피드</span>
            </div>
            <div>
                <span>좋아요</span>
            </div>
        </div>
    </div>

<%--    <c:if test="${abList.size() > 0}">--%>
<%--    <c:forEach var="ab" items="${abList}">--%>
    <div class="board-container">
    <div class="card-wrapper">
        <section class="card-post" data-bno="">
            <div class="card-content-wrapper">
                <div class="card-details-top">
                    <div class="card-text">
                        <div class="card-title">제목</div>
                        <div class="card-content">
                                내용
                        </div>
                    </div>
                    <div class="card-img">
                        <img src="#" alt="대표이미지">
                    </div>
                </div>
                <div class="card-details-bot">
                    <span>여</span>
                    <span class="lnr lnr-calendar-full"></span>
                    <span class="acc-period">&nbsp;2024-06-05 - 2024-06-30</span>
                    <span class="view-count">조회수 </span>
                </div>
            </div>
        </section>
    </div>
</div>
<%--    </c:forEach>--%>
<%--    </c:if>--%>




    <script type="module" src="/assets/js/myPage.js" defer></script>

</body>
</html>



