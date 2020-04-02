package com.keanntech.common.base.reponse;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 200, ""),
    FAIL(false, -1, "");

    private Boolean status;
    private Integer code;
    private String message;



    ResultCodeEnum(boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
