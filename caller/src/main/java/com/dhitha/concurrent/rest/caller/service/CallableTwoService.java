package com.dhitha.concurrent.rest.caller.service;

import com.dhitha.concurrent.rest.caller.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** @author Dhiraj */
@Service
public class CallableTwoService {
  @Autowired private RestTemplate restTemplate;

  public Organization findOrganizationById(Integer id, String correlationKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("correlationKey", correlationKey);
    HttpEntity<Organization> entity = new HttpEntity<>(headers);
    return restTemplate
        .exchange(
            "http://localhost:8082/organizations/" + id, HttpMethod.GET, entity, Organization.class)
        .getBody();
  }
}
