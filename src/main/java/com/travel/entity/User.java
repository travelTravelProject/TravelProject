package com.travel.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String account; // 계정

    @Setter
    private String password; // 비밀번호
    private String name; // 이름
    private String email; // 이메일
    private String nickname; // 닉네임
    private String birthday; // 생년월일
    private Gender gender; // 성별
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일
    private STATUS status; // 상태
    private Auth auth; // 권한

}
