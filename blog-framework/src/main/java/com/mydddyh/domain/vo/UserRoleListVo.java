package com.mydddyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleListVo {
    // 用户信息
    private UserAdminInfoVo user;
    // 所有可用角色
    private List<RoleListVo> roles;
    // 当前用户对应角色
    private List<Long> roleIds;
}
