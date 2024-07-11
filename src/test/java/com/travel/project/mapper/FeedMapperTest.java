package com.travel.project.mapper;

import com.travel.project.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedMapperTest {

    @Autowired
    FeedMapper feedMapper;

    @Test
    @DisplayName("피드 작성 테스트")
    void postFeed() {
        //given
        Board newB = Board.builder()
                .account("tester2")
                .categoryId(2)
                .content("스프링테스트2")
                .build();
        //when
        long newBoardId = feedMapper.saveFeed(newB);

        //then
        System.out.println(newBoardId);
        assertNotNull(newBoardId);
    }



}