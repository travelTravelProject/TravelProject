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
    <style>
        .error_texts {
            display: none;
            color: red;
        }
    </style>
</head>
<body>
    <%@ include file="include/sub_header.jsp" %>
    <div class="login_wrap">
        <div class="login">
            <p class="error_texts error_id">아이디가 틀렸습니다</p>
            <p class="error_texts error_pw">비밀번호가 틀렸습니다</p>
            <form action="/sign-in" name="sign-in" method="post" id="signInForm">
                <div class="input-container">
                    <input 
                    type="text" 
                    name="account" 
                    id="signInId"
                    class="form-control tooltipstered" 
                    required="required" 
                    placeholder=""
                    class="text-input" 
                    />
                    <label for="id">아이디</label>
                </div>
                <div class="input-container">
                    <input 
                    type="password" 
                    size="17" 
                    maxlength="20" 
                    id="signInPw"
                    name="password" 
                    class="form-control tooltipstered"
                    required="required"
                    placeholder=""
                    class="text-input" 
                    />
                    <label for="password">비밀번호</label>
                </div>

                <div class="checkbox-wrapper">
                    <label for="">
                        자동로그인
                        <input id="_checkbox-26" type="checkbox" name="autoLogin">
                        <label for="_checkbox-26" for="auto-login" class="click_label">
                            <div class="tick_mark"></div>
                        </label>
                    </label>
                </div>

                <ul class="id_pw_find_box">
                    <li><a href="/find-id">아이디 찾기</a></li>
                    <li><a href="/find-password">비번 찾기</a></li>
                </ul>

                <button type="submit" class="button"> 로그인하기</button>
            </form> 
        </div>
    </div>

    <%@ include file="include/footer.jsp" %>

    <script>
        //서버에 전송된 로그인 검증 메세지
        const result = '${result}';
        console.log('result : ', result);
        if(result == 'NO_ACC'){
            document.querySelector('.error_id').style.display = 'block';
        }else if(result == 'NO_PW'){
            document.querySelector('.error_pw').style.display = 'block';
        }

        // 비회원 상태로 접근제한 페이지에 들어갔다 온 경우
        const params = new URLSearchParams(window.location.search);
        const message = params.get('message');

        if (message === 'login-required') {
            alert('로그인이 필요한 서비스입니다.')
            const newUrl = window.location.origin + window.location.pathname;
            history.replaceState(null, null, newUrl);
        }
    </script>
</body>
</html>
