//package com.travel.project.service;
//
//import com.travel.project.common.Page;
//import com.travel.project.dto.request.ReplyRequestModifyDto;
//import com.travel.project.dto.request.ReplyRequestPostDto;
//import com.travel.project.dto.response.ReplyListDto;
//import com.travel.project.dto.response.ReplyResponseDetailDto;
//import com.travel.project.entity.Reply;
//import com.travel.project.mapper.ReplyMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class ReplyServiceTest {
//
//    @Autowired
//    ReplyService replyService;
//    @Autowired
//    ReplyMapper replyMapper;
//
//    @Test
//    @DisplayName("댓글 목록 전체조회 테스트")
//    void getRepliesTest() {
//        //given
//        long boardId = 2;
//        //when
//        List<ReplyResponseDetailDto> response = replyService.getReplies(boardId);
//        //then
//        assertThat(response).isNotNull();
//        assertThat(response.getReplies().get(0).getText()).isEqualTo("하하호호댓글2");
//    }
//
//    @Test
//    @DisplayName("댓글 등록(입력) 테스트 - 엔티티가 dto로 변환이 되어서 save되는지")
//    void registerTest() {
//        //given
//        ReplyRequestPostDto dto = new ReplyRequestPostDto();
//        dto.setText("ReplyService 댓글 등록 테스트 성공입니다.");
//        dto.setBoardId(15L);
//        dto.setAuthor("한기범");
//        dto.setParentReplyId(null);
//
//        //when
//        boolean flag = replyService.register(dto);
//        //then
//        assertThat(flag).isTrue();
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 테스트")
//    void removeTest() {
//        //given
//        long replyId = 1L;
//        long boardId = 1L;
//        //when
//        List<ReplyResponseDetailDto> response = replyService.remove(replyId);
//        //then
//        assertThat(response).isNotNull();
//    }
//
//    @Test
//    @DisplayName("댓글 수정 테스트")
//    void modifyTest() {
//        //given
//        long boardId = 2L;
//        ReplyRequestModifyDto dto = new ReplyRequestModifyDto();
//        dto.setReplyId(2L);
//        dto.setNewText("수정했잖아");
//        dto.setBoardId(boardId);
//        //when
//        List<ReplyResponseDetailDto> response = replyService.modify(dto);
//        //then
//        assertThat(response.getReplies().get(0).getText()).isEqualTo("수정했잖아");
//    }
//
//
//    @Test
//    @DisplayName("부모댓글번호에 따른 대댓글 목록 전체조회 테스트")
//    void getNestedRepliesTest() {
//        //given
//        long parentReplyId = 2L;
//        //when
//        List<ReplyResponseDetailDto> response = replyService.getNestedReplies(parentReplyId);
//        //then
//        assertThat(response).isNotNull();
//        assertThat(response.size()).isEqualTo(10);
//        assertThat(response.get(0).getText()).isEqualTo("하하호호댓글1"); // 대댓글 내용 확인
//    }
//
//
//}