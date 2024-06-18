package com.travel.controller;

import com.travel.dto.request.AccBoardWriteDto;
import com.travel.dto.response.AccBoardDetailDto;
import com.travel.dto.response.AccBoardListDto;
import com.travel.service.AccBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String detail(int boardId, Model model) {
        System.out.println("boardId: " + boardId);

        // 데이터베이스로부터 글번호 데이터 조회
        AccBoardDetailDto dto = service.detail(boardId);

        // JSP파일에 조회한 데이터 보내기
        model.addAttribute("ab", dto);

        return "acc-board/acc-detail";
    }

}
