package com.travel.project.service;

import com.travel.project.dto.request.AutoLoginDto;
import com.travel.project.dto.FindIdResponseDto;
import com.travel.project.dto.request.LoginDto;
import com.travel.project.dto.request.SignUpDto;
import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.entity.User;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static com.travel.project.login.LoginUtil.AUTO_LOGIN_COOKIE;
import static com.travel.project.service.LoginResult.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
//    private final UserService userService;

    // 회원가입 중간처리
    public boolean join(SignUpDto dto) {

        User user = dto.toEntity();
        log.info("Attempting to join user: {}", user);

        // 비밀번호 인코딩
        String encodedPassword = encoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);

        boolean isSaved = userMapper.save(user);
        log.info("User saved status: {}", isSaved);
        return isSaved;
    }

    // 아이디, 이메일 중복검사
    public boolean checkIdentifier(String type, String keyword) {
        return userMapper.existById(type, keyword);
    }


//    =================================== yj ========================================

    //로그인 검증 처리
    public LoginResult authenticate(LoginDto dto, HttpSession session, HttpServletResponse response){
        //회원가입 여부 확인
        String account = dto.getAccount();
        User foundMember = userMapper.findOne(account); //디비에서 회원정보 조회ㅘㅁ
        if (foundMember == null) {
            log.info("{} - 회원가입이 필요합니다.", account);
            return NO_ACC;
        }
        // 비밀번호 일치 검사
        String inputPassword = dto.getPassword(); // 클라이언트에 입력한 비번
        String originPassword = foundMember.getPassword(); // 데이터베이스에 저장된 비번

        // PasswordEncoder 에서는 암호화된 비번을 내부적으로 비교해주는 기능을 제공
        if (!encoder.matches(inputPassword, originPassword)) { //비번이 일치 하지 않다면
            log.info("비밀번호가 일치하지 않습니다.");
            return NO_PW;
        }

        //자동 로그인 추가 처리
        if(dto.isAutoLogin()){
            //1. 자동 로그인 쿠키 생성
            // 쿠키 내부에 절대로 중복 되지 않는 유니크한 값을 저장해야 됨, ssion.getId() 는 절대로 중복이 될 수 없다
            String sessionId = session.getId();
            Cookie autoLoginCookie = new Cookie(AUTO_LOGIN_COOKIE, sessionId);
            //쿠키 설정
            autoLoginCookie.setPath("/"); //쿠키를 사용할 경로 , 지금 사이트 전체에서 사용하겠다~
            autoLoginCookie.setMaxAge(60 * 60 * 24 * 90); // 자동 로그인 유지 시간 = 총 90일

            //2.쿠키를 클라이어늩에 전송하기 - 응답 바디에 실어 보내야 됨
            response.addCookie(autoLoginCookie);

            //3. 디비에도 해당 쿠키값을 저장함
            userMapper.updateAutoLogin(
                    AutoLoginDto.builder()
                            .sessionId(sessionId)
                            .limitTime(LocalDateTime.now().plusDays(90))
                            .account(account)
                            .build());
        }

        //일반 로그인
        maintainLoginState(session, foundMember);

        return SUCCESS;
    }

    public static void maintainLoginState(HttpSession session, User foundMember) {
        log.info("{} 님 로그인 성공 ", foundMember.getName());

        //세션 수명 : 설정된 시간 및 브라우져 닫기 전까지
        int maxInactiveInterval = session.getMaxInactiveInterval();
        session.setMaxInactiveInterval(60 * 60); //세션 수명 1시간 설정
        log.debug("session time: {}", maxInactiveInterval);

        //ra 가 model 이랑 같은거임 리다이렉트에서만 사용하는거
        //session.setAttribute("loginUserName",foundMember.getName());
        session.setAttribute("login", new LoginUserInfoDto(foundMember));
        System.out.println("foundMember = " + foundMember);
    }



//    =================================== yj ========================================


    public void autoLoginClear(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        // 1. 쿠키 제거하기
        Cookie c = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);
        if (c != null) {
            c.setPath("/");
            c.setMaxAge(0);
            response.addCookie(c);
        }

        // 2. DB에 자동로그인 컬럼들을 원래대로 돌려놓음
        userMapper.updateAutoLogin(
                AutoLoginDto.builder()
                        .sessionId("none")
                        .limitTime(LocalDateTime.now())
                        .account(LoginUtil.getLoggedInUserAccount(request.getSession()))
                        .build()
        );
    }





    public FindIdResponseDto findIdByNameAndEmail(String name, String email) {
        User user = userMapper.findIdByNameAndEmail(name, email);

        if (user != null) {
            return new FindIdResponseDto(user.getAccount());
        }

        return null;
    }



}
