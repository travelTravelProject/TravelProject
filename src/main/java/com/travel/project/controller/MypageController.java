package com.travel.project.controller;

import com.travel.project.common.Page;
import com.travel.project.common.Search;
import com.travel.project.dto.response.MyFeedListDto;
import com.travel.project.login.LoginUtil;
import com.travel.project.service.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/mypage/v1")
public class MypageController {

    private final FeedService feedService;

    public MypageController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/list/{account}/{page}")
    public ResponseEntity<?> getFeeds(
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            HttpSession session) {

        Search search = new Search(new Page(pageNo, 12));

        MyFeedListDto feedsById = feedService.findFeedsById(search, session);

        return ResponseEntity.ok().body(feedsById);
    }

}
