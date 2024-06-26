<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
change-password.
<!DOCTYPE html>
<html>
<head>
    <title>Find Password</title>
</head>
<body>
    <h1>Find Password</h1>
    <form action="/find-password" method="post">
        <div>
            <label for="account">Account:</label>
            <input type="text" id="account" name="account" required>
        </div>
        <div>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <button type="submit">Submit</button>
    </form>
    <div style="color:red;">
        ${error}
    </div>
</body>
</html>
