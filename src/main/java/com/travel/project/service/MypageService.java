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
    public MyBoardListDto findBoardsByAccount(Search search, HttpSession session) {
        LoginUserInfoDto loginUser = LoginUtil.getLoggedInUser(session);
        List<AccBoardListDto> boards = mypageMapper.findAllByAccount(loginUser.getAccount(), search);

        mypageMapper.findAllByAccount(loggedInUser.getAccount());

        return new MyBoardListDto(boards.stream()
                .map(board -> new AccBoardListDto(board))
                .collect(Collectors.toList()));

//        List<MyFeedDto> myFeeds = feedList.stream().map(f -> {
//
//            MyFeedDto myFeed = f.toMyFeed();
//            long boardId = f.getBoardId();
//
//            // 이미지 추가 (1개)
//            BoardImage firstOne = imageService.findFirstOne(boardId);
//            if(firstOne == null) throw new RuntimeException("이미지가 존재하지 않습니다.");
//            myFeed.setImage(firstOne);
//
//            // 좋아요 수 추가
//            myFeed.setLikeCount(likeService.countLikes((int)boardId));
//
//            // 북마크 수 추가
//            myFeed.setBookmarkCount(bookmarkService.countBookmarks((int)boardId));
//
//            // 댓글 수 추가 (확인 필요)
//
//            return myFeed;
//        }).collect(Collectors.toList());
//
//        PageMaker pageMaker = new PageMaker(new Page(search.getPageNo(), search.getAmount()), myFeeds.size());
//
//        return MyFeedListDto.builder()
//                .loginUser(loggedInUser)
//                .pageInfo(pageMaker)
//                .myFeeds(myFeeds)
//                .build();
    }
}




