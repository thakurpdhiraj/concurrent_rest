package com.dhitha.concurrent.rest.callableone.log;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/** @author Dhiraj */
@Aspect
@Configuration
@Slf4j
public class LogAspect {

  @Pointcut("execution(* com.dhitha.concurrent.rest.callableone.controller.UserController.getUserById(Integer,String))")
  public void aroundGetUserById() {}

  @Around("aroundGetUserById()")
  public Object logStartEndTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();

    log.info(
        "User Start: key: {}, time: {}, id: {}",
        args[1],
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
        args[0]);

    Object response = joinPoint.proceed();

    log.info(
        "User End: key: {}, time: {}, id: {}",
        args[1],
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
        args[0]);
    
    return response;
  }
}
