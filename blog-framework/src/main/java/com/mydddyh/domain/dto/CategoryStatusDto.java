package com.mydddyh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryStatusDto {
    private Long id;
    //状态0:正常,1禁用
    private String status;
}
