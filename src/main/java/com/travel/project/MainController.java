package com.travel.project;

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

}
