package com.dhitha.concurrent.rest.caller.service;

import com.dhitha.concurrent.rest.caller.model.College;
import com.dhitha.concurrent.rest.caller.model.Organization;
import com.dhitha.concurrent.rest.caller.model.Status;
import com.dhitha.concurrent.rest.caller.model.User;
import com.dhitha.concurrent.rest.caller.model.UserData;
import com.dhitha.concurrent.rest.caller.model.UserDataResponse;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Dhiraj
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CallerSyncService {
  private final CallableOneService oneService;

  private final CallableTwoService twoService;

  private final CallableThreeService threeService;

  private final CallableFourService fourService;

  public String performSynchronous(Integer id, String correlationKey){
    log.info(
        "Caller start key: {}, time: {}",
        correlationKey,
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    User user = oneService.findUserById(id, correlationKey);

    Organization org = twoService.findOrganizationById(user.getOrgId(), correlationKey);

    College college = threeService.findCollegeById(user.getCollegeId(), correlationKey);

    UserData userData =
        UserData.builder()
            .id(user.getId())
            .name(user.getName())
            .empId(user.getEmpId())
            .orgId(org.getId())
            .orgName(org.getName())
            .orgPinCode(org.getPinCode())
            .studentId(user.getStudentId())
            .collegeId(college.getId())
            .collegeName(college.getName())
            .collegePinCode(college.getPinCode())
            .build();

    UserDataResponse saveUserData = fourService.saveUserData(userData, correlationKey);
    Status status = saveUserData.getStatus();

    int tryCount = 1;
    while (tryCount < 5 && !Status.SUCCESS.equals(status)) {
      log.info("Get status count {}", tryCount);
      status =
          fourService
              .getUserDataSaveStatus(saveUserData.getRequestId(), tryCount, correlationKey)
              .getStatus();
      tryCount++;
    }
    log.info(
        "Caller end key: {}, time: {}",
        correlationKey,
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    return "SUCCESS";
  }
}
