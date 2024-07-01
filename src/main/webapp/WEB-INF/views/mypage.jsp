<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <%--            <p>TRIPSODA TRAVELER</p>--%>
            <%--            <p>BOARDING-0000000000</p>--%>
            <div class="trip-stats">
                <div>
                    <%--                    <span>00</span>--%>
                    <%--                    <p>CONTINENT</p>--%>
                    <%--                </div>--%>
                    <%--                <div>--%>
                    <span>MBTI</span>
                    <p>${userDetail.mbti}</p>
                                    </div>
                                    <div>
                                        <span>00</span>
                                        <p>DAYS</p>
                                    </div>
                </div>

            </div>
    </section>


    <%--            mbti, 소개글, 평점--%>
    <%--            <h2>${user.name}</h2>--%>
    <%--            <p>소개글: ${user.oneLiner}</p>--%>
    <%--            <p>MBTI: ${user.mbti}</p>--%>
    <%--            <p>나의 점수: ${user.rating}</p>--%>
    <%--            <p>Email: ${user.email}</p>--%>
    <%--            <p>Nickname: ${user.nickname}</p>--%>
    <%--            <p>Birthday: ${user.birthday}</p>--%>
    <%--            <p>Gender: ${user.gender}</p>--%>



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

    <script type="module" src="/assets/js/myPage.js" defer></script>

</body>
</html>



