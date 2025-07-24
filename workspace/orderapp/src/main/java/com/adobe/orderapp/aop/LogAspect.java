package com.adobe.orderapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Before("execution(* com.adobe.orderapp.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        log.info("Called :" + jp.getSignature());
        Object[] args = jp.getArgs();
        for(Object arg: args) {
            log.info("Argument : " + arg);
        }
    }

    @After("execution(* com.adobe.orderapp.service.*.*(..))")
    public void logAfter(JoinPoint jp) {
        log.info("*********");
    }

    @AfterThrowing(value = "execution(* com.adobe.orderapp.service.*.*(..))" ,throwing = "ex")
    public void handleException(Exception ex) {
        log.info("Exception :-< {}", ex.getMessage());
    }

}
