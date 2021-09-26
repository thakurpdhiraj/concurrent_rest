package com.dhitha.concurrent.rest.caller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Dhiraj */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserData {

  private Integer id;

  private String name;

  private Integer empId;

  private Integer orgId;

  private String orgName;

  private Integer orgPinCode;

  private Integer studentId;

  private Integer collegeId;

  private String collegeName;

  private Integer collegePinCode;
}
