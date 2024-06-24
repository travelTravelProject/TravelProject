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
                <p>소개글</p>
                <p>MBTI:  </p>
                <p>나의 점수: </p>
            </div>
            <p>Email: ${user.email}</p>
            <p>Nickname: ${user.nickname}</p>
            <p>Birthday: ${user.birthday}</p>
            <p>Gender: ${user.gender}</p>


        <button type="button" class="modify">프로필 수정</button>
        </form>

    </div>
</div>
<script type="module" src="/assets/js/myPage.js" defer></script>

</body>
</html>
