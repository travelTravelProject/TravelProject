package com.travel.project.controller;

import com.travel.project.dto.request.AccBoardWriteDto;
import com.travel.project.dto.response.AccBoardDetailDto;
import com.travel.project.dto.response.AccBoardListDto;
import com.travel.project.dto.response.AccBoardModifyDto;
import com.travel.project.service.AccBoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/acc-board/*")
@RequiredArgsConstructor
public class AccBoardController {

    private static final Logger log = LoggerFactory.getLogger(AccBoardController.class);
    private final AccBoardService service;

    // 1. 목록 조회 요청 (/list : GET)
    @GetMapping("/list")
    public String list(Model model) {
        System.out.println("/acc-board/acc-list GET");

        // 목록 조회 요청 위임
        List<AccBoardListDto> abList = service.findList();

        // JSP파일에 해당 목록 데이터를 전송
        model.addAttribute("abList", abList);

        return "acc-board/acc-list";
    }

    // 2. 게시글 쓰기 양식 화면 열기 요청 (/write : GET)
    @GetMapping("/write")
    public String write() {
        System.out.println("/acc-board/acc-write GET");

        return "acc-board/acc-write";
    }

    // 3. 게시글 등록 요청 (/write : POST) > 목록조회 요청 리다이렉션
    @PostMapping("/write")
    public String write(AccBoardWriteDto dto) {
        System.out.println("/acc-board/acc-write POST");

        // 1. 브라우저가 전달한 게시글 내용 읽기
        System.out.println("dto: " + dto);

        service.insert(dto);

        return "redirect:/acc-board/list";
    }

    // 4. 게시글 삭제 요청 (/delete : GET) > 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(int boardId) {
        System.out.println("/acc-board/acc-delete GET");

        service.remove(boardId);

        return "redirect:/acc-board/list";
    }

    // 5. 게시글 상세 조회 요청 (/detail : GET)
    @GetMapping("/detail")
    public String detail(@RequestParam("bno") Integer boardId, Model model, HttpServletRequest req) {
        System.out.println("/acc-board/acc-detail GET");
        // 글번호 조회
        AccBoardDetailDto dto = service.detail(boardId);
        // JSP 에 전송
        model.addAttribute("abd", dto);

        // 이전 페이지 주소
        String ref = req.getHeader("referer");
        model.addAttribute("ref", ref);

        return "acc-board/acc-detail";
    }

    // 6. 수정 화면 요청 (/modify : GET)
    @GetMapping("/modify")
    public String modify(@RequestParam("bno") Integer boardId, Model model) {
        System.out.println("/acc-board/modify GET");

        // 글번호 조회
        AccBoardModifyDto dto = service.getModifyForm(boardId);
        // JSP 에 전송
        model.addAttribute("abm", dto);

        return "acc-board/acc-modify";
    }

    // 7. 게시글 수정 요청 (/modify : POST)
    @PostMapping("/modify")
    public String modify(AccBoardModifyDto dto) {
        System.out.println("/acc-board/modify POST");

        service.modify(dto);

        return "redirect:/acc-board/detail?bno=" + dto.getBoardId();
    }

}
