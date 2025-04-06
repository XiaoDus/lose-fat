package com.lf.losefat.controller.dto;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class SmsLogin {
    private String phone;
    private Integer code;
}
