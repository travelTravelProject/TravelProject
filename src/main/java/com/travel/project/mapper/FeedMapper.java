package com.travel.project.mapper;

import com.travel.project.common.Search;
import com.travel.project.dto.request.FeedFindAllDto;
import com.travel.project.dto.request.FeedFindOneDto;
import com.travel.project.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {

    // 전체 피드 조회
    List<FeedFindAllDto> findAllFeeds(Search s);

    // 피드 하나 조회
    FeedFindOneDto findFeedById(long boardId);

    // 피드 생성
    long saveFeed(Board newBoard);

    // 피드 내용 수정
    boolean modifyFeed(Board newBoard);

    // 피드 삭제 : status 'A' -> 'D' update
    boolean deleteFeed(long boardId);

    // 피드 조회수 갱신 (모달 띄우면 조회수 +1)
    boolean upViewCount(long boardId);

    // 피드 총 조회수
//    int sumViewCount(long boardId);


}
