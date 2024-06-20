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

            <form action="/updateProfile" method="post">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${user.name}" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" required>

                <label for="nickname">Nickname:</label>
                <input type="text" id="nickname" name="nickname" value="${user.nickname}" required>

                <label for="birthday">Birthday:</label>
                <input type="date" id="birthday" name="birthday" value="${user.birthday}" required>

                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="M" ${user.gender == 'M' ? 'selected' : ''}>Male</option>
                    <option value="F" ${user.gender == 'F' ? 'selected' : ''}>Female</option>
                </select>

                <button type="submit">Update</button>
            </form>
    </div>
</div>
<script type="module" src="/assets/js/myPage.js" defer></script>
</body>
</html>
