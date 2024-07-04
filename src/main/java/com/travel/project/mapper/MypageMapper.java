package com.travel.project.mapper;

import com.travel.project.common.Search;
import com.travel.project.dto.response.AccBoardListDto;
import com.travel.project.entity.AccBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MypageMapper {

    // 내가 쓴 게시글 조회
//    void findAllByAccount(String account);

    List<AccBoardListDto> findAllByAccount(@Param("account") String account, @Param("search") Search search);
}
