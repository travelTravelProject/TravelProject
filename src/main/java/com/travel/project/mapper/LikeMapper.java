package com.travel.project.mapper;

import com.travel.project.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

    // like 생성
    void insertLike(Like like);

    // like 삭제
    void deleteLike(@Param("account") String account, @Param("boardId") int boardId);

    // 특정 게시물의 like count 조회
    int countLikes(@Param("boardId") int boardId);

    // 사용자가 게시물에 like 했는지 확인
    Like existsLike(@Param("account") String account, @Param("boardId") int boardId);

}
