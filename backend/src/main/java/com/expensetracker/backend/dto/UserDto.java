package com.expensetracker.backend.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
}