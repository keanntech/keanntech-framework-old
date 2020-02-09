package com.keanntech.common.base.exception;

import lombok.Getter;

@Getter
public class LogicErrorType implements ErrorType {

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    LogicErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
