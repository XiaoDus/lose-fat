package com.lf.backstage.entity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserEdit {
    private String userId;
    private String userName;
    private String userSex;
    private String userPhone;
    private LocalDate userBirthday;
    private String avatar;
}
