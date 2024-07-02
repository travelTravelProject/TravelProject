package com.travel.project.controller;

import com.travel.project.mapper.UserDetailMapper;
import com.travel.project.mapper.UserMapper;
import com.travel.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserManagementController {

    private final UserMapper userMapper;
    private final UserDetailMapper userDetailMapper;
    private final UserService userService;

    // 관리자 페이지 열기
    @GetMapping("/admin")
    public String admin() {
        log.info("admin GET : forwarding to user-management.jsp");
        return "user-management";
    }


}
