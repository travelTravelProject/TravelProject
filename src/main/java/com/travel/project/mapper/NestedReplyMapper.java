package com.travel.project.mapper;

import com.travel.project.entity.NestedReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NestedReplyMapper {

    // 대댓글 등록
    boolean save(NestedReply reply);

    // 대댓글 수정
    boolean modify(NestedReply reply);

    // 대댓글 삭제
    boolean delete(Long replyId);

    // 특정 댓글에 달린 대댓글 목록 조회
    List<NestedReply> findAll(long replyId);

    // 총 대댓글 수 조회
    int count(long replyId);
}
