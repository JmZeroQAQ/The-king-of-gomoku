package com.tkog.backend.enums;


public enum AppHttpCodeEnum {
    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    RESOURCE_NOT_EXIST(404, "请求的资源不存在"),
    SYSTEM_ERROR(500, "出现错误"),
    LOGIN_ERROR(504, "用户名或密码错误"),
    USERNAME_EXIST(501, "用户名已存在");

    private final int code;
    private final String message;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
