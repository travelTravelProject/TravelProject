package com.travel.project.controller;


import com.travel.project.dto.request.ReplyRequestModifyDto;
import com.travel.project.dto.request.ReplyRequestPostDto;
import com.travel.project.dto.response.ReplyResponseDetailDto;
import com.travel.project.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Controller + 반환시 JSON형식으로 변환
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 목록 전체조회 요청
    // URL : /api/v1/replies/원본글번호   -  GET -> 목록조회
    // @PathVariable : URL에 있는 변수값을 읽어줌
    @GetMapping("/{boardId}")
    public ResponseEntity<?> list(@PathVariable long boardId) {

        List<ReplyResponseDetailDto> replies = replyService.getReplies(boardId);

        return ResponseEntity
                .ok()
                .body(replies);
    }

    // 댓글 생성 요청 (요청할 때 JSON으로)
    @PostMapping
    public ResponseEntity<?> posts(@RequestBody ReplyRequestPostDto dto) {

        // 댓글 생성
        replyService.register(dto);

        // 댓글을 생성한 이후 게시물의 댓글 목록을 불러옴
        return ResponseEntity
                .ok()
                .body(replyService.getReplies(dto.getBoardId()));
    }

    // 댓글 삭제 요청
    @DeleteMapping("/{replyId}")
    public ResponseEntity<?> delete(@PathVariable long replyId) {

        List<ReplyResponseDetailDto> dtoList = replyService.remove(replyId);

        return ResponseEntity
                .ok()
                .body(dtoList);
    }

    // 댓글 수정 요청
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> modify(@RequestBody ReplyRequestModifyDto dto) {

        // 댓글이 수정된 후의 댓글 목록
        List<ReplyResponseDetailDto> replyListDto = replyService.modify(dto);

        return ResponseEntity
                .ok()
                .body(replyListDto);
    }
}
