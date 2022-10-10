package com.limengxiang.handler.exception;


import com.limengxiang.domain.ResponseResult;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e.toString());
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseResult exceptionHandler(Exception e){
//        //打印异常信息
//        log.error("出现了异常！ {}",e.toString());
//        //从异常对象中获取提示信息封装返回
//        return ResponseResult.errorResult(ResponseCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
//    }

    @ExceptionHandler(BindException.class)
    public ResponseResult bindExceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e.toString());
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(ResponseCodeEnum.USERINFO_REGISTER_ERROR.getCode(),e.getMessage());
    }
}
