package com.travel.mapper;

import com.travel.entity.Gender;
import com.travel.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;


    @Test
    @DisplayName("회원가입 성공")
    void saveTest() {
        //given
        User member = User.builder()
                .account("kuromi")
                .password("abc1234!")
                .name("쿠로미")
                .email("kuromi@gmail.com")
                .nickname("쿠로쿠로")
                .birthday("2020-06-17")
                .gender(Gender.valueOf("F"))
                .build();
        //when
        boolean flag = userMapper.save(member);
        //then
        assertTrue(flag);
    }
}

