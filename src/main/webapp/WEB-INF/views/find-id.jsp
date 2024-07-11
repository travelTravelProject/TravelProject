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
            <form action="find-id" method="post">
                <div class="input-container">
                    <input 
                    type="text" 
                    id="name" 
                    name="name" 
                    required
                    class="form-control tooltipstered" 
                    class="text-input" 
                    />
                    <label for="name">이름</label>
                </div>
                <div class="input-container">
                    <input 
                    type="email" 
                    id="email"
                     name="email"
                    required
                    class="form-control tooltipstered" 
                    class="text-input" 
                    />
                    <label for="name">이메일</label>
                </div>
                
                <button type="submit" class="button"> 확인</button>

                

            </form>
        </div>
    </div>
    
</body>
</html>