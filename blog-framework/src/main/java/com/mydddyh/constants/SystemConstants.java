package com.mydddyh.constants;

public class SystemConstants {
    /**
     *  文章是正常发布状态
     */
    public static final String ARTICLE_STATUS_NORMAL = "0";
    /**
     *  分类是正常发布状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     *  友联审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";
    /**
     *  前台web用户userId在Reids中的前缀
     */
    public static final String WEB_USERID_REDIS_PREFIX = "webLogin:";
    /**
     *  后台admin用户userId在Reids中的前缀
     */
    public static final String ADMIN_USERID_REDIS_PREFIX = "adminLogin:";
    /**
     *  报文头部中的token字段
     */
    public static final String HEADER_TOKEN_FIELD = "token";
    /**
     *  Comment表中文章评论的Type字段
     */
    public static final String ARTICLE_COMMENT_TYPE_FIELD = "0";
    /**
     *  Comment表中友链评论的Type字段
     */
    public static final String LINK_COMMENT_TYPE_FIELD = "1";
    /**
     *  Comment表中根评论的Root Id字段
     */
    public static final Long ROOT_COMMENT_ROOT_ID_FIELD =  -1L;
    /**
     *  Comment表中根评论的To Comment User Id字段
     */
    public static final Long ROOT_COMMENT_TO_COMMENT_USER_ID_FIELD =  -1L;
    /**
     *  User表中前台web用户的Type字段
     */
    public static final String NORMAL_USER_TYPE_FIELD =  "0";
    /**
     *  User表中后台admin用户的Type字段
     */
    public static final String ADMIN_USER_TYPE_FIELD =  "1";
    /**
     *  User表中超级管理员的Id字段
     */
    public static final Long SUPER_ADMIN_USER_ID = 1L;
    /**
     *  User表中Id字段默认字段
     */
    public static final Long DEFAULT_USER_ID = -1L;
    /**
     *  图片允许的三种格式
     */
    public static final String PICTURE_JPG_FILE_FORMAT = ".jpg";
    public static final String PICTURE_JPEG_FILE_FORMAT = ".jpeg";
    public static final String PICTURE_PNG_FILE_FORMAT = ".png";
    /**
     *  viewCount散列在Reids中的键值key
     */
    public static final String VIEW_COUNT_REDIS_KEY = "article:viewCount";
    /**
     *  Menu表中菜单的Menu Type字段
     */
    public static final String MENU_MENU_TYPE_FIELD =  "C";
    /**
     *  Menu表中按钮的Menu Type字段
     */
    public static final String BUTTON_MENU_TYPE_FIELD =  "F";
    /**
     *  Menu表中正常状态
     */
    public static final String MENU_STATUS_NORMAL = "0";
    /**
     *  Role表中超级管理员的Role Key字段
     */
    public static final String SUPER_ADMIN_ROLE_KEY_TYPE_FIELD = "admin";
    /**
     *  Menu表中根节点的Parent Id字段
     */
    public static final Long ROOT_PARENT_ID_TYPE_FIELD = 0L;
    /**
     *  Category表导出Excel文件名
     */
    public static final String CATEGORY_EXPORT_EXCEL_FILE_NAME = "Category Export 分类导出";
    /**
     *  Category表导出Excel Sheet名
     */
    public static final String CATEGORY_EXPORT_EXCEL_SHEET_NAME = "Category 分类";
    /**
     *  Role表中超级管理员的Id字段
     */
    public static final Long SUPER_ADMIN_ROLE_ID = 1L;
    /**
     *  Role表中正常状态
     */
    public static final String ROLE_STATUS_NORMAL = "0";
    /**
     *  Atricle表中不允许评论的Is Comment字段
     */
    public static final String ARTICLE_NO_COMMENT_IS_COMMENT_TYPE_FIELD = "1";

}

