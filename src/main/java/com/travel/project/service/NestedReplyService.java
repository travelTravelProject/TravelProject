package com.travel.project.service;

import com.travel.project.dto.request.NestedReplyRequestModifyDto;
import com.travel.project.dto.request.NestedReplyRequestPostDto;
import com.travel.project.dto.response.NestedReplyListDto;
import com.travel.project.dto.response.NestedReplyResponseDetailDto;
import com.travel.project.entity.NestedReply;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.NestedReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NestedReplyService {

    private final NestedReplyMapper nestedReplyMapper;

    // 대댓글 목록 전체조회
    public NestedReplyListDto getReplies(long replyId) {
        List<NestedReply> nestedReplies = nestedReplyMapper.findAll(replyId);
        List<NestedReplyResponseDetailDto> dtoList = nestedReplies.stream()
                .map(r -> new NestedReplyResponseDetailDto(r))
                .collect(Collectors.toList());

        return NestedReplyListDto.builder()
                .nestedReplies(dtoList)
                .build();
    }

    // 대댓글 생성
    public boolean registerReply(NestedReplyRequestPostDto dto, HttpSession session) {

        NestedReply nestedReply =  NestedReply.builder()
                .replyText(dto.getText())
                .replyWriter(dto.getAuthor())
                .nestedReplyId(dto.getNestedReplyId())
                .replyId(dto.getReplyId())
                .account(LoginUtil.getLoggedInUserAccount(session))
                .build();

        boolean flag = nestedReplyMapper.save(nestedReply);
        return flag;
    }

    // 대댓글 삭제
    // dto 생성?
    @Transactional
    public NestedReplyListDto deleteReply(long nestedReplyId, long replyId) {
        boolean flag = nestedReplyMapper.delete(nestedReplyId);
        return flag ? getReplies(replyId) : null;
    }

    // 대댓글 수정
    public NestedReplyListDto modifyReply(NestedReplyRequestModifyDto dto) {

        nestedReplyMapper.modify(dto.toEntity());
        return getReplies(dto.getReplyId());
    }

}
