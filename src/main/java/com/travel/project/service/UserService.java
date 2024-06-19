package com.travel.project.service;

import com.travel.project.dto.request.SignUpDto;
import com.travel.project.entity.User;
import com.travel.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

}
