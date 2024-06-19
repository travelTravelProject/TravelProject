package com.travel.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyJspController {

    // JSP 페이지로 이동하는 메서드 추가
    @GetMapping("/detail")
    public String getReplyPage() {
        return "detail"; // reply.jsp로 이동
    }

    @GetMapping("/fakeBoard")
    public String fakeBoard() {
        return "fakeBoard";
    }

    @GetMapping("/reply")
    public String reply() {
        return "reply";
    }
}
