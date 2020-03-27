package com.keanntech.common.base.exception;

public class ActionException extends BaseException {

    private Integer code;
    private String message;

    public ActionException(String message){
        super(-1, message);
        this.code = -1;
        this.message = message;
    }

    public ActionException(Integer code, String message){
        super(code, message);
        this.code = code;
        this.message = message;
    }

}
