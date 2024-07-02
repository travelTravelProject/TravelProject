package com.travel.project.dto.response;

import com.travel.project.entity.AccBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
public class AccBoardDetailDto {

    private long boardId;
    private String writer;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String startDate;
    private String endDate;
    private int viewCount;
    private String account;

    @Setter
    private int likeCount; // 총 좋아요 수
    @Setter
    private String userReaction; // 현재 리액션 상태

    public AccBoardDetailDto(AccBoard ab) {
        this.boardId = ab.getBoardId();
        this.title = ab.getTitle();
        this.writer = ab.getWriter();
        this.content = ab.getContent();
        this.account = ab.getAccount();

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분 ss초");
        this.createdAt = pattern.format(ab.getCreatedAt());
        this.updatedAt = pattern.format(ab.getUpdatedAt());

        DateTimeFormatter accPattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        this.startDate = accPattern.format(ab.getStartDate());
        this.endDate = accPattern.format(ab.getEndDate());
        this.viewCount = ab.getViewCount();  // 조회수 설정
    }
}
