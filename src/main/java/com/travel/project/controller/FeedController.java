package com.travel.project.controller;

import com.travel.project.common.Page;
import com.travel.project.common.Search;
import com.travel.project.dto.request.FeedFindOneDto;
import com.travel.project.dto.request.FeedModifyDto;
import com.travel.project.dto.request.FeedPostDto;
import com.travel.project.dto.response.*;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.FeedMapper;
import com.travel.project.service.FeedService;
import com.travel.project.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private final FeedMapper feedMapper;

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
        FeedListDto feeds = feedService.findAll(page, session);
        feeds.setLoginUser(LoginUtil.getLoggedInUser(session));

        log.debug("FeedListDto: {}", feeds.getFeeds().get(0));

        return ResponseEntity.ok(feeds);
    }

    // 피드 상세 조회 요청
    @GetMapping("/{boardId}")
    @ResponseBody
    public ResponseEntity<?> findOne(
            @PathVariable long boardId
            , HttpSession session) {

        log.debug("컨트롤러 글번호: {}", boardId);

        FeedDetailDto foundFeed = feedService.findById(boardId);

        if(foundFeed == null) {
            return ResponseEntity.noContent().build();
        }
        String loginAccount = LoginUtil.getLoggedInUserAccount(session);
        FeedResponseDto dto = FeedResponseDto.builder()
                .loginAccount(loginAccount)
                .isAdmin(LoginUtil.isAdmin(session))
                .isMine(LoginUtil.isMine(foundFeed.getAccount(), loginAccount))
                .feed(foundFeed)
                .build();
        log.debug("디테일응답: {}", dto);
        return ResponseEntity.ok().body(dto);
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

        // tbl_board 생성된 데이터의 boardId를 가져옴 (실패 시 RuntimeException)
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
            @RequestPart("images") List<MultipartFile> images,
            HttpSession session
    ) {

        String boardAccount = feedMapper.findFeedById(boardId).getAccount();
        String userAccount = LoginUtil.getLoggedInUserAccount(session);

        // 피드 작성자 또는 관리자가 아니면 수정 불가
        if(!LoginUtil.isMine(boardAccount, userAccount) && !LoginUtil.isAdmin(session)) {
            return ResponseEntity.badRequest().body("피드 작성자가 아니면 수정할 수 없습니다.");
        }
        FeedModifyDto dto = FeedModifyDto.builder()
                .boardId(boardId)
                .account(boardAccount)
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
    public ResponseEntity<?> delete(@PathVariable long boardId, HttpSession session) {

        FeedListDto feeds = feedService.deleteFeed(boardId, session);
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
        FeedFindOneDto feedById = feedMapper.findFeedById((long) boardId);
        String boardAccount = feedById.getAccount();

        LikeDto dto = likeService.like(account, boardId, boardAccount);// 좋아요 요청 처리

        if(dto == null) {
            return ResponseEntity.status(403)
                    .body("자신이 작성한 피드에는 좋아요를 누를 수 없습니다.");
        }

        return ResponseEntity.ok().body(dto);
    }

}
