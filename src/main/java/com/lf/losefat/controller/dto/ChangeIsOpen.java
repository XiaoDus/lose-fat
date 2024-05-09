package com.lf.losefat.controller.dto;

import lombok.Data;

@Data
public class ChangeIsOpen {
    private String uid;
    private String listName;
    private int isOpen;
}
