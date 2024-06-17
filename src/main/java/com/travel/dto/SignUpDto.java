package com.travel.dto;

import com.travel.entity.Gender;
import com.travel.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SignUpDto {

    @NotBlank
    private String account;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank
    private String birthday;
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
