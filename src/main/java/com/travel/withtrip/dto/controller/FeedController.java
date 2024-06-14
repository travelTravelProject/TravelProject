package com.travel.withtrip.dto.controller;

import com.travel.withtrip.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final FeedService feedService;

    // 피드 전체 목록 조회 요청
    @GetMapping("/feeds/list")
    public ResponseEntity<?> listFeeds() {

        return null;
    }

}
