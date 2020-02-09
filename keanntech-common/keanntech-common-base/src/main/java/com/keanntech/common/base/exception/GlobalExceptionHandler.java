package com.keanntech.common.base.exception;

import com.alibaba.fastjson.JSONObject;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {BaseException.class})
    @ResponseBody
    public Object baseExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        //系统级异常，错误码固定为-1，提示语固定为系统繁忙，请稍后再试
        ResponseData result = ResponseDataUtil.buildError(ResultEnums.ERROR.getCode(), ResultEnums.ERROR.getMsg());
        //如果是业务逻辑异常，返回具体的错误码与提示信息
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            result.setCode(baseException.getErrorType().getCode());
            result.setMsg(baseException.getErrorType().getMsg());
        } else {
            //对系统级异常进行日志记录
            logger.error("系统异常:" + e.getMessage(), e);
        }
        return JSONObject.toJSON(result);
    }

}
