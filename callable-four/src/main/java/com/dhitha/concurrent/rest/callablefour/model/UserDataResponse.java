package com.dhitha.concurrent.rest.callablefour.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Dhiraj
 */
@Data
@AllArgsConstructor
public class UserDataResponse {

  String requestId;

  Status status;

}
