package com.travel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    // 웰컴 페이지^^
    @GetMapping("/")
    public String list() {
        return "index";
    }

    @GetMapping("/feed")
    public String feed() {
        System.out.println(System.getProperty("user.dir"));
        return "feed-list";
    }

//    @GetMapping("/feed")
//    public String feed() { return "feedList"; }

}
