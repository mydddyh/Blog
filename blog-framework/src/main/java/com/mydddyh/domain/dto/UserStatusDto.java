package com.mydddyh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusDto {
    //主键, id->userId
    private Long userId;
    //账号状态（0正常 1停用）
    private String status;
}
