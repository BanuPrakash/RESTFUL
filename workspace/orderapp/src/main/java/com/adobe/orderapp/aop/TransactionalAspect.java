package com.adobe.orderapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class TransactionalAspect {

    @Around("@annotation(Tx)")
    public Object doTransaction(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = null;
        try {
            log.info("Transaction starts");
            // Transaction tx = em.beginTransaction();
            // Transaction tx = jta.beginTransaction();
                ret = pjp.proceed(); // invoke actual method
            log.info("Transaction commits");
            // tx.commit
        } catch (Exception ex) {
            // tx.rollback();
            log.info("Transaction rollbacks");
            throw  ex;
        }
        return  ret;
    }
}
