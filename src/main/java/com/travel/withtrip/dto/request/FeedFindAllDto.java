package com.travel.withtrip.dto.request;

import com.travel.withtrip.entity.BoardImage;
import com.travel.withtrip.entity.Status;

import java.time.LocalDateTime;
import java.util.List;

public class FeedFindAllDto {

    private long boardId; // 게시글 번호
    private String account; // 게시글 작성자 계정
    private int categoryId; // 게시글 타입(동행, 피드) 번호
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private int viewCount; // 조회수

    private LocalDateTime createdAt; // 생성일 - 자동 타임스탬프
    private LocalDateTime updatedAt; // 수정일 - 자동 타임스탬프
    private Status status; // 디폴트 A, 삭제시 D로 수정 필요

    private List<BoardImage> feedImageList; // 게시글 이미지
}
