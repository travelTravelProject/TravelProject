package com.travel.project.mapper;

import com.travel.project.common.Search;
import com.travel.project.entity.AccBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccBoardMapper {

    // 게시물 목록 조회
    List<AccBoard> findAll(Search page);

    // 게시물 상세 조회
    AccBoard findOne(int boardId);

    // 게시물 등록
    boolean save(AccBoard accBoard);

    // 게시물 삭제
    boolean delete(int boardId);

    // 게시물 수정
    boolean modify(AccBoard accBoard);

    // 조회수 상승
    void upViewCount(int boardId);

    // 총 게시물 수 조회
    int count(Search search);
}
