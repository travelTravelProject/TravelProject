package com.travel.project.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {

    private int bookmarkId; // 북마크 ID
    private String account; // 사용자 계정
    private long boardId; // 게시글 ID
    private LocalDateTime createdAt; // 생성 시간
}
