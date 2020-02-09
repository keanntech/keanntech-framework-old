package com.keanntech.common.base.reponse;

import com.keanntech.common.base.exception.ErrorType;

public enum ResultEnums implements ErrorType {

    SUCCESS("200", "请求成功！"),
    ERROR("-1", "请求失败！"),

    USER_NOT_EXIST("2000","用户不存在！"),

    RESOURCE_DATA_EMPTY("3000","资源数据为空！");

    private String code;
    private String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
