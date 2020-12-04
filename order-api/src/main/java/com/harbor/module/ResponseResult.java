package com.harbor.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by harbor on 2020/3/28.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    private Map<String,Object> map;

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<T>(ResultCode.SUCCESS.getCode(), "Success", data, null);
    }

    public static <T> ResponseResult<T> ok(T data, Map<String,Object> map) {
        return new ResponseResult<T>(ResultCode.SUCCESS.getCode(), "Success", data, map);
    }

    public static ResponseResult ok(){
        return new ResponseResult().builder().code(ResultCode.SUCCESS.getCode()).message("Success").build();
    }

    public static ResponseResult unauthrized(){
        return new ResponseResult().builder().code(ResultCode.UNAUTHORIZED.getCode()).message("Operation unauthorized").build();
    }

    public static ResponseResult error(String msg){
        return new ResponseResult().builder().code(ResultCode.INTERNAL_SERVER_ERROR.getCode()).message(msg).build();
    }

    public static ResponseResult notFound(String message){
        return new ResponseResult().builder().code(ResultCode.NOT_FOUND.getCode()).message(message).build();
    }
}
