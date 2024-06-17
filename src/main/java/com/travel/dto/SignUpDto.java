package com.travel.dto;

import com.travel.entity.Gender;
import com.travel.entity.User;
import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SignUpDto {

    private String account;
    private String password;
    private String name;
    private String email;
    private String nickname;
    private Date birthday;
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
