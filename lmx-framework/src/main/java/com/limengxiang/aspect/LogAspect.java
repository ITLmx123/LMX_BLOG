package com.limengxiang.aspect;

import com.alibaba.fastjson.JSON;
import com.limengxiang.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Component
@Aspect
@Slf4j
public class LogAspect {

    Lock lock = new ReentrantLock();

    @Pointcut("@annotation(com.limengxiang.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        lock.lock();
        Object result;
        try {
            beforeLog(joinPoint);
            result = joinPoint.proceed();
            afterLog(result);
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        lock.unlock();
        return result;
    }

    private void beforeLog(ProceedingJoinPoint joinPoint) {
        //定义一个字符串集合，来判断一些方法不需要出参，避免报错
        List<String> strings = Arrays.asList("uploadImg","");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取被增强方法上面的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        if (strings.contains(joinPoint.getSignature().getName())){
            return;
        }
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SystemLog.class);
    }

    private void afterLog(Object result) {
        // 打印出参
        log.info("Response       : {}",result);
    }
}
