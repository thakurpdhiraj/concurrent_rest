package com.dhitha.concurrent.rest.callablethree.service;

import com.dhitha.concurrent.rest.callablethree.model.College;
import java.util.List;
import org.springframework.stereotype.Service;

/** @author Dhiraj */
@Service
public class CollegeService {

  private static final List<College> COLLEGES;

  static {
    College c1 = new College(101, "COLLEGE1", 400074);
    College c2 = new College(102, "COLLEGE2", 400071);
    College c3 = new College(103, "COLLEGE3", 400024);
    COLLEGES = List.of(c1, c2, c3);
  }

  public College findCollegeById(Integer id) {

    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return COLLEGES.stream().filter(o -> o.getId().equals(id)).findFirst().orElseThrow();
  }
}
