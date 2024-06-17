package com.travel.service;

import com.travel.dto.SignUpDto;
import com.travel.entity.User;
import com.travel.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
//    private final UserService userService;

    // 회원가입 중간처리
    public boolean join(SignUpDto dto) {

        User user = dto.toEntity();
        return userMapper.save(user);
    }

    // 아이디, 이메일 중복검사
    public boolean checkIdentifier(String type, String keyword) {
        return userMapper.existById(type, keyword);
    }

}
