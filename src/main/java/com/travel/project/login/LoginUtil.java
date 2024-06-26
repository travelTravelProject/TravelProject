package com.travel.project.login;

import javax.servlet.http.HttpSession;

// 왜 만들었냐면 로그인 한 사람이 /members/sign-up, /members/sign-in 페이지로 가면 안되자나
//하나하나 막을것도 아니고 그래서 만든거임 공통으로
public class LoginUtil {

    public static final String LOGIN = "'login'";
    public static final String AUTO_LOGIN_COOKIE  = "auto" ;


    // 로그인 여부 확인
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(LOGIN) != null;
    }

}
