package com.keanntech.common.base.reponse;

import com.keanntech.common.base.exception.ErrorType;

public enum ResultEnums implements ErrorType {

    SUCCESS(200, "请求成功！"),
    ERROR(-1, "请求失败！"),

    USER_NOT_EXIST(2000,"用户不存在！"),
    USERINFO_NOT_EMPTY(2001,"用户信息不能为空！"),
    USERINFO_NOT_INTEGRATED(2002,"用户信息不完整！"),

    MENU_EMPTY(3000,"未设置菜单！"),

    ROLE_EMPTY(4000, "未分配角色！");

    private Integer code;
    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
