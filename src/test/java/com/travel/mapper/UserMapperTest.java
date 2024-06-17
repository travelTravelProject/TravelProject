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
}

