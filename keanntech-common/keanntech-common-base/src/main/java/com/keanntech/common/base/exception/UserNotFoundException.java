package com.keanntech.common.base.exception;

import com.keanntech.common.base.reponse.ResultEnums;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ResultEnums.USER_NOT_EXIST);
    }

    public UserNotFoundException(String message) {
        super(ResultEnums.USER_NOT_EXIST, message);
    }

}
