package com.travel.project.service;

import com.travel.project.dto.request.SignUpDto;
import com.travel.project.entity.Gender;
import com.travel.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입하면 비밀번호 인코딩된다.")
    void joinTest() {
        //given
        SignUpDto dto = SignUpDto.builder()
                .account("kitty")
                .password("kkk123!")
                .email("kitty@gmail.com")
                .name("헬로키티")
                .nickname("키티키티")
                .birthday(LocalDate.of(1998, 12, 07))
                .gender(Gender.valueOf("F"))
                .build();
        //when
        boolean flag = userService.join(dto);
        //then
        assertTrue(flag);

    }


}