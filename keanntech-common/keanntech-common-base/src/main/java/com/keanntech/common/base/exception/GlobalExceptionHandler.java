package com.keanntech.common.base.exception;

import com.keanntech.common.base.reponse.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**-------- 通用异常处理方法 --------**/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage()).code(-1);    // 通用异常结果
    }

    /**-------- 自定义定异常处理方法 --------**/
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Result error(BaseException e) {
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage()).code(e.getCode());
    }

}
