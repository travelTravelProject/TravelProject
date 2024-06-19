package com.travel.project.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    private long boardId; // 게시글 번호
    private String account; // 게시글 작성자 계정
    private int categoryId; // 게시글 타입(동행, 피드) 번호
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private int viewCount; // 조회수

    // 동행글
    private String location; // 위치. 임시로 문자열
    private LocalDate startDate; // 동행 시작일
    private LocalDate endDate; // 동행 종료일

    private LocalDateTime createdAt; // 생성일 - 자동 타임스탬프
    private LocalDateTime updatedAt; // 수정일 - 자동 타임스탬프
//    private STATUS status; // 디폴트 A, 삭제시 D로 수정 필요,
//    ENUM 없어서 주석처리


}
