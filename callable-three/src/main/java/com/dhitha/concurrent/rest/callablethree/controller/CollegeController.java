package com.dhitha.concurrent.rest.callablethree.controller;

import com.dhitha.concurrent.rest.callablethree.model.College;
import com.dhitha.concurrent.rest.callablethree.model.Error;
import com.dhitha.concurrent.rest.callablethree.service.CollegeService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author Dhiraj */
@RestController
@RequestMapping("/colleges")
public class CollegeController {

  @Autowired private CollegeService collegeService;

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<College> getCollegeById(
      @PathVariable Integer id, @RequestHeader String correlationKey) {
    College org = collegeService.findCollegeById(id);
    return ResponseEntity.ok(org);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception e, HttpServletRequest request) {
    String correlationKey = request.getHeader("correlationKey");

    Error error = new Error(404, e.getMessage(), correlationKey);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
