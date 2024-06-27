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
    <h1>프로필 수정</h1>
    <div class="update-form">

<%--        <p>account: ${user.account}</p>--%>

            <form action="/mypage/update" method="post" id="mypageUpdateForm">
                <input type="hidden" name="account" value="${user.account}">

                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${user.name}" required>

                <label for="oneLiner">소개글:</label>
                <input type="text" id="oneLiner" name="oneLiner"
                       value="${user.oneLiner}" placeholder="소개글을 입력해주세요." required>

                <label for="mbti">MBTI:</label>
                <select id="mbti" name="mbti" required>
                    <option value="" disabled selected>MBTI 선택</option>
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





                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" required>

                <label for="nickname">Nickname:</label>
                <input type="text" id="nickname" name="nickname" value="${user.nickname}" required>

<%--                <label for="birthday">Birthday:</label>--%>
<%--                <input type="date" id="birthday" name="birthday" value="${user.birthday}" required>--%>

<%--                <label for="gender">Gender:</label>--%>
<%--                <select id="gender" name="gender" required>--%>
<%--                    <option value="M" ${user.gender == 'M' ? 'selected' : ''}>Male</option>--%>
<%--                    <option value="F" ${user.gender == 'F' ? 'selected' : ''}>Female</option>--%>
<%--                </select>--%>

                <button type="submit" class="update-button">Update</button>
            </form>
    </div>
</div>
<script type="module" src="/assets/js/myPageUpdate.js" defer></script>
</body>
</html>
