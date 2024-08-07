package com.travel.project.controller;

import com.travel.project.common.Page;
import com.travel.project.common.Search;
import com.travel.project.dto.response.MyFeedListDto;
import com.travel.project.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/mypage/v1")
@RequiredArgsConstructor
public class MyFeedController {

    private final FeedService feedService;

    @GetMapping("/list/{account}/{page}")
    public ResponseEntity<?> getFeeds(
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            HttpSession session) {

        Search search = new Search(new Page(pageNo, 12));

        MyFeedListDto feedsById = null;

        try {
            feedsById = feedService.findFeedsByAccount(search, session);

            return ResponseEntity.ok().body(feedsById);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
