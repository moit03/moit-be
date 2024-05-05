package com.sparta.moit.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    @Around("execution(* com.sparta.moit.domain..controller.*.*(..))")
    public Object measureClassMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (totalTime > 1000L) {
            log.warn("[" + className + "." + methodName + "] took " + totalTime + " ms.");
            return returnValue;
        }
        log.info("[" + className + "." + methodName + "] took " + totalTime + " ms.");
        return returnValue;
    }
}
