package com.mydddyh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleStatusDto {
    //角色ID
    private Long roleId;
    //角色状态（0正常 1停用）
    private String status;
}
