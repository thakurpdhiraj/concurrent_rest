package com.dhitha.concurrent.rest.callablefour.service;

import com.dhitha.concurrent.rest.callablefour.model.Status;
import com.dhitha.concurrent.rest.callablefour.model.UserData;
import com.dhitha.concurrent.rest.callablefour.model.UserDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** @author Dhiraj */
@Service
@Slf4j
public class UserDataService {

  public UserDataResponse saveUserData(UserData userData, String correlationKey) {
    log.info("UserData: saveUserData, key: {} user: {}", correlationKey, userData);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return new UserDataResponse(correlationKey, Status.IN_PROGRESS);
  }

  public UserDataResponse getSaveStatus(Integer tryCount, String correlationKey) {
    if (tryCount == 3) {
      return new UserDataResponse(correlationKey, Status.SUCCESS);
    }
    return new UserDataResponse(correlationKey, Status.IN_PROGRESS);
  }
}
