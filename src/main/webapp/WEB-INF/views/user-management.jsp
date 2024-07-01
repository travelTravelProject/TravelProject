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


    <script type="module" src="/" defer></script>

</body>
</html>



