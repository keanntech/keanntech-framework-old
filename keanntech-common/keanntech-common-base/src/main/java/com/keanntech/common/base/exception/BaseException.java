package com.keanntech.common.base.exception;

import com.keanntech.common.base.reponse.ResultCodeEnum;
import lombok.Data;


@Data
public class BaseException extends RuntimeException {
    private Integer code;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
