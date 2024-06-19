package com.travel.project.dto.request;

import com.travel.project.entity.Gender;
import com.travel.project.entity.User;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SignUpDto {

    @NotBlank
    @Size(min = 4, max = 14, message = "아이디는 4~14글자")
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 2, max = 6)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nickname;

    @NotNull
    @Past(message = "생년월일은 과거 날짜여야 합니다")
    private LocalDate birthday;

    @NotBlank
    private Gender gender;

    public User toEntity() {
        return User.builder()
                .account(this.account)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .nickname(this.nickname)
                .birthday(this.birthday)
                .gender(this.gender)
                .build();
    }

}
