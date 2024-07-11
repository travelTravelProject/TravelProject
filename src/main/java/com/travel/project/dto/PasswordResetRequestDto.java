package com.travel.project.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
    private String account;
    private String name;
    private String email;
}