package com.travel.project.mapper;

import com.travel.project.common.Page;
import com.travel.project.entity.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired
    ReplyMapper replyMapper;

    @Test
    @DisplayName("")
    void bulk() {
        for (int i = 1; i <= 10; i++) {
            Reply reply = Reply.builder()
                    .boardId(2)
                    .account("testuser")
                    .replyText("하하호호댓글" + i)
                    .replyWriter("꾸러긔" + i)
                    .parentReplyId(2L)
                    .build();
            replyMapper.save(reply);

        }
    }

    @Test
    @DisplayName("특정 게시글에 대한 댓글목록 전체 조회")
    void findAllTest() {
        // given
        long boardNo = 10;
        // when
        List<Reply> replies = replyMapper.findAll(boardNo);
        // then
        replies.forEach(System.out::println);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteTest() {
        //given
        long replyId = 1;
        //when
        replyMapper.delete(replyId);
        //then
    }

    @Test
    @DisplayName("댓글 수정")
    void modifyTest() {
        //given
        long replyId = 2;
        Reply reply = Reply.builder()
                .replyId(replyId)
                .replyText("내용을 수정했어요 수정됐나요?")
                .build();
        //when
        replyMapper.modify(reply);
        //then
    }

    @Test
    @DisplayName("특정 게시글에 달린 총 댓글 수 조회")
    void countTest() {
        //given
        long boardId = 24;
        //when
        int repliesCount = replyMapper.count(boardId);
        //then
        System.out.println("댓글 수 = " + repliesCount);
    }

    @Test
    @DisplayName("댓글 번호로 원본글 번호 찾기")
    void findBnoTest() {
        //given
        long replyId = 2;
        //when
        long bno = replyMapper.findBno(replyId);
        //then
        System.out.println("원본 글 번호 = " + bno);
    }

    @Test
    @DisplayName("부모 댓글번호로 대댓글 목록 조회")
    void findAllNestedReplyTest() {
        //given
        Long parentReplyId = 2L;

        // 부모 댓글 번호를 설정하고 테스트 데이터가 존재한다고 가정
        // 실제 테스트를 위해서는 데이터베이스에 해당 부모 댓글과 대댓글이 존재해야함
        //when
        List<Reply> nestedReplies = replyMapper.findAllNestedReply(parentReplyId);
        //then
        assertThat(nestedReplies).isNotNull();
        for (Reply reply : nestedReplies) {
            assertThat(reply.getParentReplyId()).isEqualTo(parentReplyId);
        }
        nestedReplies.forEach(System.out::println);
    }

}