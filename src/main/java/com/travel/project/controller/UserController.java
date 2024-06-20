package com.travel.project.controller;

import com.travel.project.dto.request.LoginDto;
import com.travel.project.dto.request.SignUpDto;
import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.service.LoginResult;
import com.travel.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

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
