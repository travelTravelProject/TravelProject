<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8" />
    <title>WithTravel 회원 가입</title>
    <link rel="stylesheet" href="/assets/css/sing_up_yj.css">
</head>
<body>
    <%@ include file="include/sub_header.jsp" %>
    <div class="sign_up_wrap">
        <div class="sign_up">
            <h3 style="text-align: center;">회원가입</h3>
            <form
                    action="/sign-up"
                    name="signup"
                    id="signUpForm"
                    method="post"
                    enctype="multipart/form-data"
            >
            <div class="sign_up_int_common_box">
                <div class="input-container">
                    <input 
                    type="text" 
                    name="account" 
                    id="user_id"
                    class="form-control tooltipstered" 
                    required="required" 
                    placeholder="숫자와 영어로 4-14자"
                    class="text-input" 
                    />
                    <label for="user_id">
                        아이디   
                    </label>
                    <p>
                        <span id="idChk"></span>
                    </p> 
                </div>
            </div>
            
            <div class="sign_up_int_common_box">
                <div class="input-container">
                    <input 
                    type="password"
                    size="17"
                    maxlength="20"
                    id="password"
                    name="password"
                    class="form-control tooltipstered"
                    required="required"
                    aria-required="true"
                    placeholder="영문과 특수문자를 포함한 최소 8자"
                    />
                    <label for="password">비밀번호</label>
                    <p>
                        <span id="pwChk"></span>
                    </p> 
                </div>
            </div>
            
            <div class="sign_up_int_common_box">
                <div class="input-container">
                    <input 
                    type="password"
                    size="17"
                    maxlength="20"
                    id="password_check"
                    name="pw_check"
                    class="form-control tooltipstered"
                    required="required"
                    aria-required="true"
                    placeholder="비밀번호가 일치해야합니다."
                    />
                    <label for="password_check">비밀번호 확인</label>
                    <p>
                        <span id="pwChk2"></span>
                    </p> 
                </div>
            </div>
            
            <div class="sign_up_int_common_box">
                <div class="input-container">
                    <input 
                    type="text"
                    name="name"
                    id="user_name"
                    class="form-control tooltipstered"
                    maxlength="6"
                    required="required"
                    aria-required="true"
                    placeholder="한글로 최대 6자"
                    />
                    <label for="user_name">이름</label>
                    <p>
                        <span id="nameChk"></span>
                    </p> 
                </div>
            </div>
            
            <div class="sign_up_int_common_box">
                <div class="input-container">
                    <input 
                    type="text"
                    name="nickname"
                    id="user_nickname"
                    class="form-control tooltipstered"
                    required="required"
                    aria-required="true"
                    placeholder="최대 8자"
                    />
                    <label for="user_nickname">닉네임</label>
                    <p>
                        <span id="nicknameChk"></span>
                    </p> 
                </div>
            </div>
            
            <div class="sign_up_int_common_box">
                <div class="input-container">
                    <input 
                    type="email"
                    name="email"
                    id="user_email"
                    class="form-control tooltipstered"
                    required="required"
                    aria-required="true"
                    placeholder="ex) abc@mvc.com"
                    />
                    <label for="user_email">이메일</label>
                    <p>
                        <span id="emailChk"></span>
                    </p> 
                </div>
            </div>
            
            <div class="birth_gender">
                <div class="birth_box">
                    <label class="birth_title" for="user_birthday">생년월일</label>
                    <input
                            type="date"
                            name="birthday"
                            id="user_birthday"
                            class="form-control tooltipstered"
                            required="required"
                            aria-required="true"
                            placeholder="ex) 2000-06-17"
                    />
                    <p>
                        <span id="birthdayChk"></span>
                    </p> 
                </div>
                <div>
                    <label class="gender_title" for="gender">성별</label>
                    <p>
                        <span id="genderChk"></span>
                    </p> 
                    <div class="gender_box">
                        <div class="Radio">
                          <input 
                            value="M" 
                            type="radio" 
                            name="gender"
                            id="Radio1" 
                            required="required"
                            aria-required="true"
                           />
                          <label for="Radio1">남성</label>
                        </div>
                        <div class="Radio">
                          <input 
                            value="F" 
                            type="radio" 
                            name="gender" 
                            id="Radio2" 
                            required="required"
                            aria-required="true"
                          />
                          <label for="Radio2">여성</label>
                        </div>
                      </div>
                      
                </div>
            </div>
            
            <button 
                type="submit"
                value="회원가입"
                class="btn form-control tooltipstered button"
                id="signup-btn"
                > 회원가입
            </button>

            </form>
        </div>
    </div>

    <script type="module" src="/assets/js/signUp.js" defer></script>
</body>
</html>
