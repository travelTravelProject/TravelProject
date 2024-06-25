<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Page</title>
    <link rel="stylesheet" href="/assets/css/mypage.css">
</head>
<body>
<div class="container">
    <h1>My Page</h1>
    <div class="profile-form ">
        <form
                action="/mypage"
                name="mypage"
                id="mypageForm"
                method="post"
                enctype="multipart/form-data"
        >

            <div class="profile">
                <div class="thumbnail-box">
                    <img src="/assets/img/image-add.png" alt="Profile Image" class="profile-image">
                </div>

                <label>프로필 이미지 추가</label>

                <input type="file"
                       id="profile-img"
                       accept="image/*"
                       style="display: none;"
                       name="profileImage"
                >
            </div>

            <%--            mbti, 소개글, 평점--%>
            <h2>${user.name}</h2>
            <div>
                <p>소개글: <span id="oneLinerText">${userDetail.oneLiner}</span> <button type="button" class="modify" data-modal="oneLinerModal">수정</button></p>
                <p>MBTI: <span id="mbtiText">${userDetail.mbti}</span> <button type="button" class="modify" data-modal="mbtiModal">수정</button></p>
            </div>
            <p>나의 점수: ${userDetail.rating}</p>
            <p>Email: ${user.email}</p>
            <p>Nickname: ${user.nickname}</p>
            <p>Birthday: ${user.birthday}</p>
            <p>Gender: ${user.gender}</p>


        <button type="button" class="modify">프로필 수정</button>
        </form>

    </div>
</div>

<%--<!-- 소개글 모달 -->--%>
<%--<div id="oneLinerModal" class="modal">--%>
<%--    <div class="modal-content">--%>
<%--        <span class="close" data-modal="oneLinerModal">&times;</span>--%>
<%--        <h2>소개글 수정</h2>--%>
<%--        <textarea id="oneLinerInput">${userDetail.oneLiner}</textarea>--%>
<%--        <button type="button" class="saveButton" data-modal="oneLinerModal">저장</button>--%>
<%--    </div>--%>
<%--</div>--%>

<%--&lt;%&ndash;<!-- MBTI 모달 -->&ndash;%&gt;--%>
<%--<div id="mbtiModal" class="modal">--%>
<%--    <div class="modal-content">--%>
<%--        <span class="close" data-modal="mbtiModal">&times;</span>--%>
<%--        <h2>MBTI 수정</h2>--%>
<%--        <input type="text" id="mbtiInput" value="${user.mbti}">--%>
<%--        <button type="button" class="saveButton" data-modal="mbtiModal">저장</button>--%>
<%--    </div>--%>
<%--</div>--%>



<script type="module" src="/assets/js/myPage.js" defer></script>

</body>
</html>
