package com.senlainc.advertisementsystem.controller;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j2
public class ControllerAspect {

    @Pointcut("execution(public * com.senlainc.advertisementsystem.controller..*(..))")
    public void controllerMethod() { }

    @Around("controllerMethod()")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        StringBuilder sb = new StringBuilder();

        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();

        sb.append("\nClass Name: ").append(point.getSignature().getDeclaringTypeName())
                .append("\nMethod Name: ").append(point.getSignature().getName())
                .append("\nParameters: ").append(Arrays.toString(point.getArgs()))
                .append("\nReturn value: ").append(object)
                .append("\nTime taken for execution: ").append(endTime - startTime).append(" ms").append("\n-------\n");

        log.warn(sb.toString());

        return object;
    }
}
