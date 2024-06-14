package com.travel.withtrip.service;

import com.travel.withtrip.dto.request.FeedFindAllDto;
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
}
