package com.travel.project.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageMapper {

    // 내가 쓴 게시글 조회
    void findAllByAccount(String account);

}
