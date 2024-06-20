package com.travel.project.dto.response;

import com.travel.project.entity.AccBoard;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 화면에 필요한 데이터만 모아놓은 클래스
@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class AccBoardListDto {

    private int boardId; // 게시글 번호
    private String shortTitle; // 줄임 처리된 제목
    private String shortContent; // 줄임 처리된 글 내용
    private String date; // 포맷팅된 날짜 문자열
    private int view; // 조회 수
//    private boolean hit; // 인기 게시물인지
//    private boolean newArticle; // 새 게시물인지 (1시간 이내)
//    private int replyCount; // 댓글 수
//    private String account;


    // 엔터티를 DTO로 변환
    public AccBoardListDto (AccBoard ab) {
        this.boardId = ab.getBoardId();
        this.shortTitle = makeShortTitle(ab.getTitle());
        this.shortContent = makeShortContnet(ab.getContent());

        // 게시물 등록시간
//        LocalDateTime regTime = ab.getCreatedAt();
        this.date = dateFormatting(ab.getCreatedAt());

//        String s = dateFormatting(ab.getCreatedAt());
//        System.out.println("s = " + s);
//        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.view = ab.getViewCount();
//        this.hit = this.view > 10;
//        this.newArticle = ab.getCreatedAt().isBefore(regTime.plusMinutes(60));
//        this.replyCount = ab.getReplyCount();
//        this.account = ab.getAccount();
    }

    private String dateFormatting(LocalDateTime regDateTime) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return pattern.format(regDateTime);
    }

    private String makeShortContnet(String content) {
        return content.length() > 30 ? content.substring(0, 30) + "..." : content;
    }

    private String makeShortTitle(String title) {
        return title.length() > 10 ? title.substring(0, 10) + "..." : title;
    }
}
