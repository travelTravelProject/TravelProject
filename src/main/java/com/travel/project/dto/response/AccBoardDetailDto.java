package com.travel.project.dto.response;

import com.travel.project.entity.AccBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
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
    private String location;
    private String imagePath;
    private int replyCount;
    private String gender; // 작성자 성별
    private LocalDate birthday; // 생년월일
    private String ageGroup; // 연령대 추가
    private String oneLiner; // 소개글
    private String profileImage; // 프로필 사진
    private String mbti; // mbti

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
        this.location = ab.getLocation();
        this.imagePath = ab.getImagePath();
        this.replyCount = ab.getReplyCount();
        this.gender = ab.getGender().name();
        this.birthday = ab.getBirthday(); // 생년월일 설정
        this.oneLiner = ab.getOneLiner();
        this.profileImage = ab.getProfileImage();
        this.mbti = ab.getMbti();



        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분 ss초");
        this.createdAt = pattern.format(ab.getCreatedAt());
        this.updatedAt = pattern.format(ab.getUpdatedAt());

        DateTimeFormatter accPattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        this.startDate = accPattern.format(ab.getStartDate());
        this.endDate = accPattern.format(ab.getEndDate());
        this.viewCount = ab.getViewCount();  // 조회수 설정

        // 연령대 계산
        if (this.birthday != null) {
            int age = Period.between(this.birthday, LocalDate.now()).getYears();
            this.ageGroup = (age / 10) * 10 + "대";
        }
    }
}
