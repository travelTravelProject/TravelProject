package com.travel.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/feed")
public class FeedJspController {

    @GetMapping("/feed/list")
    public String list() {
        return "feed-list";
    }
}
