package com.travel.project.service;

import com.travel.project.common.Page;
import com.travel.project.common.PageMaker;
import com.travel.project.dto.request.ReplyRequestModifyDto;
import com.travel.project.dto.request.ReplyRequestPostDto;
import com.travel.project.dto.response.ReplyListDto;
import com.travel.project.dto.response.ReplyResponseDetailDto;
import com.travel.project.entity.Reply;
import com.travel.project.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// 댓글에 대한 중간로직을 처리
public class ReplyService {

    private final ReplyMapper replyMapper;

    // 댓글 목록 전체조회
    public List<ReplyResponseDetailDto> getReplies(long boardId) {
        List<Reply> replies = replyMapper.findAll(boardId);
        return replies.stream()
                .map(r -> new ReplyResponseDetailDto(r))
                .collect(Collectors.toList());
    }

    // 댓글 입력
    public boolean register(ReplyRequestPostDto dto) {
        Reply reply = Reply.builder()
                .replyText(dto.getText())
                .replyWriter(dto.getAuthor())
                .boardId(dto.getBoardId())
                .parentReplyId(dto.getParentReplyId())
                .account("testuser") // 나중에는 로그인한 유저로 교체(세션)
                .build();

        boolean flag = replyMapper.save(reply);
        return flag;
    }

    // 댓글 삭제
    // @Transactional : 삭제시 에러나 실패했을 때 원래의 데이터를 유지하기 위함
    @Transactional
    public List<ReplyResponseDetailDto> remove(long replyId) {
        // 댓글 번호로 원본 글번호 찾기
        long boardId = replyMapper.findBno(replyId);
        boolean flag = replyMapper.delete(replyId);

        // 댓글 삭제 후 삭제된 댓글 목록을 반환
        return flag ? getReplies(boardId) : Collections.emptyList();
    }

    // 댓글 수정
    public List<ReplyResponseDetailDto> modify(ReplyRequestModifyDto dto) {
        // dto -> entity
        // -> 데이터가 클라이언트에서 데이터베이스에 저장되도록 하기 위해
        replyMapper.modify(dto.toEntity());
        // 수정 후 댓글 목록 반환 (수정사항 반영)
        return getReplies(dto.getBoardId());
    }

    // 대댓글 목록 전체조회
    // 대댓글도 ReplyListDto로 바꿔야되나? (페이징 적용해야하나?)
    public List<ReplyResponseDetailDto> getNestedReplies(long parentReplyId) {
        List<Reply> replies = replyMapper.findAllNestedReply(parentReplyId);
        return replies.stream()
                .map(reply -> new ReplyResponseDetailDto(reply))
                .collect(Collectors.toList());
    }

    // 대댓글 입력
    public boolean registerNestedReply(ReplyRequestPostDto dto) {

        Reply reply = Reply.builder()
                .replyText(dto.getText())
                .replyWriter(dto.getAuthor())
                .boardId(dto.getBoardId())
                .parentReplyId(dto.getParentReplyId())
                .build();
        boolean flag = replyMapper.save(reply);
        return flag;
    }

    // 대댓글 삭제
    @Transactional
    public List<ReplyResponseDetailDto> removeNestedReply(long replyId) {
        // 부모 댓글 번호 찾기
        long parentReplyId = replyMapper.findParentReplyId(replyId);
        boolean flag = replyMapper.delete(replyId);
        return flag ? getNestedReplies(parentReplyId) : Collections.emptyList();
    }

    // 대댓글 수정
    public List<ReplyResponseDetailDto> modifyNestedReply(ReplyRequestModifyDto dto) {
        // dto -> entity
        replyMapper.modify(dto.toEntity());
        return getNestedReplies(dto.getParentReplyId());
    }
}
