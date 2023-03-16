package com.mydddyh.enums;

import com.mydddyh.constants.SystemConstants;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功 ^_^"),
    // 登录
    NEED_LOGIN          (401,"请先登录 -_-!"),
    NO_OPERATOR_AUTH    (403,"无权限操作 -_-!"),
    SYSTEM_ERROR        (500,"系统出现错误 -_-!"),
    LOGIN_ERROR         (600,"用户名或密码错误 -_-!"),
    PASSWORD_ERROR      (601,"密码错误 -_-!"),
    USER_NOT_EXIST      (602,"用户不存在 -_-!"),
    OTHER_SYSTEM_ERROR  (603,"认证或授权失败 -_-!"),
    REQUIRE_USERNAME    (604,"未填写用户名 -_-!"),
    REQUIRE_PASSWORD    (605,"未填写密码 -_-!"),
    REQUIRE_COMMENT     (606,"未填写评论 -_-!"),
    FILE_TYPE_ERROR     (607,"请上传 JPG/JPEG/PNG 格式的文件 -_-!"),
    WRONG_USER_ID       (608,"用户ID不匹配 -_-!"),
    USERNAME_NULL       (609,"用户名为空 -_-!"),
    NICKNAME_NULL       (610,"昵称为空 -_-!"),
    EMAIL_NULL          (611,"邮箱为空 -_-!"),
    PASSWORD_NULL       (612,"密码为空 -_-!"),
    USERNAME_EXIST      (613,"用户名已存在 -_-!"),
    EMAIL_EXIST         (614,"邮箱已存在 -_-!"),
    PHONE_NUMBER_EXIST  (615,"手机号已存在 -_-!"),
    TAG_NAME_NULL       (616,"未填写标签名称 -_-!"),
    TAG_NAME_EXIST      (617,"标签名称已存在 -_-!"),
    TAG_ID_NULL         (618,"未填写标签ID -_-!"),
    TAG_NOT_EXIST       (619,"标签不存在 -_-!"),
    FILE_UPLOAD_ERROR   (620,"文件上传失败 -_-!"),
    EXPORT_FILE_ERROR   (621,"文件导出错误 -_-!"),
    ARTICLE_NOT_EXIST   (622,"文章不存在 -_-!"),
    MENU_NOT_EXIST      (623,"菜单不存在 -_-!"),
    MENU_ID_ERROR       (624,"上级菜单不能为自身 -_-!"),
    MENU_CHILDREN_EXIST (625,"存在子菜单时不能删除 -_-!"),
    ROLE_NOT_EXIST      (626,"角色不存在 -_-!"),
    UPDATE_SUPER_ADMIN  (627,"不能修改超级管理员 -_-!"),
    DELETE_SUPER_ADMIN  (628,"不能删除超级管理员 -_-!"),
    DELETE_MULTI_ROWS   (629,"目前请不要一次性删除多行 orz"),
    CATEGORY_NOT_EXIST  (630,"分类不存在 -_-!"),
    LINK_NOT_EXIST      (631,"友链不存在 -_-!"),
    ARTICLE_NO_COMMENT  (632,"该文章不允许评论 -_-!");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
