package com.travel.project.controller;

import com.travel.project.dto.FindIdResponseDto;
import com.travel.project.dto.request.LoginDto;
import com.travel.project.dto.request.SignUpDto;

import com.travel.project.dto.request.UpdateProfileDto;
import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.login.LoginUtil;
//import com.travel.project.dto.request.UpdateProfileDto;
import com.travel.project.entity.Gender;
import com.travel.project.entity.User;

import com.travel.project.entity.UserDetail;
import com.travel.project.mapper.UserDetailMapper;
import com.travel.project.mapper.UserMapper;
import com.travel.project.service.LoginResult;

import com.travel.project.service.UserService;
import com.travel.project.util.FileUtil;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserDetailMapper userDetailMapper;

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

        if (user == null) {
            // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/sign-in";
        }

        // 사용자 계정 정보를 기반으로 UserDetail 객체 가져오기
        UserDetail userDetail = userService.getUserDetailByAccount(user.getAccount());
        System.out.println("userDetail = " + userDetail);

        model.addAttribute("user", user);
        model.addAttribute("userDetail", userDetail);

        return "mypage";
    }

    // 마이페이지에서 프로필 사진 등록
//    @PostMapping("/mypage")
//    public String mypage(@Validated LoginUserInfoDto dto, HttpSession session, Model model) {
//        log.info("mypage POST : forwarding to mypage.jsp");
//
////        String uploadedFile = FileUtil.uploadFile(dto.getProfileImage());
//    }

    // 마이페이지 프로필 수정페이지 열기
    @GetMapping("/mypage/update")
    public String showUpdatePage(HttpSession session, Model model) {
        log.info("mypage GET : forwarding to mypage-update.jsp");

        LoginUserInfoDto user = (LoginUserInfoDto) session.getAttribute("user");
        if (user == null) {
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

        LoginUserInfoDto loginUser = (LoginUserInfoDto) session.getAttribute("user");
        log.info("loginUser = " + loginUser);
        log.info("loginUser.getAccount() = " + loginUser.getAccount());
        log.info("dto.getAccount() = " + dto.getAccount());

        if (!dto.getAccount().equals(loginUser.getAccount())) {
            return "redirect:/sign-in";
        }

        UpdateProfileDto updatedUser = UpdateProfileDto.builder()
                .account(dto.getAccount())
                .name(dto.getName())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .oneLiner(dto.getOneLiner())
                .mbti(dto.getMbti())
                .rating(dto.getRating())
                .build();
//
        // 데이터베이스에 업데이트된 사용자 정보 저장
        userService.saveUpdateUser(updatedUser);
        userService.saveOrUpdateUserDetail(updatedUser);

        // 세션의 기존 LoginUserInfoDto 객체 업데이트
        loginUser.setName(dto.getName());
        loginUser.setEmail(dto.getEmail());
        loginUser.setNickname(dto.getNickname());
        loginUser.setMbti(dto.getMbti());
        loginUser.setOneLiner(dto.getOneLiner());
        loginUser.setRating(dto.getRating());

        // 세션에 업데이트된 사용자 정보 저장
        session.setAttribute("user", loginUser);
//        session.setAttribute("updatedUser", updatedUser);
//        session.setAttribute("updatedUser", new LoginUserInfoDto(updatedUser));
        System.out.println("updatedUser = " + updatedUser);
        log.info("Updated user profile: {}", updatedUser);

        // RedirectAttributes를 사용하여 사용자 정보 전달
        ra.addFlashAttribute("updatedUser", updatedUser);

        return "redirect:/mypage";
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
                         HttpServletResponse response) { // (LoginDto dto, Model model) Model model : 사용 xx
        //RedirectAttributes : 리다이렉트된 페이지에 데이터를 전달


        log.info("/sign-in POST");
        log.debug("parameter {}", dto);
        System.out.println("!!!! dto = " + dto);
        System.out.println("로그인 페이지 버튼!");

        //세션 얻기
        HttpSession session = request.getSession(); //사용자 기억 해 줄

        LoginResult result = userService.authenticate(dto, session, response);

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
