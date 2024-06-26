package com.travel.project.controller;

import com.travel.project.dto.FindIdResponseDto;
import com.travel.project.dto.request.LoginDto;
import com.travel.project.dto.request.SignUpDto;

import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.login.LoginUtil;
import com.travel.project.service.LoginResult;

import com.travel.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

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
    public String signUp(SignUpDto dto) {

        log.info("sign-up POST : forwarding to sign-up.jsp");

        boolean flag = userService.join(dto);
//        log.info("sign-up POST: join result: {}", flag);

        return flag ? "redirect:/" : "redirect:/sign-up";
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
    public String myPage() {
        log.info("mypage GET : forwarding to mypage.jsp");
        return "/mypage";
    }


    // 마이페이지 테스트
    @GetMapping("/mypage/update")
    public String myPage(Model model) {
        log.info("mypage GET : forwarding to mypage-update.jsp");

//        User user = new User();
//        user.setName("John Doe");
//        user.setEmail("john.doe@example.com");
//        user.setNickname("johnny");
//        user.setBirthday(LocalDate.parse("1990-01-01"));
//        user.setGender(Gender.valueOf("M"));

//        model.addAttribute("user", user);
        return "/mypage-update";
    }



//===============================================================================
//===============================================================================

    @GetMapping("/sign-in")
    public String signIn(HttpSession session
            , @RequestParam(required = false) String redirect
    ) {
        log.info("/sign-in GET : forwarding to sign-in jsp");
        System.out.println("로그인 페이지 111111111111");
        //return "/sign-up";


        //로그인 한사람이 이 요청 보내믄 돌려보내버려
        // session 의 특정 데이터 : session.removeAttribute("login");
        LoginUserInfoDto login =  //로그인 한 사람이면 login 값을 가졌을 것이거 아니면 null 이다.
                (LoginUserInfoDto) session.getAttribute("login");

//        if(login != null){ //로그인 한 사람a
//            System.out.println("로그인 한 사람은 홈으로 돌려보내기 ");
//            return "redirect:/"; //다시 홈으로 돌려보내버린다요
//        }

//        if(LoginUtil.isLoggedIn(session)){ //로그인 한 사람
//            System.out.println("LoginUtil 사용하요 로그인 한 사람은 홈으로 돌려보내기 ");
//            return "redirect:/"; //다시 홈으로 돌려보내버린다요
//        }

        /* // 아래 조건문을 LoginUtil 로 빼서 작업함

         */
        //return "/sign-in";

        session.setAttribute("redirect", redirect);

        log.info("/sign-in GET : forwarding to sign-in.jsp");
        return "/sign-in";
    }

    //로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDto dto,
                         RedirectAttributes ra,
                         HttpServletRequest request,
                         HttpServletResponse response){ // (LoginDto dto, Model model) Model model : 사용 xx
        //RedirectAttributes : 리다이렉트된 페이지에 데이터를 전달


        log.info("/sign-in POST");
        log.debug("parameter {}", dto);
        System.out.println("!!!! dto = " + dto);
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
//        if(result == LoginResult.SUCCESS){
//            return "redirect:/"; //로그인 성공시 index 페이지로 이동
//        }
//        return "redirect:/sign-in";

        if (result == LoginResult.SUCCESS) {

            // 혹시 세션에 리다이렉트 URL이 있다면
            String redirect = (String) session.getAttribute("redirect");
            if (redirect != null) {
                session.removeAttribute("redirect");
                return "redirect:" + redirect;
            }

            return "redirect:/"; // 로그인 성공시
        }

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {

        // 세션 구하기
        HttpSession session = request.getSession();

        // 자동로그인 상태인지 확인
        if (LoginUtil.isAutoLogin(request)) {
            // 쿠키를 제거하고, DB에도 자동로그인 관련데이터를 원래대로 해놓음
            userService.autoLoginClear(request, response);
        }

        // 세션에서 로그인 기록 삭제
        session.removeAttribute("login");

        // 세션을 초기화 (reset)
        session.invalidate();

        // 홈으로 보내기
        return "redirect:/";
    }

//===============================================================================
//===============================================================================

    @GetMapping("/find-id")
    public String findId() {
        System.out.println("아이디 찾기 페이지");
        return "/find-id"; // find-id.html 또는 find-id.jsp와 같은 뷰 이름 반환
    }

    @PostMapping("/find-id")
    public String findId(@RequestParam String name,
                         @RequestParam String email,
                         Model model) {
        // Call service method to find ID based on name and email
        FindIdResponseDto result = userService.findIdByNameAndEmail(name, email);

        if (result != null) {
            // If ID is found, pass it to find-id-result.jsp
            model.addAttribute("result", result);
            return "find-id-result"; // Redirect to find-id-result.jsp
        } else {
            // If ID is not found, handle appropriately
            model.addAttribute("error", "User not found");
            return "find-id"; // Redirect back to find-id.jsp with an error message
        }
    }


    // 비밀번호 찾기 페이지 열기
    @GetMapping("/find-pw")
    public String findPw() {
        System.out.println("비밀번호 찾기 페이지");
        return "/find-pw";
    }










}