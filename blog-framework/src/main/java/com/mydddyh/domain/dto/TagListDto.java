package com.mydddyh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {
    //标签名
    private String name;
    //备注
    private String remark;
}
