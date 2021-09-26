package com.dhitha.concurrent.rest.caller.controller;

import com.dhitha.concurrent.rest.caller.service.CallerAsyncService;
import com.dhitha.concurrent.rest.caller.service.CallerSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author Dhiraj */
@RestController
@RequestMapping("/caller")
@RequiredArgsConstructor
@Slf4j
public class CallerController {

  private final CallerSyncService callerSyncService;

  private final CallerAsyncService callerAsyncService;

  @GetMapping(value = "/sync/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
  public String performSynchronous(@PathVariable Integer id, @RequestHeader String correlationKey) {
    return callerSyncService.performSynchronous(id, correlationKey);
  }

  @GetMapping(value = "/async/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
  public String performASynchronous(@PathVariable Integer id, @RequestHeader String correlationKey) {
    return callerAsyncService.performAsync(id, correlationKey);
  }
}
