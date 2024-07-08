package com.travel.project.dto.response;

import com.travel.project.entity.AccBoard;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

// 화면에 필요한 데이터만 모아놓은 클래스
@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class AccBoardListDto {

    private long boardId; // 게시글 번호
    private String shortTitle; // 줄임 처리된 제목
    private String shortContent; // 줄임 처리된 글 내용
    private String date; // 포맷팅된 날짜 문자열
    private int view; // 조회 수
    private String account; // 작성자 계정
    private String startDate; // 동행 시작일
    private String endDate; // 동행 종료일
    private String writer; // 닉네임
    private String gender; // 작성자 성별
    private String imagePath; // 이미지 경로 추가
    private int replyCount; // 총 댓글 수
    private LocalDate birthday; // 생년월일
    private String ageGroup; // 연령대 추가

    // 엔터티를 DTO로 변환
    public AccBoardListDto (AccBoard ab) {
        this.boardId = ab.getBoardId();
        this.shortTitle = makeShortTitle(ab.getTitle());
        this.shortContent = makeShortContent(ab.getContent());
        this.date = dateFormatting(ab.getCreatedAt());
        this.view = ab.getViewCount();
        this.account = ab.getAccount();
        this.startDate = dateFormatting(ab.getStartDate());
        this.endDate = dateFormatting(ab.getEndDate());
        this.writer = ab.getWriter();
        this.gender = ab.getGender().name();
        this.imagePath = ab.getImagePath(); // 이미지 경로 설정
        this.replyCount = ab.getReplyCount();
        this.birthday = ab.getBirthday(); // 생년월일 설정

        // 연령대 계산
        if (this.birthday != null) {
            int age = Period.between(this.birthday, LocalDate.now()).getYears();
            this.ageGroup = (age / 10) * 10 + "대";
        }

    }

    private String dateFormatting(LocalDateTime regDateTime) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return pattern.format(regDateTime);
    }

    private String makeShortContent(String content) {
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    private String makeShortTitle(String title) {
        return title.length() > 20 ? title.substring(0, 20) + "…" : title;
    }

}
