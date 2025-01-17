package ru.clevertec.newsonline.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
public class ControllerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* ru.clevertec.newsonline.controller..*(..))") public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering in Method : " + joinPoint.getSignature().getName());
        logger.info("Class Name : " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Arguments : " + joinPoint.getArgs());
        logger.info("Target class : " + joinPoint.getTarget().getClass().getName());
    }

    @AfterReturning(pointcut = "execution(* ru.clevertec.newsonline.controller..*(..))", returning = "result")
    public void logAfterFindNewsById(JoinPoint joinPoint, Object result) {
        logger.info("Method Return value : " + result);
    }

    @AfterThrowing(pointcut = "execution(* ru.clevertec.newsonline.controller..*(..))", throwing = "error") public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Method Exception : " + error);
    }
}
