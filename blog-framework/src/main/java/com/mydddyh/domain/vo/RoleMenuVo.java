package com.mydddyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuVo {
    // 树状Menu
    private List<MenuTreeVo> menus;
    // 已经勾选的Menu
    private List<Long> checkedKeys;
}
