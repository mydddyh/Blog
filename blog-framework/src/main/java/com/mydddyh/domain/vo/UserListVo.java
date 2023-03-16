package com.mydddyh.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListVo {
    //主键
    private Long id;
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //账号状态（0正常 1停用）
    private String status;
    //手机号
    private String phoneNumber;
    //创建时间
    private Date createTime;
}
