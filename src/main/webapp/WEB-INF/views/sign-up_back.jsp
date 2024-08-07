<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="include/static-head.jsp" %>
    <meta charset="UTF-8" />
    <title>WithTravel 회원 가입</title>
    <link rel="stylesheet" href="/assets/css/user.css" />
</head>
<body>
    <%@ include file="include/sub_header.jsp" %>
<div class="container wrap">
    <div class="row">
        <div class="offset-md-2 col-md-4">
            <div class="card">
                <div class="card-header text-white">
                    <h2><span class="text-gray">WithTravel</span> 회원 가입</h2>
                </div>
                <div class="card-body">
                    <form
                            action="/sign-up"
                            name="signup"
                            id="signUpForm"
                            method="post"
                            enctype="multipart/form-data"
                    >
                        <table class="table">
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>아이디를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                        <span id="idChk"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input
                                            type="text"
                                            name="account"
                                            id="user_id"
                                            class="form-control tooltipstered"
                                            maxlength="14"
                                            required="required"
                                            aria-required="true"
                                            placeholder="숫자와 영어로 4-14자"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>비밀번호를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                        <span id="pwChk"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
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
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>비밀번호를 재확인해주세요.</strong>
                                        <span id="pwChk2"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
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
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>이름을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                        <span id="nameChk"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
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
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>닉네임을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                        <span id="nicknameChk"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input
                                            type="text"
                                            name="nickname"
                                            id="user_nickname"
                                            class="form-control tooltipstered"
                                            required="required"
                                            aria-required="true"
                                            placeholder="최대 8자"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>이메일을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                        <span id="emailChk"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input
                                            type="email"
                                            name="email"
                                            id="user_email"
                                            class="form-control tooltipstered"
                                            required="required"
                                            aria-required="true"
                                            placeholder="ex) abc@mvc.com"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>생년월일을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                        <span id="birthdayChk"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input
                                            type="date"
                                            name="birthday"
                                            id="user_birthday"
                                            class="form-control tooltipstered"
                                            required="required"
                                            aria-required="true"
                                            placeholder="ex) 2000-06-17"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">
                                    <p>
                                        <strong>성별을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                        <span id="genderChk"></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <select
                                            name="gender"
                                            id="user_gender"
                                            class="form-control tooltipstered"
                                            required="required"
                                            aria-required="true"
                                    >
                                        <option value="M">남성</option>
                                        <option value="F">여성</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center">
                                    <p>
                                        <strong>회원가입하셔서 더 많은 서비스를 사용하세요!</strong>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center" colspan="2">
                                    <input
                                            type="submit"
                                            value="회원가입"
                                            class="btn form-control tooltipstered"
                                            id="signup-btn"
                                    />
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="module" src="/assets/js/signUp.js" defer></script>
</body>
</html>
