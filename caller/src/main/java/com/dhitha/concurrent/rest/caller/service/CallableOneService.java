package com.dhitha.concurrent.rest.caller.service;

import com.dhitha.concurrent.rest.caller.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** @author Dhiraj */
@Service
public class CallableOneService {

  @Autowired private RestTemplate restTemplate;

  public User findUserById(Integer id, String correlationKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("correlationKey", correlationKey);
    HttpEntity<User> entity = new HttpEntity<>(headers);
    return restTemplate
        .exchange("http://localhost:8081/users/" + id, HttpMethod.GET, entity, User.class)
        .getBody();
  }
}
