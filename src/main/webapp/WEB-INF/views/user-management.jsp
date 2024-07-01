<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="KO">
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="/assets/css/mypage.css">

    <style>
        /* 모달 스타일 */
        .modal {
            display: none; /* 초기에는 모달 숨김 */
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
            animation-name: modalopen;
            animation-duration: 0.4s;
        }

        @keyframes modalopen {
            from {opacity: 0}
            to {opacity: 1}
        }

        .close {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }
    </style>

</head>
<body>
<%@ include file="include/sub_header.jsp" %>
<div class="container">
    <div class="card-profile"></div>
    <section class="trip-card">
        <div class="profile-form">
            <div class="profile">
<%--                    로고로 바꾸기 --%>
                <img src="/assets/img/anonymous.jpg" alt="Anonymous Profile Image">
            </div>
            <h2>관리자</h2>
        </div>
    </section>

    <div class="user-management">
        <p><a href="#" onclick="openModal('userInfoModal')">회원정보 조회</a></p>
        <p>회원정보 삭제</p>

    </div>

    <!-- 회원 정보 조회 모달 -->
    <div id="userInfoModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('userInfoModal')">&times;</span>
            <h3>회원 정보 조회</h3>
            <table>
                <thead>
                <tr>
                    <th>계정</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>닉네임</th>
                    <th>생일</th>
                    <th>성별</th>
                    <th>회원 상태</th>
                    <th>권한</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.account}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.nickname}</td>
                        <td>${user.birthday}</td>
                        <td>${user.gender}</td>
                        <td>${user.status}</td>
                        <td>${user.auth}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        // 모달 열기
        function openModal(modalId) {
            const modal = document.getElementById(modalId);
            modal.style.display = "block";
        }

        // 모달 닫기
        function closeModal(modalId) {
            const modal = document.getElementById(modalId);
            modal.style.display = "none";
        }
    </script>



    <script type="module" src="/" defer></script>

</body>
</html>



