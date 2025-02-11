package com.lf.backstage.entity.dto;

import lombok.Data;

@Data
public class UserLoginRequests {
    private String username;
    private String password;
    public String verification;
}
