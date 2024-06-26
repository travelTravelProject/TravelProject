package com.travel.project.mapper;

import com.travel.project.entity.AccBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccBoardMapperTest {

    @Autowired
    AccBoardMapper accBoardMapper;

    @Test
    @DisplayName("게시물 300개 만들기")
    void bulkInsertTest() {
        //given
        for (int i = 1; i <= 300; i++) {
            AccBoard ab = new AccBoard();
            ab.setAccount("TEST ACCOUNT" + i);
            ab.setWriter("TEST WRITER" + i);
            ab.setContent("TEST CONTENT" + i);
            ab.setTitle("TEST TITLE" + i);
            ab.setLocation("TEST LOCATION");
            ab.setCategoryId(i);
            // 설정된 날짜
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(i % 10); // startDate로부터 i % 10 일 후로 설정

            ab.setStartDate(startDate);
            ab.setEndDate(endDate);


            accBoardMapper.save(ab);


        }
    }

}