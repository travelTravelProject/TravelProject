package com.travel.project.dto;


import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class FindIdResponseDto {

    private String id;

    public FindIdResponseDto(String id) {
        this.id = id;
    }

    // Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

