package com.dhitha.concurrent.rest.caller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Dhiraj */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

  private Integer id;

  private String name;

  private Integer empId;

  private Integer orgId;

  private Integer studentId;

  private Integer collegeId;
}
