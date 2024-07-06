package com.travel.project.controller;

import com.travel.project.dto.FindIdResponseDto;
import com.travel.project.dto.PasswordChangeDto;
import com.travel.project.dto.PasswordResetRequestDto;
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
import org.springframework.web.multipart.MultipartFile;
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
    private final UserDetailMapper userDetailMapper;
    private final UserService userService;

    @Value("${file.upload.root-path}")
    private String rootPath;

//     static String rootPath = System.getProperty("user.dir")
//             + "/src/main/resources/static/assets/upload";

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

        System.out.println("LoginUserInfoDto user = " + user);

        if (user == null) {
            // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/sign-in";
        }

        // 사용자 계정 정보를 기반으로 UserDetail 객체 가져오기
        UserDetail userDetail = userService.getUserDetailByAccount(user.getAccount());
        System.out.println("userDetail = " + userDetail);

        userService.updateUser(user, session);

        // 생년월일 연령대 계산
        String birthYear = userService.calculateAgeGroup(user.getAccount());

        model.addAttribute("user", user);
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("birthYear", birthYear); // 연령대

        return "mypage";
    }

    // 마이페이지 프로필 수정페이지 열기
    @GetMapping("/mypage/update")
    public String showUpdatePage(HttpSession session, Model model) {
        log.info("mypage GET : forwarding to mypage-update.jsp");

        LoginUserInfoDto user = (LoginUserInfoDto) session.getAttribute("user");
        if (user == null) {
            // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/sign-in";
        }

        // 사용자 계정 정보를 기반으로 UserDetail 객체 가져오기
        UserDetail userDetail = userService.getUserDetailByAccount(user.getAccount());
        System.out.println("userDetail = " + userDetail);

        userService.updateUser(user, session);

        model.addAttribute("user", user);
        model.addAttribute("userDetail", userDetail);

        System.out.println("user = " + user);

        return "mypage-update";
    }

    // 마이페이지 프로필 수정하기
    @PostMapping("/mypage/update")
    public String myPageUpdate(@Validated UpdateProfileDto dto, HttpSession session) {

        log.info("mypage POST : forwarding to mypage-update.jsp");

        UserDetail existingUserDetail = userService.getUserDetailByAccount(dto.getAccount());
        log.info("existingUserDetail: {}", existingUserDetail);

        // 프로필 이미지 업로드 및 경로 설정
        MultipartFile profileImage = dto.getProfileImage();
        String profilePath = null;
        String DEFAULT_PROFILE_IMAGE_PATH = "assets/img/anonymous.jpg";

        if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
            profilePath = FileUtil.uploadFile(profileImage);
            log.info("profilePath = " + profilePath);
        } else if (existingUserDetail == null || existingUserDetail.getProfileImage() == null) {
            // 새로 가입한 회원이거나 기존 회원의 프로필 이미지가 없을 경우 기본 이미지 사용
            profilePath = DEFAULT_PROFILE_IMAGE_PATH;
        } else {
            profilePath = existingUserDetail.getProfileImage();
        }

//        existingUserDetail.setProfileImage(profilePath);
//        userService.saveUserDetail(existingUserDetail);
//        userService.saveOrUpdateUserDetail(dto, session, profilePath);

        // 세션에서 로그인 사용자 정보 가져오기
        LoginUserInfoDto loginUser = (LoginUserInfoDto) session.getAttribute("user");

        if (!loginUser.getAccount().equals(loginUser.getAccount())) {
            return "redirect:/sign-in";
        }

        userService.saveOrUpdateUserDetail(dto, session, profilePath);
        userService.updateUser(loginUser, session);
        userService.saveUpdateUser(dto);

        loginUser.setName(dto.getName());
        loginUser.setNickname(dto.getNickname());
        loginUser.setMbti(dto.getMbti());
        loginUser.setOneLiner(dto.getOneLiner());
        dto.setProfileImage(profileImage);
        loginUser.setProfileImage(profilePath);

        // 세션에 업데이트된 사용자 정보 저장
        session.setAttribute("user", loginUser);
        System.out.println("loginUser = " + loginUser);

        log.info("Updated user profile: {}", loginUser);
        // Updated user profile: LoginUserInfoDto(account=kitty, name=키티키티123, nickname=헬로키티kitty,
        // email=kitty@gmail.com, auth=COMMON, birthday=1996-06-12, gender=F, mbti=ENTP,
        // oneLiner=하이하이 키티 헬로헬로, profileImage=/assets/upload/2024/07/04/b6242aed-3495-4871-8406-cc09f74bbdd4_다운로드 (2).jfif)

        session.setAttribute("user", loginUser);

        return "redirect:/mypage";
    }


//===============================================================================
//===============================================================================

    @GetMapping("/sign-in")
    public String signIn(HttpSession session
            , @RequestParam(required = false) String redirect,
                         HttpServletRequest request) {
        log.info("/sign-in GET : forwarding to sign-in jsp");

        //session.setAttribute("redirect", redirect);

        //직전 페이지 주소 (https://blog.naver.com/varkiry05/221312360666)
        String referrer = request.getHeader("Referer");
        session.setAttribute("redirect", referrer);


        log.info("/sign-in GET : forwarding to sign-in.jsp");
        return "/sign-in";
    }

    //로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDto dto,
                         RedirectAttributes ra,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        log.info("/sign-in POST");
        log.debug("parameter {}", dto);
        System.out.println("!!!! dto = " + dto);
        System.out.println("로그인 페이지 버튼!");

        //세션 얻기
        HttpSession session = request.getSession(); //사용자 기억 해 줄

        LoginResult result = userService.authenticate(dto, session, response);

        System.out.println("result = " + result);

        ra.addFlashAttribute("result", result);

        if (result == LoginResult.SUCCESS) {

            // 혹시 세션에 리다이렉트 URL 이 있다면
            String redirect = (String) session.getAttribute("redirect");
            System.out.println("redirect = " + redirect);
            if (redirect != null) {
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
//    @GetMapping("/find-pw")
//    public String findPw() {
//        System.out.println("비밀번호 찾기 페이지");
//        return "/find-pw";
//    }

    // 비밀번호 찾기 폼 열기
    @GetMapping("/find-password")
    public String showFindPasswordForm() {
        return "/find-password";
    }

    // 비밀번호 찾기 요청 처리
    @PostMapping("/find-password")
    public String verifyUserForPasswordReset(@ModelAttribute PasswordResetRequestDto dto, RedirectAttributes ra, HttpSession session) {
        boolean verified = userService.verifyUserForPasswordReset(dto);
        if (verified) {
            session.setAttribute("account", dto.getAccount());
            return "redirect:/change-password";
        }
        ra.addFlashAttribute("error", "User verification failed.");
        return "redirect:/find-password";
    }


    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute PasswordChangeDto dto, HttpSession session, RedirectAttributes ra) {
        String account = (String) session.getAttribute("account");
        if (account == null) {
            ra.addFlashAttribute("error", "Session expired. Please try again.");
            return "redirect:/find-password";
        }
        dto.setAccount(account);
        boolean changed = userService.changePassword(dto);
        if (changed) {
            ra.addFlashAttribute("message", "Password changed successfully.");
            session.removeAttribute("account");
            return "redirect:/sign-in";
        }
        ra.addFlashAttribute("error", "Password change failed.");
        return "redirect:/change-password";
    }


// 임시 main.jsp

    @GetMapping("/feedtest")
    public String feedtest() {
        return "/feedtest"; // find-id.html 또는 find-id.jsp와 같은 뷰 이름 반환
    }


}




