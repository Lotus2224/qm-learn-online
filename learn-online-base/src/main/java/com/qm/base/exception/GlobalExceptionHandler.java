package com.qm.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

// 统一异常处理类
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //对项目的自定义异常类型进行处理
    @ResponseBody
    @ExceptionHandler(LearnOnlineException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 该异常枚举错误码为500
    public RestErrorResponse customException(LearnOnlineException exception) {
        log.error("系统异常{}", exception.getErrMessage());
        return new RestErrorResponse(exception.getErrMessage());
    }

    // 处理其它异常
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception exception) {
        log.error("系统异常{}", exception.getMessage());
        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    // 处理JSR-303抛出的异常，方法参数无效异常
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult(); // 获取失败的验证结果
        List<FieldError> fieldErrors = bindingResult.getFieldErrors(); // 获取字段错误列表
        StringBuffer stringBuffer = new StringBuffer(); // 定义一个线程安全的可变字符串对象，用来记录错误信息
        fieldErrors.forEach(fieldError -> stringBuffer.append(fieldError.getDefaultMessage()).append(","));
        log.error(stringBuffer.toString());
        return new RestErrorResponse(stringBuffer.toString());
    }
}