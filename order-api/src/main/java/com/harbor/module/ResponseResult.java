package com.harbor.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<T>(ResultCode.SUCCESS.getCode(), "Success", data);
    }

    public static ResponseResult ok(){
        return new ResponseResult().builder().code(ResultCode.SUCCESS.getCode()).message("Success").build();
    }

    public static ResponseResult unauthrized(){
        return new ResponseResult().builder().code(ResultCode.UNAUTHORIZED.getCode()).message("Operation unauthorized").build();
    }

    public static ResponseResult error(){
        return new ResponseResult().builder().code(ResultCode.INTERNAL_SERVER_ERROR.getCode()).message("Internal server error").build();
    }

    public static ResponseResult notFound(String message){
        return new ResponseResult().builder().code(ResultCode.NOT_FOUND.getCode()).message(message).build();
    }
}
