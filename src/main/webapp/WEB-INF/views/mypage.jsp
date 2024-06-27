<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="/assets/css/mypage.css">
</head>
<body>
<div class="container">
    <div class="card-profile"></div>
<%--    <h1>My Page</h1>--%>
    <section class="trip-card">
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

            </form>
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
                    <p>${user.mbti}ISFJ</p>
                    <%--                </div>--%>
                    <%--                <div>--%>
                    <%--                    <span>00</span>--%>
                    <%--                    <p>DAYS</p>--%>
                    <%--                </div>--%>
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
            <div class="profile-pic">
                <img src="/assets/img/image-add.png" alt="프로필 이미지">
            </div>
            <div class="profile-info">
                <h2>${user.mbti}키티</h2>
                <p>30대 · 여자 · 한국</p>
                <p>자기소개를 입력하고 마음에 맞는 동행을 구해보세요!</p>
            </div>

        </section>
            <div class="profile-stats">
                <button type="button" class="modify">프로필 수정</button>
            </div>

    </div>





<script type="module" src="/assets/js/myPage.js" defer></script>

</body>
</html>
