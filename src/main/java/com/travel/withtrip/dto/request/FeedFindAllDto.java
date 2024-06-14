package com.travel.withtrip.dto.request;

import com.travel.withtrip.entity.BoardImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedFindAllDto {

    @Setter
    private long boardId; // 게시글 id

    private String account; // 게시글 작성자 계정
    private int categoryId; // 게시글 카테고리 id(동행 1, 피드 2) 번호
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private int viewCount; // 조회수

    private LocalDateTime createdAt; // 생성일 - 타임스탬프
    private LocalDateTime updatedAt; // 수정일 - 타임스탬프
//    private Status status; // 디폴트 A, 삭제시 D로 수정 필요

    @Setter
    private List<BoardImage> feedImageList; // 게시글 이미지
    // 좋아요 수, 북마크 수, 댓글 수
}
