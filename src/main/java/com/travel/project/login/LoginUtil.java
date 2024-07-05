package com.travel.project.login;

import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.entity.Auth;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 왜 만들었냐면 로그인 한 사람이 /members/sign-up, /members/sign-in 페이지로 가면 안되자나
//하나하나 막을것도 아니고 그래서 만든거임 공통으로
public class LoginUtil {

    public static final String LOGIN = "user";
    public static final String AUTO_LOGIN_COOKIE  = "auto" ;


    // 로그인 여부 확인
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(LOGIN) != null;
    }






    // 로그인한 회원의 계정명 얻기
    public static String getLoggedInUserAccount(HttpSession session) {
        LoginUserInfoDto loggedInUser = getLoggedInUser(session);
        return loggedInUser != null ? loggedInUser.getAccount() : null;
    }

    // 로그인한 회원의 닉네임 얻기
    public static String getLoggedInUserNickname(HttpSession session) {
        LoginUserInfoDto loggedInUser = getLoggedInUser(session);
        return loggedInUser != null ? loggedInUser.getNickname() : null;
    }

    public static LoginUserInfoDto getLoggedInUser(HttpSession session) {
        return (LoginUserInfoDto) session.getAttribute(LOGIN);
    }

    public static boolean isAdmin(HttpSession session) {
        LoginUserInfoDto loggedInUser = getLoggedInUser(session);
        Auth auth = null;
        if (isLoggedIn(session)) {
            auth = Auth.valueOf(loggedInUser.getAuth());
        }
        return auth == Auth.admin;
    }

    public static boolean isMine(String boardAccount, String loggedInUserAccount) {
        return boardAccount.equals(loggedInUserAccount);
    }

    public static boolean isAutoLogin(HttpServletRequest request) {
        Cookie autoLoginCookie = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);
        return autoLoginCookie != null;
    }

}
