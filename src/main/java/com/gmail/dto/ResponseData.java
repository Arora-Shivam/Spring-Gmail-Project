package com.gmail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseData {

    private String FileName;
    private String downloadUrl;
    private String FileType;
    private Long FileSize;


}
