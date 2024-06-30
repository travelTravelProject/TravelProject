package com.travel.project.intercertor;

import com.travel.project.entity.AccBoard;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.AccBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.travel.project.login.LoginUtil.isMine;

@Configuration
@RequiredArgsConstructor
public class AccBoardInterceptor implements HandlerInterceptor {

    private final AccBoardMapper accBoardMapper;

    // preHandle을 구현하여 로그인을 안한 회원은 글쓰기, 글수정, 글삭제 요청을 거부하고 로그인 페이지로 리다이렉션
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        HttpSession session = request.getSession();

        // 요청 URL
        String redirectUri = request.getRequestURI();

        // 로그인 여부 확인
        if (!LoginUtil.isLoggedIn(session)) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            response.sendRedirect("/sign-in?message=login-required&redirect=" + redirectUri);
            return false;
        }

        // 수정 및 삭제 요청시 관리자인지 확인
        if (redirectUri.equals("/acc-board/delete") || redirectUri.equals("/acc-board/modify")) {
            // 1. 관리자인지 확인
            if (LoginUtil.isAdmin(session)) {
                return true;
            }
            // 2. 삭제 또는 수정하려는 글의 작성자인지 확인
            long boardId = Integer.parseInt(request.getParameter("bno"));
            AccBoard accBoard = accBoardMapper.findOne(boardId);
            String accBoardAccount = accBoard.getAccount();
            // 현재 로그인한 회원의 계정명
            String loggedInUserAccount = LoginUtil.getLoggedInUserAccount(session);

            // 계정명 비교
            if (!isMine(accBoardAccount, loggedInUserAccount)) {
                // 작성자가 아닌 경우 접근 거부
                response.setStatus(403);
                response.sendRedirect("/access-deny?message=authorization");
                return false;
            }
        }
        return true;
    }
}
