package com.dhitha.concurrent.rest.callabletwo.controller;

import com.dhitha.concurrent.rest.callabletwo.model.Error;
import com.dhitha.concurrent.rest.callabletwo.model.Organization;
import com.dhitha.concurrent.rest.callabletwo.service.OrganizationService;
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
@RequestMapping("/organizations")
public class OrganizationController {

  @Autowired private OrganizationService organizationService;

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Organization> getOrgById(
      @PathVariable Integer id, @RequestHeader String correlationKey) {
    Organization org = organizationService.findOrgById(id);
    return ResponseEntity.ok(org);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleNoSuchElementException(Exception e, HttpServletRequest request) {
    String correlationKey = request.getHeader("correlationKey");

    Error error = new Error(404, e.getMessage(), correlationKey);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
