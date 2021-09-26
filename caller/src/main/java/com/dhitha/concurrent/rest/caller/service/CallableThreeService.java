package com.dhitha.concurrent.rest.caller.service;

import com.dhitha.concurrent.rest.caller.model.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** @author Dhiraj */
@Service
public class CallableThreeService {
  @Autowired private RestTemplate restTemplate;

  public College findCollegeById(Integer id, String correlationKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("correlationKey", correlationKey);
    HttpEntity<College> entity = new HttpEntity<>(headers);
    return restTemplate
        .exchange("http://localhost:8083/colleges/" + id, HttpMethod.GET, entity, College.class)
        .getBody();
  }
}
