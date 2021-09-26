package com.dhitha.concurrent.rest.callablefour.log;

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

  @Pointcut(
      "execution(* com.dhitha.concurrent.rest.callablefour.controller.UserDataController.saveUserData(com.dhitha.concurrent.rest.callablefour.model.UserData,String))")
  public void aroundSaveUserData() {}

  @Pointcut(
      "execution(* com.dhitha.concurrent.rest.callablefour.controller.UserDataController.getSaveStatus(String,Integer,String))")
  public void aroundGetSaveStatus() {}

  @Around("aroundSaveUserData()")
  public Object logStartEndTimeSaveUserData(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();

    log.info(
        "UserData Save Start: key: {}, time: {}",
        args[1],
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS));

    Object response = joinPoint.proceed();

    log.info(
        "UserData Save End: key: {}, time: {},",
        args[1],
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS));

    return response;
  }

  @Around("aroundGetSaveStatus()")
  public Object logStartEndTimeGetStatus(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();

    log.info(
        "UserData Get Status Start: key: {}, time: {}, requestId: {}, tryCount: {}",
        args[2],
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
        args[0],
        args[1]);

    Object response = joinPoint.proceed();

    log.info(
        "UserData Get Status End: key: {}, time: {}, requestId: {}, tryCount: {}",
        args[2],
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
        args[0],
        args[1]);

    return response;
  }
}
