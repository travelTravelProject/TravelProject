package com.travel.mapper;

import com.travel.entity.Gender;
import com.travel.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void saveTest() {
        //given
        User member = User.builder()
                .account("haha")
                .password("haha123!")
                .name("하하하")
                .email("haha@gmail.com")
                .nickname("하이하이")
                .birthday("1995-12-15")
                .gender(Gender.valueOf("M"))
                .build();
        //when
        boolean flag = userMapper.save(member);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("haha 계정명 조회하면 하하하가 조회된다.")
    void findOneTest() {
        //given
        String acc = "haha";
        //when
        User foundUser  = userMapper.findOne(acc);
        //then
        assertEquals("하하하", foundUser.getName());
    }


    @Test
    @DisplayName("평문의 암호를 인코딩하여야 한다.")
    void encodingTest() {
        //given
        String rawPassword = "abc1234!";
        //when
        String encodedPassword = passwordEncoder.encode(rawPassword);
        //then
        System.out.println("encodedPassword = " + encodedPassword);

    }


}


