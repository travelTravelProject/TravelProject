package com.travel.project.entity;

import lombok.*;

@Getter @ToString @Setter
@EqualsAndHashCode
@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class UserDetail {

    private int userDetailId; // 인덱스
    private String mbti; // mbti
    private String oneLiner; // 소개글
    @Setter
    private String profileImage; // 프로필 사진
    private int rating; // 평점

    private String account; // 계정

    // 기본 생성자
//    public UserDetail() {
//        this.oneLiner = "안녕하세요.";
//        this.mbti = "MBTI를 입력해주세요.";
//        this.rating = 0;
//    }
}
