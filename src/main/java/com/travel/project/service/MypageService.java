package com.travel.project.service;

import com.travel.project.common.Page;
import com.travel.project.common.PageMaker;
import com.travel.project.common.Search;
import com.travel.project.dto.request.FeedFindAllDto;
import com.travel.project.dto.response.AccBoardListDto;
import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.dto.response.MyBoardListDto;
import com.travel.project.entity.BoardImage;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.MypageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {

    @Autowired
    private final MypageMapper mypageMapper;

    @Transactional
    public List<AccBoardListDto> findBoardsByAccount(HttpSession session) {
        LoginUserInfoDto loggedInUser = LoginUtil.getLoggedInUser(session);

        List<AccBoardListDto> boardList = mypageMapper.findAllByAccount(loggedInUser.getAccount());
        log.debug("findBoardsByAccount: {}", boardList);

        return boardList;
    }
}





