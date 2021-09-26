package com.dhitha.concurrent.rest.callablefour.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/** @author Dhiraj */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class UserData {

  @NotNull
  @Min(0)
  private Integer id;

  @NotEmpty
  private String name;

  @NotNull
  private Integer empId;

  @NotNull
  private Integer orgId;

  @NotEmpty
  private String orgName;

  @NotNull
  private Integer orgPinCode;

  @NotNull
  private Integer studentId;

  @NotNull
  private Integer collegeId;

  @NotEmpty
  private String collegeName;

  @NotNull
  private Integer collegePinCode;
}
