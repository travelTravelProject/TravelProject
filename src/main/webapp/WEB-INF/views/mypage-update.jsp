<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="KO">
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8">
<%--    <title>My Page</title>--%>
    <link rel="stylesheet" href="/assets/css/mypage.css">
</head>
<body>
<%@ include file="include/sub_header.jsp" %>

<div class="container">
    <h1>프로필 수정</h1>
    <div class="update-form">
        <div class="profile-form ">
            <form
                    action="/mypage/update"
                    name="mypage"
                    id="mypageUpdateForm"
                    method="post"
                    enctype="multipart/form-data"
            >

                <div class="profile">
                    <div class="thumbnail-box">
                        <c:choose>
                            <c:when test="${not empty userDetail.profileImage && userDetail.profileImage != 'none'}">
                                <img src="${userDetail.profileImage}" alt="profile image">
                            </c:when>
                            <c:otherwise>
                                <img src="/assets/img/anonymous.jpg" alt="Anonymous Profile Image">
                            </c:otherwise>
                        </c:choose>
<%--                        <img src="${not empty userDetail.profileImage && userDetail.profileImage != 'none' ? userDetail.profileImage : '/assets/img/image-add.png'}"--%>
<%--                             alt="Profile Image" class="profile-image" id="profileImagePreview">--%>
                    </div>

                    <label>프로필 이미지 추가</label>

                    <input type="file"
                           id="profile-img"
                           accept="image/*"
                           style="display: none;"
                           name="profileImage"
                    >
                </div>


                <input type="hidden" name="account" value="${user.account}">

                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${user.name}" required>

                <label for="nickname">Nickname:</label>
                <input type="text" id="nickname" name="nickname" value="${user.nickname}" required>

                <label for="oneLiner">소개글:</label>
                <input type="text" id="oneLiner" name="oneLiner"
                       value="${userDetail.oneLiner}"
                       placeholder="${userDetail.oneLiner != null ? userDetail.oneLiner : '소개글을 입력해주세요.'}" >

                <label for="mbti">MBTI:</label>
                <select id="mbti" name="mbti" required>
                    <option value="" disabled selected>MBTI 선택하세요.</option>
                    <option value="ISTJ" ${userDetail.mbti == 'ISTJ' ? 'selected' : ''}>ISTJ</option>
                    <option value="ISFJ" ${userDetail.mbti == 'ISFJ' ? 'selected' : ''}>ISFJ</option>
                    <option value="INFJ" ${userDetail.mbti == 'INFJ' ? 'selected' : ''}>INFJ</option>
                    <option value="INTJ" ${userDetail.mbti == 'INTJ' ? 'selected' : ''}>INTJ</option>
                    <option value="ISTP" ${userDetail.mbti == 'ISTP' ? 'selected' : ''}>ISTP</option>
                    <option value="ISFP" ${userDetail.mbti == 'ISFP' ? 'selected' : ''}>ISFP</option>
                    <option value="INFP" ${userDetail.mbti == 'INFP' ? 'selected' : ''}>INFP</option>
                    <option value="INTP" ${userDetail.mbti == 'INTP' ? 'selected' : ''}>INTP</option>
                    <option value="ESTP" ${userDetail.mbti == 'ESTP' ? 'selected' : ''}>ESTP</option>
                    <option value="ESFP" ${userDetail.mbti == 'ESFP' ? 'selected' : ''}>ESFP</option>
                    <option value="ENFP" ${userDetail.mbti == 'ENFP' ? 'selected' : ''}>ENFP</option>
                    <option value="ENTP" ${userDetail.mbti == 'ENTP' ? 'selected' : ''}>ENTP</option>
                    <option value="ESTJ" ${userDetail.mbti == 'ESTJ' ? 'selected' : ''}>ESTJ</option>
                    <option value="ESFJ" ${userDetail.mbti == 'ESFJ' ? 'selected' : ''}>ESFJ</option>
                    <option value="ENFJ" ${userDetail.mbti == 'ENFJ' ? 'selected' : ''}>ENFJ</option>
                    <option value="ENTJ" ${userDetail.mbti == 'ENTJ' ? 'selected' : ''}>ENTJ</option>
                </select>

<%--                <label for="email">Email:</label>--%>
<%--                <input type="email" id="email" name="email" value="${user.email}" required>--%>


                <button type="submit" class="update-button">수정</button>
            </form>
        </div>
    </div>
</div>



<script type="module" src="/assets/js/myPageUpdate.js" defer></script>
</body>
</html>
