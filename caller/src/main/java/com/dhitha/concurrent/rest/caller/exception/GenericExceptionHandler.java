package com.dhitha.concurrent.rest.caller.exception;

import com.dhitha.concurrent.rest.caller.model.Error;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

/** @author Dhiraj */
@ControllerAdvice
public class GenericExceptionHandler {

  @Autowired private ObjectMapper objectMapper;

  @ExceptionHandler(RestClientResponseException.class)
  public ResponseEntity<List<Error>> handleRestClientException(RestClientResponseException ex) {
    String responseBodyAsString = ex.getResponseBodyAsString();
    List<Error> errors;
    try {
      errors = objectMapper.readValue(
          responseBodyAsString,
          objectMapper.getTypeFactory().constructCollectionType(List.class, Error.class));
    } catch (JsonProcessingException e) {
      errors = List.of(new Error());
    }
    return ResponseEntity.status(ex.getRawStatusCode()).body(errors);
  }
}
