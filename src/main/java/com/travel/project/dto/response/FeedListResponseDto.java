package com.travel.project.dto.response;

import com.travel.project.dto.request.FeedFindAllDto;
import com.travel.project.entity.BoardImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedListResponseDto {

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

    public FeedListResponseDto(FeedFindAllDto feedList) {
        this.account = feedList.getAccount();
        this.nickname = feedList.getNickname();
        this.profileImage = feedList.getProfileImage();
        this.categoryId = feedList.getCategoryId();
        this.content = feedList.getContent();
        this.viewCount = feedList.getViewCount();
        this.createdAt = feedList.getCreatedAt();
        this.updatedAt = feedList.getUpdatedAt();
//        this.feedImageList = new ArrayList<>(); // imageService 조회 결과로 setter

    }
}
