package com.travel.project.dto.response;

import lombok.*;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedResponseDto {

    private String loginAccount;
    private boolean isMine; // js에 mine, admin 으로 전달됨
    private boolean isAdmin;
    private FeedDetailDto feed;

}
