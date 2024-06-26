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
                        <option value="" disabled selected>Select your MBTI</option>
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
