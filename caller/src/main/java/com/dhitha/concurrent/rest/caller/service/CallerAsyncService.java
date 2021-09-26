package com.dhitha.concurrent.rest.caller.service;

import com.dhitha.concurrent.rest.caller.model.College;
import com.dhitha.concurrent.rest.caller.model.Organization;
import com.dhitha.concurrent.rest.caller.model.Status;
import com.dhitha.concurrent.rest.caller.model.User;
import com.dhitha.concurrent.rest.caller.model.UserData;
import com.dhitha.concurrent.rest.caller.model.UserDataResponse;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** @author Dhiraj */
@Service
@RequiredArgsConstructor
@Slf4j
public class CallerAsyncService {

  private final CallableOneService oneService;

  private final CallableTwoService twoService;

  private final CallableThreeService threeService;

  private final CallableFourService fourService;

  public String performAsync(Integer id, String correlationKey) {
    log.info(
        "Caller start key: {}, time: {}",
        correlationKey,
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS));

    CompletableFuture<String> getSaveStatus =
        findUser(id, correlationKey)
            .thenComposeAsync(
                u -> {
                  CompletableFuture<Organization> org = findOrg(u.getOrgId(), correlationKey);
                  CompletableFuture<College> college =
                      findCollege(u.getCollegeId(), correlationKey);
                  return org.thenCombineAsync(
                      college,
                      (o, c) ->
                          UserData.builder()
                              .id(u.getId())
                              .name(u.getName())
                              .empId(u.getEmpId())
                              .orgId(o.getId())
                              .orgName(o.getName())
                              .orgPinCode(o.getPinCode())
                              .studentId(u.getStudentId())
                              .collegeId(c.getId())
                              .collegeName(c.getName())
                              .collegePinCode(c.getPinCode())
                              .build());
                })
            .thenComposeAsync(
                userData ->
                    saveUserData(userData, correlationKey)
                        .thenApplyAsync(
                            userDataResponse -> {
                              Status status = userDataResponse.getStatus();
                              int tryCount = 1;
                              while (tryCount < 5 && !Status.SUCCESS.equals(status)) {
                                log.info("Get status count {}", tryCount);
                                status =
                                    fourService
                                        .getUserDataSaveStatus(
                                            userDataResponse.getRequestId(),
                                            tryCount,
                                            correlationKey)
                                        .getStatus();
                                tryCount++;
                              }
                              return "SUCCESS";
                            }));
    String result = getSaveStatus.join();
    log.info(
        "Caller end key: {}, time: {}",
        correlationKey,
        LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    return result;
  }

  private CompletableFuture<UserDataResponse> saveUserData(
      UserData userData, String correlationKey) {
    return CompletableFuture.supplyAsync(() -> fourService.saveUserData(userData, correlationKey));
  }

  private CompletableFuture<User> findUser(Integer id, String correlationKey) {
    return CompletableFuture.supplyAsync(() -> oneService.findUserById(id, correlationKey));
  }

  private CompletableFuture<Organization> findOrg(Integer id, String correlationKey) {
    return CompletableFuture.supplyAsync(() -> twoService.findOrganizationById(id, correlationKey));
  }

  private CompletableFuture<College> findCollege(Integer id, String correlationKey) {
    return CompletableFuture.supplyAsync(() -> threeService.findCollegeById(id, correlationKey));
  }
}
