package com.mydddyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {
    //菜单ID
    private Long id;
    //父菜单ID
    private Long parentId;
    //原menuName字段
    private String label;
    //新增字段
    private List<MenuTreeVo> children;
}
