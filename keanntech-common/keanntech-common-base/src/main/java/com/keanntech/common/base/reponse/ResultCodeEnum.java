package com.keanntech.common.base.reponse;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 200, "请求成功"),
    FAIL(false, -1, "请求失败");

    private Boolean status;
    private Integer code;
    private String message;



    ResultCodeEnum(boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
