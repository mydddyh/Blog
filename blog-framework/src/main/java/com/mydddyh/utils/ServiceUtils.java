package com.mydddyh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ServiceUtils {
    public static String addWildcard(String s) {
        final char ch = '%';
        return ch + s + ch;
    }

    public static String generateFilePath(String fileName, Boolean inDatePath){
        // 获取日期
        String date = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        // 获取uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取后缀名
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        // 拼接文件名
        StringBuilder stringBuilder = new StringBuilder();
        if (inDatePath) stringBuilder.append(date);
        return stringBuilder.append(uuid).append(fileType).toString();
    }
}
