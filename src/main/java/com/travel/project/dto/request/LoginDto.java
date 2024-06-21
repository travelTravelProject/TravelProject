package com.travel.project.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LoginDto {

    private String account; // 계정
    private String password; // 비밀번호
    private boolean autoLogin; //자동로그인 체쿠 여부
}
