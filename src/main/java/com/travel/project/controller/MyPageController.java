package com.travel.project.controller;

import com.travel.project.common.Search;
import com.travel.project.dto.response.AccBoardListDto;
import com.travel.project.dto.response.MyBoardListDto;
import com.travel.project.service.MypageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

   private final MypageService mypageService;

    @GetMapping("/mypage/boards/{account}")
    public ResponseEntity<List<AccBoardListDto>> getboards(@PathVariable String account, HttpSession session) {

        List<AccBoardListDto> boardsByAccount = mypageService.findBoardsByAccount(session);
//        Search search = new Search();
//        search.setAccount(account);
//        MyBoardListDto myBoardList = mypageService.findBoardsByAccount(session);
//        List<AccBoardListDto> boardList = myBoardListDto.getBoards();

        log.debug("boardsByAccount: {}", boardsByAccount);

        return ResponseEntity.ok().body(boardsByAccount);
    }



}
