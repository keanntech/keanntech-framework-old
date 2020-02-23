package com.keanntech.common.base.exception;

import lombok.Getter;

@Getter
public class LogicErrorType implements ErrorType {

    /**
     * 错误类型码
     */
    private Integer code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    LogicErrorType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
