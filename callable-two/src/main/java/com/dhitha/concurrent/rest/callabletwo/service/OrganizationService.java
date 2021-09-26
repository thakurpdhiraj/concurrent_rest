package com.dhitha.concurrent.rest.callabletwo.service;

import com.dhitha.concurrent.rest.callabletwo.model.Organization;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Dhiraj
 */
@Service
public class OrganizationService {

  private static final List<Organization> ORGANIZATIONS;

  static {
    Organization o1 = new Organization(1, "ORG1", 400074);
    Organization o2 = new Organization(2, "ORG2", 400071);
    Organization o3 = new Organization(3, "ORG3", 400024);
    ORGANIZATIONS = List.of(o1,o2,o3);
  }

  public Organization findOrgById(Integer id) {

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return ORGANIZATIONS.stream().filter(o -> o.getId().equals(id)).findFirst().orElseThrow();
  }
}
