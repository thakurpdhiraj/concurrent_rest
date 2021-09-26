package com.dhitha.concurrent.rest.callablefour.model;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author Dhiraj
 */
@Data
public class Error {

  private LocalDateTime timestamp;

  private Integer status;

  private String error;

  private String correlationKey;

  public Error() {
    timestamp = LocalDateTime.now();
    status = 500;
    error = "Something Went Wrong";
  }

  public Error(Integer status, String error, String correlationKey) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.error = error;
    this.correlationKey = correlationKey;
  }
}
