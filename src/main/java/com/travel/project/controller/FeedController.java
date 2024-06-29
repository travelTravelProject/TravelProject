package com.travel.project.controller;

import com.travel.project.common.Page;
import com.travel.project.common.Search;
import com.travel.project.dto.request.FeedModifyDto;
import com.travel.project.dto.request.FeedPostDto;
import com.travel.project.dto.response.FeedDetailResponseDto;
import com.travel.project.dto.response.FeedListDto;
import com.travel.project.dto.response.LikeDto;
import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.login.LoginUtil;
import com.travel.project.service.FeedService;
import com.travel.project.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/feed/v1")
@Slf4j
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final LikeService likeService;

    // 피드 전체 조회 요청
    @GetMapping("/list") // 페이지, 검색 쿼리스트링
//    @ResponseBody
    public ResponseEntity<?> list(
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "type", defaultValue = "content", required = false) String type,
            @RequestParam(name = "keyword", defaultValue = "", required = false) String keyword
            , HttpSession session
    ) {
//        log.debug("겟: " + pageNo + "-" + type + "-" + keyword);
        Search page = new Search(new Page(pageNo, 5), type, keyword);
        page.setKeyword(keyword);
        page.setType(type);
        // Search type, keyword 확인 필요
        FeedListDto feeds = feedService.findAll(page);
        feeds.setLoginUser(LoginUtil.getLoggedInUser(session));

        log.debug("FeedListDto: {}", feeds.getFeeds().get(0));

        return ResponseEntity.ok(feeds);
    }

    // 피드 상세 조회 요청
    @GetMapping("/{boardId}")
    @ResponseBody
    public ResponseEntity<?> findOne(@PathVariable long boardId) {

        log.debug("컨트롤러 글번호: {}", boardId);

        FeedDetailResponseDto foundFeed = feedService.findById(boardId);

        if(foundFeed == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(foundFeed);
    }

    // 생성 요청
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<?> makeNewFeed(
            @RequestPart("content") String content,
            @RequestPart("account") String account,
            @RequestPart("images") List<MultipartFile> images
            , BindingResult bindingResult
            , HttpSession session
    ) {
        log.debug("POST 컨트롤러 계정: {}", account);
        LoginUserInfoDto user = (LoginUserInfoDto)session.getAttribute("user");
        log.debug("POST 세션 계정: {}", user.getAccount());
        if(!account.equals(user.getAccount())) {
            return ResponseEntity.badRequest().body("계정 불일치");
        }
        FeedPostDto dto = FeedPostDto.builder()
                .account(account)
                .content(content)
                .images(images)
                .build();

        if (bindingResult.hasErrors()) {
            // 에러 메세지 Map 필요
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        // 받아온 계정과 세션 계정 일치하는지 검증 필요
        // 일치하면 Board 작성 아니면 에러메세지 리턴

        // tbl_board 생성된 데이터의 boardId를 가져옴 (실패 시 -1 리턴)
        long newBoardId = feedService.insertFeed(dto, session);
        if(newBoardId < 0) {
            return ResponseEntity
                    .internalServerError()
                    .body("피드 등록 실패!");
        }

        return ResponseEntity
                .ok().body(feedService.findById(newBoardId));
    }

    // 수정 - 수정한 내용을 JSON으로 받도록 수정해야 함
    @RequestMapping(value="/{boardId}", method= {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> updateFeed(
            @PathVariable long boardId,
            @RequestPart("content") String content,
            @RequestPart("account") String account,
            @RequestPart("images") List<MultipartFile> images
    ) {

        FeedModifyDto dto = FeedModifyDto.builder()
                .boardId(boardId)
                .account(account)
                .content(content)
                .images(images)
                .categoryId(2)
                .build();
        log.debug("피드수정 컨트롤러 req: {}", dto);

        boolean flag = feedService.updateFeed(dto);
        if(!flag) {
            return ResponseEntity
                    .internalServerError()
                    .body("피드 등록 실패!");
        }

        return ResponseEntity
                .ok().body(feedService.findById(boardId));
    }

    // 삭제 - boardId를 받아서 status D로 변경
    @DeleteMapping("/{boardId}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable long boardId) {

        FeedListDto feeds = feedService.deleteFeed(boardId);
        log.debug("컨트롤러 피드삭제 번호: {}", boardId);
        return ResponseEntity.ok().body(feeds); // list로 리다이렉트?
    }

    // 좋아요 요청 비동기 처리
    @GetMapping("/like/{boardId}")
    @ResponseBody
    public ResponseEntity<?> like(@PathVariable int boardId, HttpSession session) throws SQLException {

        // 로그인 검증
        if(!LoginUtil.isLoggedIn(session)) {
            return ResponseEntity.status(403)
                    .body("로그인이 필요합니다.");

        }

        log.info("좋아요 async request 피드 컨트롤러!");

        String account = LoginUtil.getLoggedInUserAccount(session);

        LikeDto dto = likeService.like(account, boardId);// 좋아요 요청 처리
        return ResponseEntity.ok().body(dto);
    }

}
