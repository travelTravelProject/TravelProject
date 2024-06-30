package com.travel.project.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccBoard {

    private long boardId; // 글번호
    private String account; // 계정명
    private int categoryId; // 게시글 타입 번호
    private String title; // 제목
    private String content; // 내용
    private int viewCount; // 조회수
    private LocalDateTime createdAt; // 게시글 작성 시간
    private LocalDateTime updatedAt; // 게시글 수정 시간
    private STATUS status; // 상태
    private String location; // 여행지
    private LocalDateTime startDate; // 동행 시작일
    private LocalDateTime endDate; // 동행 종료일
    private String writer; // 작성자
    private Gender gender; // 성별
}
