package com.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    // setup logger
    private Logger logger = Logger.getLogger(CRMLoggingAspect.class.getName());

    // setup pointcut declarations
    @Pointcut("execution(* com.project.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* com.project.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("execution(* com.project.dao.*.*(..))")
    private void forDaoPackage() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {
    }

    // add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {

        // display method arguments we are calling
        String method = joinPoint.getSignature().toShortString();
        logger.info("->>> in @Before: " + method);

        // display the arguments to the method

        // get the arguments
        Object[] args = joinPoint.getArgs();

        // loop through and display args
        for (Object tempArg : args) {
            logger.info("->>> argument: " + tempArg);
        }
    }

    // add @AfterReturning advice
    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterReturning(JoinPoint joinPoint, Object theResult) {

        // display method we are returning from
        String method = joinPoint.getSignature().toShortString();
        logger.info("->>> in AfterReturning: " + method);

        // display data returned
        logger.info("->>> result:" + theResult);
    }

}
