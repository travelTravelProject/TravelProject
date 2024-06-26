package com.travel.project.controller;

import com.travel.project.dto.request.NestedReplyRequestModifyDto;
import com.travel.project.dto.request.NestedReplyRequestPostDto;
import com.travel.project.dto.request.ReplyRequestPostDto;
import com.travel.project.dto.response.NestedReplyListDto;
import com.travel.project.dto.response.NestedReplyResponseDetailDto;
import com.travel.project.entity.NestedReply;
import com.travel.project.service.NestedReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nested/replies")
@RequiredArgsConstructor // 주입을 위해
@Slf4j
@CrossOrigin // CORS 정책 허용 범위 설정
public class NestedReplyController {

    private final NestedReplyService nestedReplyService;

    // 대댓글 목록 전체조회 요청
    @GetMapping("/{replyId}")
    public ResponseEntity<?> getNestedReply(@PathVariable long replyId) {
        NestedReplyListDto replies = nestedReplyService.getReplies(replyId);
        return ResponseEntity
                .ok()
                .body(replies);
    }

    // 대댓글 생성
    @PostMapping
    public ResponseEntity<?> createNestedReply(@RequestBody NestedReplyRequestPostDto dto) {
        nestedReplyService.registerReply(dto);
        return ResponseEntity.ok()
                .body(nestedReplyService.getReplies(dto.getReplyId()));
    }

    // 대댓글 삭제
    @DeleteMapping("/{nestedReplyId}")
    public ResponseEntity<?> deleteNestedReply(@PathVariable int nestedReplyId, long replyId) {

        NestedReplyListDto dtoList = nestedReplyService.deleteReply(nestedReplyId, replyId);

        return ResponseEntity.ok().body(dtoList);
    }

    // 대댓글 수정
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> modifyNestedReply(
            @PathVariable long nestedReplyId,
            @RequestBody NestedReplyRequestModifyDto dto) {
        // 대댓글이 수정된 후의 대댓글 목록
        NestedReplyListDto NestedReplyListDto = nestedReplyService.modifyReply(dto);
        return ResponseEntity.ok().body(NestedReplyListDto);
    }

}
