package com.harbor.exception;


import com.harbor.module.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackages = "com.harbor")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandle(MethodArgumentNotValidException  e){ // 处理方法参数的异常类型

        //按需重新封装需要返回的错误信息
        List<String> errors = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }


        return new ResponseEntity(ResponseResult.error(String.join(",", errors)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity handle(RuntimeException e){
        log.error("internal server error", e);
        return new ResponseEntity(ResponseResult.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); //自己需要实现的异常处理
    }


}
