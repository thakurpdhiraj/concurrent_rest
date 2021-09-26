package com.dhitha.concurrent.rest.caller.service;

import com.dhitha.concurrent.rest.caller.model.UserData;
import com.dhitha.concurrent.rest.caller.model.UserDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/** @author Dhiraj */
@Service
public class CallableFourService {
  @Autowired private RestTemplate restTemplate;

  public UserDataResponse saveUserData(UserData userData, String correlationKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("correlationKey", correlationKey);
    HttpEntity<UserData> entity = new HttpEntity<>(userData, headers);
    return restTemplate
        .exchange("http://localhost:8084/userdata", HttpMethod.POST, entity, UserDataResponse.class)
        .getBody();
  }

  public UserDataResponse getUserDataSaveStatus(
      String requestId, Integer tryCount, String correlationKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("correlationKey", correlationKey);
    HttpEntity<UserData> entity = new HttpEntity<>(headers);
    UriComponentsBuilder uriBuilder =
        UriComponentsBuilder.fromHttpUrl("http://localhost:8084/userdata/status")
            .queryParam("requestId", requestId)
            .queryParam("tryCount", tryCount);
    return restTemplate
        .exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, UserDataResponse.class)
        .getBody();
  }
}
