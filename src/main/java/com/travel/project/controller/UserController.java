package com.travel.project.controller;

import com.travel.project.dto.request.LoginDto;
import com.travel.project.dto.request.SignUpDto;

import com.travel.project.dto.request.UpdateProfileDto;
import com.travel.project.entity.Gender;
import com.travel.project.entity.User;

import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.mapper.UserMapper;
import com.travel.project.service.LoginResult;

import com.travel.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDate;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    @Value("${file.upload.root-path}")
    private String rootPath;

    private final UserService userService;

    // 회원가입 양식 열기
    @GetMapping("/sign-up")
    public String signUp() {
        log.info("sign-up GET : forwarding to sign-up.jsp");
        return "/sign-up";
    }

    // 회원가입 요청 처리
    @PostMapping("/sign-up")
    public String signUp(@Validated SignUpDto dto) {

        log.info("sign-up POST : forwarding to sign-up.jsp");

        boolean flag = userService.join(dto);
//        log.info("sign-up POST: join result: {}", flag);

        return flag ? "redirect:/sign-in" : "redirect:/sign-up";
    }

    // 아이디, 이메일 중복검사 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check(String type, String keyword) {
        boolean flag = userService.checkIdentifier(type, keyword);
//        log.debug("{} check result : {} " , type, flag);
        return ResponseEntity.ok().body(flag);
    }

    // 마이페이지 열기
    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        log.info("mypage GET : forwarding to mypage.jsp");

        // 세션에서 로그인된 사용자 정보 가져오기
        LoginUserInfoDto user = (LoginUserInfoDto) session.getAttribute("user");
//        log.debug("\"User information retrieved from session: {}\", user");

        System.out.println("user = " + user);

        if(user == null) {
            // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/sign-in";
        }

        // 모델에 사용자 정보 추가
        model.addAttribute("user", user);
        return "/mypage";
    }

    // 마이페이지 프로필 수정페이지 열기
    @GetMapping("/mypage/update")
    public String showUpdatePage(HttpSession session, Model model) {
        log.info("mypage GET : forwarding to mypage-update.jsp");

        LoginUserInfoDto user = (LoginUserInfoDto) session.getAttribute("user");
        if(user == null) {
            // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/sign-in";
        }

        model.addAttribute("user", user);
        return "mypage-update";
    }

    // 마이페이지 프로필 수정하기
    @PostMapping("/mypage/update")
    public String myPageUpdate(@Validated UpdateProfileDto dto,
                               HttpSession session,
                               RedirectAttributes ra) {
        log.info("updateProfile POST: {}", dto);

        LoginUserInfoDto loginUser = (LoginUserInfoDto)session.getAttribute("user");
        log.info("loginUser = " + loginUser);
        log.info("loginUser.getAccount() = " + loginUser.getAccount());
        log.info("dto.getAccount() = " + dto.getAccount());

        if(!dto.getAccount().equals(loginUser.getAccount())) {
            return "redirect:/sign-in";
        }

        User updatedUser = User.builder()
                .account(dto.getAccount())
                .name(dto.getName())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .build();

        // 데이터베이스에 업데이트된 사용자 정보 저장
        userService.saveUpdateUser(updatedUser);

        // 세션에 업데이트된 사용자 정보 저장
        session.setAttribute("user", new LoginUserInfoDto(updatedUser));
        System.out.println("updatedUser = " + updatedUser);
        log.info("Updated user profile: {}", updatedUser);

        // 사용자에게 알림 메시지
        ra.addFlashAttribute("successMessage", "프로필이 성공적으로 업데이트 되었습니다.");

        return "redirect:/mypage";
    }



//===============================================================================
//===============================================================================

    @GetMapping("/sign-in")
    public String signIn(HttpSession session){
        log.info("/sign-in GET : forwarding to sign-in jsp");
        System.out.println("로그인 페이지 111111111111");
        //return "/members/sign-up";


        //로그인 한사람이 이 요청 보내믄 돌려보내버려
        // session 의 특정 데이터 : session.removeAttribute("login");
        LoginUserInfoDto login =  //로그인 한 사람이면 login 값을 가졌을 것이거 아니면 null 이다.
                (LoginUserInfoDto) session.getAttribute("login");

        if(login != null){ //로그인 한 사람
            System.out.println("로그인 한 사람은 홈으로 돌려보내기 ");
            return "redirect:/"; //다시 홈으로 돌려보내버린다요
        }

        /* // 아래 조건문을 LoginUtil 로 빼서 작업함

         */
        return "/sign-in";
    }

    //로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDto dto, RedirectAttributes ra, HttpServletRequest request, HttpServletResponse response){ // (LoginDto dto, Model model) Model model : 사용 xx
        //RedirectAttributes : 리다이렉트된 페이지에 데이터를 전달
        log.info("/sign-in POST");
        log.debug("param {}", dto);
        System.out.println("로그인 페이지 버튼!");

        //세션 얻기
        HttpSession session = request.getSession(); //사용자 기억 해 줄

        LoginResult result = userService.authenticate(dto,session,response);
        System.out.println("result = " + result);
        //로그인 검증 결과를 jsp 한테 보내는거임
        //리다이렉트시 리다이렉트 된 페이지에 데이터를 보낼 땐 model 객체 사용 xx
        //request 객체는 한번 요청이 끝나면 메모리에서 제거댐유
        //model.addAttribute("result", result); // 리다이렉트시에는 model 을 사용하면 안된다.
        ra.addFlashAttribute("result", result);
        if(result == LoginResult.SUCCESS){
            return "redirect:/"; //로그인 성공시 index 페이지로 이동
        }
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session, HttpServletRequest request){
        // 세션 구하기
        //HttpSession session = request.getSession();

        // 세션에서 로그인 기록 삭제
        session.removeAttribute("login");

        // 세션을 초기화 (reset)
        session.invalidate();

        // 홈으로 보내기
        return "redirect:/";
    }












}
