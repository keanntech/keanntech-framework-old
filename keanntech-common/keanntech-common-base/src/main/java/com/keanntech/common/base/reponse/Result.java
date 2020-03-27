package com.keanntech.common.base.reponse;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result<T> {
    private Boolean status;
    private Integer code;
    private String message;

    private Map<String, T> data = new HashMap<>();

    // 构造器私有
    private Result(){}

    // 通用返回成功
    public static Result ok() {
        Result r = new Result();
        r.setStatus(ResultCodeEnum.SUCCESS.getStatus());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    // 通用返回失败，未知错误
    public static Result error() {
        Result r = new Result();
        r.setStatus(ResultCodeEnum.FAIL.getStatus());
        r.setCode(ResultCodeEnum.FAIL.getCode());
        r.setMessage(ResultCodeEnum.FAIL.getMessage());
        return r;
    }

    // 设置结果，形参为结果枚举
    public static Result setResult(ResultCodeEnum result) {
        Result r = new Result();
        r.setStatus(result.getStatus());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /**------------使用链式编程，返回类本身-----------**/

    // 自定义返回数据
    public Result data(Map<String,T> map) {
        this.setData(map);
        return this;
    }

    // 通用设置data
    public Result data(String key, T value) {
        this.data.put(key, value);
        return this;
    }

    // 自定义状态信息
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    // 自定义返回结果
    public Result success(Boolean status) {
        this.setStatus(status);
        return this;
    }
}
