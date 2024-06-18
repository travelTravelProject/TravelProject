package com.travel.dto.response;

import com.travel.entity.AccBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
public class AccBoardDetailDto {

    private int boardId;
    private String writer;
    private String title;
    private String content;
    private String regDateTime;

    @Setter
    private int likeCount; // 총 좋아요 수
    @Setter
    private String userReaction; // 현재 리액션 상태

    public AccBoardDetailDto(AccBoard ab) {
        this.boardId = ab.getBoardId();
        this.title = ab.getTitle();
        this.writer = ab.getWriter();
        this.content = ab.getContent();

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분 ss초");
        this.regDateTime = pattern.format(ab.getCreatedAt());
    }
}
