package com.travel.project.dto.response;

import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {

    // 좋아요 처리를 위해 클라이언트에 보낼 JSON
    private int likeCount; // 갱신된 총 좋아요 수
    private boolean userLike; // 현재 리액션 상태 (좋아요 눌렀는지 아닌지)
}
