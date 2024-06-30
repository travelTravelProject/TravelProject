package com.travel.project.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkDto {

    // 북마크 처리를 위해 클라이언트에 보낼 JSON
    private int bookmarkCount; // 북마크 수
    private boolean userBookmark; // 북마크 상태
}
