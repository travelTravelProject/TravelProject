package com.travel.project.dto.response;

import lombok.*;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedResponseDto {

    private String loginAccount;
    private boolean isMine;
    private boolean isAdmin;
    private FeedDetailDto feed;

}
