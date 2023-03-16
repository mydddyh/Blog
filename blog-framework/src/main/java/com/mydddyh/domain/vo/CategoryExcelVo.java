package com.mydddyh.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryExcelVo {
    @ExcelProperty("Name 分类名")
    private String name;
    //描述
    @ExcelProperty("Description 描述")
    private String description;
    //状态0:正常,1禁用
    @ExcelProperty("Status 状态 0正常 1禁用")
    private String status;
}
