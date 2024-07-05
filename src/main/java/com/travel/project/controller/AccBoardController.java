package com.travel.project.controller;

import com.travel.project.common.Page;
import com.travel.project.common.PageMaker;
import com.travel.project.common.Search;
import com.travel.project.dto.request.AccBoardWriteDto;
import com.travel.project.dto.response.*;
import com.travel.project.entity.AccBoard;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.AccBoardMapper;
import com.travel.project.mapper.BookmarkMapper;
import com.travel.project.service.AccBoardImageService;
import com.travel.project.service.AccBoardService;
import com.travel.project.service.BookmarkService;
import com.travel.project.service.LikeService;
import com.travel.project.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/acc-board/*")
@RequiredArgsConstructor
@Slf4j
public class AccBoardController {

    private final AccBoardService boardService;
    private final AccBoardMapper boardMapper;
    private final BookmarkService bookmarkService;
    private final AccBoardImageService boardImageService;


    // 1. 목록 조회 요청 (/list : GET)
    @GetMapping("/list")
    public String list(@ModelAttribute("s") Search page, Model model, HttpSession session) {
        System.out.println("/acc-board/list GET");
        System.out.println(session.getAttribute("user"));
        // 목록 조회 요청 위임
        List<AccBoardListDto> abList = boardService.findList(page);

        // 페이지 정보
        PageMaker maker = new PageMaker(page, boardService.getCount(page));

        // JSP파일에 해당 목록 데이터를 전송
        model.addAttribute("abList", abList);
        model.addAttribute("maker", maker);

        return "acc-board/list";
    }

    // 2. 게시글 쓰기 양식 화면 열기 요청 (/write : GET)
    @GetMapping("/write")
    public String write(HttpSession session) {
        System.out.println("/acc-board/acc-write GET");
        // 로그인 확인
        if (!LoginUtil.isLoggedIn(session)) {
            return "redirect:/sign-in";
        }

        return "acc-board/acc-write";
    }

    // 3. 게시글 등록 요청 (/write : POST) > 목록조회 요청 리다이렉션
    @PostMapping("/write")
    public String write(AccBoardWriteDto dto, HttpSession session) {
        System.out.println("/acc-board/acc-write POST");

        // 로그인 확인
        if (!LoginUtil.isLoggedIn(session)) {
            return "redirect:/sign-in"; // 로그인 페이지로 리다이렉트
        }

        // 저장될 boardId =  전체 게시글수 + 1
        long boardId = boardService.getTotalCount() + 1;

        // 게시글 저장
        boardService.insert(dto, session);

        // 서버 업로드 후 업로드 경로 반환
        String imagePath = FileUtil.uploadFile(dto.getPostImage());

        // 이미지 정보 저장
        if (imagePath != null) {
            boardImageService.saveBoardImage(boardId, imagePath);
        }

        // boardService.insert가 정상적으로 동작한다면, boardImageService로 이미지 등록 요청

        return "redirect:/acc-board/list";
    }

    // 4. 게시글 삭제 요청 (/delete : GET) > 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(long boardId) {
        System.out.println("/acc-board/acc-delete GET");

        boardService.remove(boardId);

        return "redirect:/acc-board/list";
    }

    // 5. 게시글 상세 조회 요청 (/detail : GET)
    @GetMapping("/detail")
    public String detail(@RequestParam("bno") long boardId, Model model, HttpServletRequest req) {
        System.out.println("/acc-board/acc-detail GET");
        // 글번호 조회
        AccBoardDetailDto dto = boardService.detail(boardId);
        // JSP 에 전송
        model.addAttribute("abd", dto);
        boolean flag = boardService.checkBookmark(req.getSession(), boardId);
        model.addAttribute("bookmark", flag);

        // String 타입의 날짜를 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate startDate = LocalDate.parse(dto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(dto.getEndDate(), formatter);
        // 날짜 차이 계산
        long period = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        model.addAttribute("period", period);

        // 사용자 정보 가져오기
        HttpSession session = req.getSession();
        LoginUserInfoDto userInfo = (LoginUserInfoDto) session.getAttribute("user"); // 세션에서 LoginUserInfoDto 객체를 가져옴
        String account = userInfo != null ? userInfo.getAccount() : null; // 사용자 계정 정보 추출
        String boardAccount = dto.getAccount(); // 게시글 작성자 계정 정보 추출

        // 현재 사용자가 글의 작성자이거나 관리자인지 확인
        model.addAttribute("isOwnerOrAdmin", account != null && (account.equals(boardAccount) || LoginUtil.isAdmin(session)));

        // 이전 페이지 주소
        String ref = req.getHeader("referer");

        // 최근 'acc-board/list' URL을 찾기 위한 조건문
        if (ref != null && !ref.contains("acc-board/list")) {
            // 세션이 존재하는지 확인
            HttpSession sessionCheck = req.getSession(false);
            if (sessionCheck != null) {
                // 세션에서 이전 referer 값을 가져옴
                String prevRef = (String) sessionCheck.getAttribute("prevReferer");

                // 이전 referer가 존재하고, 'acc-board/list'를 포함하는지 확인
                if (prevRef != null && prevRef.contains("acc-board/list")) {
                    // 조건을 만족하면 이전 referer를 현재 referer로 설정
                    ref = prevRef;
                } else {
                    // 조건을 만족하지 않으면 기본 referer를 '/acc-board/list'로 설정
                    ref = "/acc-board/list";
                }
            } else {
                // 세션이 없으면 기본 referer를 '/acc-board/list'로 설정
                ref = "/acc-board/list";
            }
        } else if (ref != null) {
            // 현재 referer가 'acc-board/list'를 포함하면, 세션에 저장
            HttpSession sessionSave = req.getSession();
            sessionSave.setAttribute("prevReferer", ref);
        }


        model.addAttribute("ref", ref);

        return "acc-board/acc-detail";
    }

    // 6. 수정 화면 요청 (/modify : GET)
    @GetMapping("/modify")
    public String modify(@RequestParam("bno") Integer boardId, Model model) {
        System.out.println("/acc-board/modify GET");

        // 글번호 조회
        AccBoardModifyDto dto = boardService.getModifyForm(boardId);
        // JSP 에 전송
        model.addAttribute("abm", dto);

        return "acc-board/acc-modify";
    }

    // 7. 게시글 수정 요청 (/modify : POST)
    @PostMapping("/modify")
    public String modify(AccBoardModifyDto dto) {
        System.out.println("/acc-board/modify POST");

        boardService.modify(dto);

        return "redirect:/acc-board/detail?bno=" + dto.getBoardId();
    }

    // 북마크 요청 비동기 처리
    @GetMapping("/bookmark")
    @ResponseBody
    public ResponseEntity<?> bookmark(@RequestParam("boardId") long boardId, HttpSession session) throws SQLException {
        // 로그인 검증
        if (!LoginUtil.isLoggedIn(session)) {
            return ResponseEntity.status(403).body("로그인이 필요합니다.");
        }

        String account = LoginUtil.getLoggedInUserAccount(session);
        AccBoard findAccountByBoardId = boardMapper.findOne(boardId);
        String boardAccount = findAccountByBoardId.getAccount();

        BookmarkDto dto = bookmarkService.bookmark(account, boardId, boardAccount);

        if (dto == null) {
            return ResponseEntity.status(400).body("자신의 글은 북마크할 수 없습니다.");
        }

        return ResponseEntity.ok().body(dto);
    }

    // 북마크 상태를 가져오는 메서드
    @GetMapping("/bookmark/status")
    @ResponseBody
    public ResponseEntity<?> getBookmarkStatus(@RequestParam("boardId") long boardId, HttpSession session) {

        String account = LoginUtil.getLoggedInUserAccount(session);
        boolean isBookmarked = bookmarkService.isBookmarkByUser(account, boardId);

        return ResponseEntity.ok().body(isBookmarked);
    }
}
