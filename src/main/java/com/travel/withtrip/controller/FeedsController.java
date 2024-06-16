package com.travel.withtrip.controller;

import com.travel.withtrip.dto.request.FeedFindAllDto;
import com.travel.withtrip.dto.response.FeedDetailResponseDto;
import com.travel.withtrip.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@Slf4j
@RequiredArgsConstructor
public class FeedsController {

    private final FeedService feedService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {

        List<FeedFindAllDto> feeds = feedService.findAll();

        return ResponseEntity.ok().body(feeds);
    }
    @GetMapping("/{boardId}")
    public ResponseEntity<?> findById(@PathVariable long boardId) {
        FeedDetailResponseDto foundFeed = feedService.findById(boardId);

        return ResponseEntity.ok().body(foundFeed);
    }

    // list post mapping 으로 게시글 작성?
    // 작성결과 받아오는 DTO로 변경 필요
    @PostMapping("/list")
    public ResponseEntity<?> makeNewFeed(@RequestBody FeedFindAllDto feed) {
        boolean b = feedService.insertFeed(feed);
        return null;
    }

    // 수정 - 수정한 내용을 JSON으로 받도록 수정해야 함
    @RequestMapping(value="/list/{boardId}", method= {RequestMethod.PUT, RequestMethod.PATCH})
    public String updateFeed(@PathVariable long boardId) {

        boolean flag = feedService.updateFeed(boardId);
        if(flag) return "redirect:/list";
        else return "error";
    }



}
