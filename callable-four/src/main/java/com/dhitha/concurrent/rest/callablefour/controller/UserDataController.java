package com.dhitha.concurrent.rest.callablefour.controller;

import com.dhitha.concurrent.rest.callablefour.model.Error;
import com.dhitha.concurrent.rest.callablefour.model.UserData;
import com.dhitha.concurrent.rest.callablefour.model.UserDataResponse;
import com.dhitha.concurrent.rest.callablefour.service.UserDataService;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** @author Dhiraj */
@RestController
@RequestMapping("/userdata")
public class UserDataController {

  @Autowired private UserDataService userDataService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDataResponse> saveUserData(
      @Valid @RequestBody UserData userData, @RequestHeader String correlationKey) {

    UserDataResponse response = userDataService.saveUserData(userData, correlationKey);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDataResponse> getSaveStatus(
      @RequestParam String requestId,
      @RequestParam Integer tryCount,
      @RequestHeader String correlationKey) {
    UserDataResponse saveStatus = userDataService.getSaveStatus(tryCount, requestId);
    return ResponseEntity.ok(saveStatus);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Object> handleBindException(BindException e, HttpServletRequest request) {
    String correlationKey = request.getHeader("correlationKey");
    List<Error> errors =
        e.getFieldErrors().stream()
            .map(
                error ->
                    new Error(
                        400, error.getField() + " " + error.getDefaultMessage(), correlationKey))
            .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception e, HttpServletRequest request) {
    String correlationKey = request.getHeader("correlationKey");

    Error error = new Error(500, e.getMessage(), correlationKey);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(error));
  }
}
