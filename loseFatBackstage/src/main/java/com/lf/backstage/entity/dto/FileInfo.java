package com.lf.backstage.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileInfo {
    private String name;
    private String path;
    private long size;
    private boolean isDirectory;

}
