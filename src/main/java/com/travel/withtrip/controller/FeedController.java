package com.travel.withtrip.controller;

import com.travel.withtrip.dto.request.FeedFindAllDto;
import com.travel.withtrip.dto.request.FeedModifyDto;
import com.travel.withtrip.dto.request.FeedPostDto;
import com.travel.withtrip.dto.response.FeedDetailResponseDto;
import com.travel.withtrip.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/feed")
@Slf4j
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // 피드 전체 조회 요청
    @GetMapping("/list")
    public ResponseEntity<?> list() {

        List<FeedFindAllDto> feeds = feedService.findAll();

        return ResponseEntity.ok().body(feeds);
    }
    // 피드 상세 조회 요청
    @GetMapping("/{boardId}")
    public ResponseEntity<?> findById(@PathVariable long boardId) {

        FeedDetailResponseDto foundFeed = feedService.findById(boardId);

        return ResponseEntity.ok().body(foundFeed);
    }

    // 피드 생성 요청
    @PostMapping("/list")
    public ResponseEntity<?> makeNewFeed(
            @Validated @RequestPart(value="json") FeedPostDto dto
            , @RequestPart(value="file") MultipartFile file // 이미지
            , BindingResult bindingResult
            , HttpSession session
    ) {

        if (bindingResult.hasErrors()) {
            // 에러 메세지 Map 필요
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        long newBoardId = feedService.insertFeed(dto, session);
        if(newBoardId < 0) return ResponseEntity
                .internalServerError()
                .body("피드 등록 실패!");

        return ResponseEntity
                .ok()
                .body(feedService.findById(newBoardId));
    }

    // 수정 - 수정한 내용을 JSON으로 받도록 수정해야 함
    @RequestMapping(value="/list", method= {RequestMethod.PUT, RequestMethod.PATCH})
    public String updateFeed(
            @RequestPart(value="json") FeedModifyDto dto
            , @RequestPart(value="file") MultipartFile file // 이미지
    ) {

        boolean flag = feedService.updateFeed(dto);
        if(flag) return "redirect:/feed/"+ dto.getBoardId();
        else return "error";
    }



}
