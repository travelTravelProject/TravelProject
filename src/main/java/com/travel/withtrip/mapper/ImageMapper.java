package com.travel.withtrip.mapper;

import com.travel.withtrip.entity.BoardImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {

    // 게시글 이미지(최대 10개) 조회
    List<BoardImage> findAllImages(int boardId);

    // 이미지 추가
    boolean insertImage(BoardImage image);

    // 이미지 삭제
    boolean deleteImage(int imageId);


}
