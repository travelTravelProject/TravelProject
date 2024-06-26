package com.travel.project.dto;


import lombok.Data;

@Data
public class PasswordChangeDto {
    private String account;
    private String newPassword;
}