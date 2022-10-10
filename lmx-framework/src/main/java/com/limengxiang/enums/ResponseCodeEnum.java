package com.limengxiang.enums;

public enum ResponseCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    USERNAME_NO_EXIST(502,"用户名不存在"),
    PHONE_NUMBER_EXIST(503,"手机号已存在"),
    EMAIL_EXIST(504, "邮箱已存在"),
    REQUIRE_USERNAME(505, "必需填写用户名"),
    FILE_TYPE_ERROR(507,"文件类型错误"),
    LOGIN_ERROR(506,"用户名或密码错误"),
    FILE_NAME_NULL(508,"文件名为空"),
    FILE_DELETE_ERROR(510,"文件删除错误"),
    USERINFO_REGISTER_ERROR(509,"用户填写信息不符合规范");
     final int code;
     final String msg;

    ResponseCodeEnum(int code, String errorMessage){
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
