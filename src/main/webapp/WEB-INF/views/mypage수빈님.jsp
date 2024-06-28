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
                <p>소개글: <span id="oneLinerText">${userDetail.oneLiner}</span>
                    <button type="button" class="registerModify" data-modal="oneLinerModal">등록 및 수정</button></p>
                <p>MBTI: <span id="mbtiText">${userDetail.mbti}</span>
                    <button type="button" class="registerModify" data-modal="mbtiModal">등록 및 수정</button></p>
                <p>나의 점수: ${userDetail.rating}</p>
            </div>
            <p>Email: ${user.email}</p>
            <p>Nickname: ${user.nickname}</p>
            <p>Birthday: ${user.birthday}</p>
            <p>Gender: ${user.gender}</p>

        <button type="button" class="modify">프로필 수정</button>
        </form>

    </div>
</div>


<!-- 소개글 모달 -->
<div id="oneLinerModal" class="modal">
    <div class="modal-content">
        <span class="close" data-modal="oneLinerModal">&times;</span>
        <h2>소개글 수정</h2>
        <textarea id="oneLinerInput">${userDetail.oneLiner}</textarea>
        <button type="button" class="saveButton" data-modal="oneLinerModal">저장</button>
    </div>
</div>

<%--<!-- MBTI 모달 -->--%>
<div id="mbtiModal" class="modal">
    <div class="modal-content">
        <span class="close" data-modal="mbtiModal">&times;</span>
        <h2>MBTI 수정</h2>
        <select id="mbtiSelect">
            <option value="ISTJ">ISTJ</option>
            <option value="ISFJ">ISFJ</option>
            <option value="INFJ">INFJ</option>
            <option value="INTJ">INTJ</option>
            <option value="ISTP">ISTP</option>
            <option value="ISFP">ISFP</option>
            <option value="INFP">INFP</option>
            <option value="INTP">INTP</option>
            <option value="ESTP">ESTP</option>
            <option value="ESFP">ESFP</option>
            <option value="ENFP">ENFP</option>
            <option value="ENTP">ENTP</option>
            <option value="ESTJ">ESTJ</option>
            <option value="ESFJ">ESFJ</option>
            <option value="ENFJ">ENFJ</option>
            <option value="ENTJ">ENTJ</option>
        </select>
        <button type="button" class="saveButton" data-modal="mbtiModal">저장</button>
    </div>
</div>




<script type="module" src="/assets/js/myPage.js" defer></script>

</body>
</html>
