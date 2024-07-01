package com.travel.project.controller;

import com.travel.project.mapper.UserDetailMapper;
import com.travel.project.mapper.UserMapper;
import com.travel.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserManagementController {

    private final UserMapper userMapper;
    private final UserDetailMapper userDetailMapper;
    private final UserService userService;


}
