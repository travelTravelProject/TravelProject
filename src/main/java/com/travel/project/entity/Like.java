package com.travel.project.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    private int likeId;
    private String account;
    private int boardId;
    private LocalDateTime createdAt;
}
