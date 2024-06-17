package com.travel.project.service;

import com.travel.project.dto.request.ReplyRequestPostDto;
import com.travel.project.dto.response.ReplyResponseDetailDto;
import com.travel.project.entity.Reply;
import com.travel.project.mapper.ReplyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReplyServiceTest {

    @Autowired @InjectMocks
    ReplyService replyService;
    @Autowired @Mock
    ReplyMapper replyMapper;

    @Test
    @DisplayName("댓글 목록 전체조회 테스트")
    void getRepliesTest() {
        //given
        long boardId = 24;
        //when
        List<ReplyResponseDetailDto> response = replyService.getReplies(boardId);
        //then
        assertThat(response).isNotNull();
        assertThat(response.get(0).getText()).isEqualTo("내용을 수정했어요 수정됐나요?");
        assertThat(response.get(1).getText()).isEqualTo("하하호호댓글");
        assertThat(response.size()).isEqualTo(replyMapper.count(boardId));
    }

    @Test
    @DisplayName("댓글 등록(입력) 테스트")
    void registerTest() {
        //given
        ReplyRequestPostDto dto = new ReplyRequestPostDto();
        dto.setText("ReplyService 댓글 등록 테스트 성공입니다.");
        dto.setAuthor("김요한");
        dto.setBoardId(24L);
        dto.setParentReplyId(1L);

        Reply expectedReply = Reply.builder()
                .replyText(dto.getText())
                .boardId(dto.getBoardId())
                .account(dto.getAuthor())  // account 데이터를 설정
                .parentReplyId(dto.getParentReplyId())  // parentReplyId 데이터를 설정
                .build();

        when(replyMapper.save(any(Reply.class))).thenReturn(true);
        //when
        boolean flag = replyService.register(dto);
        //then
        assertThat(flag).isTrue();
    }

}