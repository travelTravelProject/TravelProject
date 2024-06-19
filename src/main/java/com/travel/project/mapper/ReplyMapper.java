package com.travel.project.mapper;

import com.travel.project.common.Page;
import com.travel.project.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    // 댓글 등록
    boolean save(Reply reply);

    // 댓글 수정
    boolean modify(Reply reply);

    // 댓글 삭제
    boolean delete(long replyId);

    // 특정 게시물에 달린 댓글 목록 조회 - 게시물 번호 파라미터 필요
    // 나중에 페이징 필요?
    List<Reply> findAll(long boardId);
    // 총 댓글 수 조회
    int count(long boardId);

    // 댓글 번호로 원본글 번호 찾기
    long findBno(long replyId);

    // 부모 댓글번호로 대댓글 목록 조회
    List<Reply> findAllNestedReply(long parentReplyId);

    // 대댓글의 부모 댓글번호 찾기
    long findParentReplyId(long replyId);
}
