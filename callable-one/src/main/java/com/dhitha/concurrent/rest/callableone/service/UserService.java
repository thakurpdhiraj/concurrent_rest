package com.dhitha.concurrent.rest.callableone.service;

import com.dhitha.concurrent.rest.callableone.model.User;
import java.util.List;
import org.springframework.stereotype.Service;

/** @author Dhiraj */
@Service
public class UserService {

  public static final List<User> USERS;

  static {
    User u1 = new User(1, "User1", 10001, 1,5001,101);
    User u2 = new User(2, "User2", 10002,1,5002,101);
    User u3 = new User(3, "User3", 10003,1,1001,102);
    User u4 = new User(4, "User4", 50001, 2, 1002, 102);
    User u5 = new User(5, "User5", 50002, 2,1003,102);
    USERS = List.of(u1, u2, u3, u4, u5);
  }

  public User findUserById(Integer id) {
    return USERS.stream().filter(u -> u.getId().equals(id)).findFirst().orElseThrow();
  }
}
