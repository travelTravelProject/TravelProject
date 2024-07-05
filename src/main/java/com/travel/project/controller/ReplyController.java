package com.travel.project.controller;


import com.travel.project.common.Page;
import com.travel.project.dto.request.ReplyRequestModifyDto;
import com.travel.project.dto.request.ReplyRequestPostDto;
import com.travel.project.dto.response.ReplyListDto;
import com.travel.project.dto.response.ReplyResponseDetailDto;
import com.travel.project.login.LoginUtil;
import com.travel.project.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController // Controller + 반환시 JSON형식으로 변환
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin // CORS 정책 허용범위 설정, 나머지는 다 차단
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 목록 전체조회 요청
    // URL : /api/v1/replies/원본글번호   -  GET -> 목록조회
    // @PathVariable : URL에 있는 변수값을 읽어줌
    @GetMapping("/{boardId}/page/{pageNo}")
    public ResponseEntity<?> list(
            @PathVariable long boardId 
            , @PathVariable int pageNo
            , HttpSession session
    ) {

        if (boardId == 0) {
            String message = "글 번호는 0번이 될 수 없습니다.";
            log.warn(message);
            return ResponseEntity
                    .badRequest()
                    .body(message);
        }

        log.info("/api/v1/replies/{} : GET", boardId);

        ReplyListDto replies = replyService.getReplies(boardId, new Page(pageNo, 10 ));
        replies.setLoginUser(LoginUtil.getLoggedInUser(session));
        return ResponseEntity
                .ok()
                .body(replies);

    }


    // 댓글 생성 요청 (요청할 때 JSON으로)
    @PostMapping
    public ResponseEntity<?> posts(@RequestBody ReplyRequestPostDto dto
        , HttpSession session
    ) {

        // 댓글 생성
        boolean flag = replyService.register(dto, session);

        if (!flag) return ResponseEntity
                .internalServerError()
                .body("댓글 등록 실패!");

        // 댓글을 생성한 이후 게시물의 댓글 목록을 불러옴
        return ResponseEntity
                .ok()
                .body(replyService.getReplies(dto.getBoardId(), new Page(1, 10)));
    }

    // 댓글 삭제 요청
    @DeleteMapping("/{replyId}")
    public ResponseEntity<?> delete(@PathVariable long replyId) {

        ReplyListDto dtoList = replyService.remove(replyId);

        return ResponseEntity
                .ok()
                .body(dtoList);
    }

    // 댓글 수정 요청
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> modify(@RequestBody ReplyRequestModifyDto dto) {

        // 댓글이 수정된 후의 댓글 목록
        ReplyListDto replyListDto = replyService.modify(dto);

        return ResponseEntity
                .ok()
                .body(replyListDto);
    }

    // 대댓글 목록 전체조회 요청
    // URL : /api/v1/nested/부모댓글번호   -  GET -> 목록조회
//    @GetMapping("/nested/{parentReplyId}")
//    public ResponseEntity<?> listNestedReplies(@PathVariable long parentReplyId) {
//        List<ReplyResponseDetailDto> replies = replyService.getNestedReplies(parentReplyId);
//        return ResponseEntity.ok().body(replies);
//    }
//
//    // 대댓글 생성
//    @PostMapping("/nested")
//    public ResponseEntity<?> postNestedReply(@RequestBody ReplyRequestPostDto dto) {
//        replyService.registerNestedReply(dto);
//        return ResponseEntity.ok().body(replyService.getNestedReplies(dto.getParentReplyId()));
//    }
//
//    // 대댓글 삭제
//    @DeleteMapping("/nested/{replyId}")
//    public ResponseEntity<?> deleteNestedReply(@PathVariable long replyId) {
//        List<ReplyResponseDetailDto> dtoList = replyService.removeNestedReply(replyId);
//        return ResponseEntity.ok().body(dtoList);
//    }
//
//    // 대댓글 수정
//    @RequestMapping(path = "/nested", method = {RequestMethod.PUT, RequestMethod.PATCH})
//    public ResponseEntity<?> modifyNestedReply(@RequestBody ReplyRequestModifyDto dto) {
//        // 댓글이 수정된 후의 댓글 목록
//        List<ReplyResponseDetailDto> replyListDto = replyService.modifyNestedReply(dto);
//        return ResponseEntity
//                .ok()
//                .body(replyListDto);
//    }


}
