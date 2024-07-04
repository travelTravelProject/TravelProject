package com.travel.project.controller;

import com.travel.project.service.MypageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final MypageService mypageService;

    @GetMapping
    public ResponseEntity<?> getboards(String account, HttpSession session) {



        return ResponseEntity.ok().body(account);
    }



//    @GetMapping("/list/{account}/{page}")
//    public ResponseEntity<?> getFeeds(
//            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
//            HttpSession session) {
//
//        Search search = new Search(new Page(pageNo, 12));
//
//        MyFeedListDto feedsById = feedService.findFeedsByAccount(search, session);
//
//        return ResponseEntity.ok().body(feedsById);
//    }


}
