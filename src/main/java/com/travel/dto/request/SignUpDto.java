package com.travel.dto.request;

import com.travel.entity.Gender;
import com.travel.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
