<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="KO">
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/assets/css/login.css">
</head>
<body>
    <%@ include file="include/sub_header.jsp" %>
    <div class="login_wrap">
        <div class="login">
             <form action="/change-password" method="post">
                <div class="input-container">
                    <input 
                    type="password" id="newPassword" name="newPassword" required
                    required
                    class="form-control tooltipstered" 
                    class="text-input" 
                    />
                    <label for="newPassword">새로운 비밀번호</label>
                </div>
                <div class="input-container">
                    <input 
                    type="password" id="confirmPassword" name="confirmPassword" required
                    required
                    class="form-control tooltipstered" 
                    class="text-input" 
                    />
                    <label for="name">비밀번호 확인</label>
                </div>
                
                <button type="submit" class="button"> 로그인하기</button>

                <div style="color:red;">
                    ${error}
                </div>
                <div style="color:green;">
                    ${message}
                </div>
            

            </form>
        </div>
    </div>
    
</body>
</html>