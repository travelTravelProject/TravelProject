package com.travel.entity;

import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail {

    private int userDetailId; // 인덱스
    private String mbti; // mbti
    private String oneLiner; // 소개글
    @Setter
    private String profileImage; // 프로필 사진
    private int rating; // 평점

    private String account; // 계정


}
