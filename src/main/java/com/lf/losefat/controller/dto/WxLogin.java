package com.lf.losefat.controller.dto;

import lombok.Data;

/**
 * @author xiaodu
 */
@Data
public class WxLogin {
    private String js_code;
    private String name;
    private String avatar;
    private String iv;
    private String encryptedData;
}
