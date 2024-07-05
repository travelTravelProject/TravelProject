package com.travel.project.dto.response;

import com.travel.project.entity.BoardImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedDetailDto {
    private long boardId; // 게시글 id

    private String account; // 게시글 작성자 계정
    private String nickname; // 게시글 작성자 닉네임
    private String profileImage; // 게시글 작성자 프사

    private int categoryId; // 게시글 카테고리 id(동행 1, 피드 2) 번호
    //    private String title; // 게시글 제목

    @Setter
    private String content; // 게시글 내용
    private int viewCount; // 조회수

    private LocalDateTime createdAt; // 생성일 - 타임스탬프
    private LocalDateTime updatedAt; // 수정일 - 타임스탬프

    @Setter
    private List<BoardImage> feedImageList; // 게시글에 등록된 모든 이미지
    // 댓글 수, 좋아요 수, 북마크 수 추가
    @Setter
    private int replyCount; // 게시글 총 댓글 수
    @Setter
    private int likeCount; // 게시글 총 좋아요 수
    @Setter
    private int bookmarkCount; // 게시글 총 북마크 수

    @Setter
    private boolean userLike; // 현재 좋아요 상태
    @Setter
    private boolean userBookmark; // 현재 북마크 상태


}
