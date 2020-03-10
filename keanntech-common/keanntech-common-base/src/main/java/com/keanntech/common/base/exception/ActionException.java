package com.keanntech.common.base.exception;

import com.keanntech.common.base.reponse.ResultEnums;

public class ActionException extends BaseException {

    public ActionException(){
        super(ResultEnums.ERROR);
    }

    public ActionException(String message){
        super(ResultEnums.ERROR, message);
    }

}
