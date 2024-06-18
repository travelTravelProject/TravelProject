package com.travel.mapper;

import com.travel.entity.AccBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccBoardMapperTest {

    @Autowired
    AccBoardMapper accBoardMapper;

    @Test
    @DisplayName("글작성")
    void insertTest() {

        for (int i = 1; i < 10; i++) {
            AccBoard ab = new AccBoard();
            ab.setTitle("제목" + i);
            ab.setWriter("작성자" + i);
            ab.setContent("내용내용" + i);
            ab.setAccount("account" + i);

            accBoardMapper.save(ab);
        }
    }

    @Test
    @DisplayName("상세조회")
    void fiondOneTest() {
        //given
        int boardId = 1;
        //when
        List<AccBoard> all = accBoardMapper.findAll();
        //then
        System.out.println("one = " + all);
    }

    @Test
    @DisplayName("삭제")
    void deleteTest() {
        //given
        int boardId = 29;
        //when
        boolean deleteOne = accBoardMapper.delete(boardId);
        //then
    }

}