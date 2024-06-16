package com.travel.withtrip.service;

import com.travel.withtrip.dto.request.FeedFindAllDto;
import com.travel.withtrip.dto.response.FeedDetailResponseDto;
import com.travel.withtrip.entity.Board;
import com.travel.withtrip.mapper.FeedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedMapper feedMapper;

    public List<FeedFindAllDto> findAll() {
        List<FeedFindAllDto> feedList = feedMapper.findAllFeeds();

        // 각 피드마다 이미지 리스트를 추가
        // 각 피드마다 댓글 수, 좋아요 수, 북마크 수 추가

        return feedList;
    }

    public FeedDetailResponseDto findById(long boardId) {
        // board 받아와서 response dto에 담아주기...
        // mapper.xml 타입도 주의
        Board feedById = feedMapper.findFeedById(boardId);


        return new FeedDetailResponseDto(feedById);

    }

    public boolean insertFeed(FeedFindAllDto newFeed) {
        int newBoardId = feedMapper.saveFeed(new Board());

        // 넘버포맷익셉션 예외 처리 필요
        if (newBoardId > 0) {
            // tbl_board_image에 이미지 순서대로 추가
        }

        return true;
    }

    public boolean updateFeed(long boardId) {
        return feedMapper.modifyFeed(boardId);
    }

    public boolean deleteFeed(long boardId) {
        return feedMapper.deleteFeed(boardId);
    }
    public boolean addViewCount(long boardId) {
        return feedMapper.upViewCount(boardId);
    }

}
