package com.travel.project.mapper;

import com.travel.project.entity.Bookmark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookmarkMapper {

    // bookmark 생성
    void insertBookmark(Bookmark bookmark);

    // bookmark 삭제
    void deleteBookmark(@Param("account") String account, @Param("boardId") long boardId);

    // 특정 게시물의 bookmark count 조회
    int countBookmarks(@Param("boardId") long boardId);

    // 사용자가 게시물에 bookmark 했는지 확인
    Bookmark existsBookmark(@Param("account") String account, @Param("boardId") long boardId);

}
