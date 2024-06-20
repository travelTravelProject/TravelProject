package com.travel.project.controller;

import com.travel.project.dto.request.SignUpDto;
import com.travel.project.entity.Gender;
import com.travel.project.entity.User;
import com.travel.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

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

}
