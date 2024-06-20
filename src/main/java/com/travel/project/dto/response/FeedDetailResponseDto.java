package com.travel.project.dto.response;

import com.travel.project.dto.request.FeedFindAllDto;
import com.travel.project.dto.request.FeedFindOneDto;
import com.travel.project.entity.BoardImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedDetailResponseDto {
    private long boardId; // 게시글 id

    private String account; // 게시글 작성자 계정
    private String nickname; // 게시글 작성자 닉네임
    private String profileImage; // 게시글 작성자 프사

    private int categoryId; // 게시글 카테고리 id(동행 1, 피드 2) 번호
    //    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private int viewCount; // 조회수

    private LocalDateTime createdAt; // 생성일 - 타임스탬프
    private LocalDateTime updatedAt; // 수정일 - 타임스탬프

    @Setter
    private List<BoardImage> feedImageList; // 게시글에 등록된 모든 이미지
    // 댓글 수, 좋아요 수, 북마크 수 추가

    // 메서드냐 생성자냐...
    public FeedDetailResponseDto(FeedFindOneDto feedById) {
        this.boardId = feedById.getBoardId();
        this.account = feedById.getAccount();
        this.nickname = feedById.getNickname();
        this.profileImage = feedById.getProfileImage();
        this.categoryId = feedById.getCategoryId();
        this.content = feedById.getContent();
        this.viewCount = feedById.getViewCount();
        this.createdAt = feedById.getCreatedAt();
        this.updatedAt = feedById.getUpdatedAt();
    }

    public FeedDetailResponseDto(FeedFindAllDto feeds) {
        this.boardId = feeds.getBoardId();
        this.account = feeds.getAccount();
        this.nickname = feeds.getNickname();
    }
}
