package com.mydddyh.exception;

import com.mydddyh.enums.AppHttpCodeEnum;

public class SystemException extends RuntimeException {
    private int code;
    private String msg;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        this(httpCodeEnum.getCode(), httpCodeEnum.getMsg());
    }

    public SystemException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
