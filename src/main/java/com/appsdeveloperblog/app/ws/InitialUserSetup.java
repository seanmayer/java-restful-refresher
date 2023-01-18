package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.io.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.io.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.io.repositories.AuthorityRepository;
import com.appsdeveloperblog.app.ws.io.repositories.RoleRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialUserSetup {

  @Autowired
  private AuthorityRepository authorityRepository;

  @Autowired
  private RoleRepository roleRepository;

  @EventListener
  @Transactional
  public void onApplicationEvent(ApplicationReadyEvent event) {
    System.out.println("Application Event started...");

    AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
    AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
    AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

    createRole("ROLE_USER", List.of(readAuthority, writeAuthority));
    createRole(
      "ROLE_ADMIN",
      List.of(readAuthority, writeAuthority, deleteAuthority)
    );
  }

  @Transactional
  private AuthorityEntity createAuthority(String name) {
    AuthorityEntity authority = authorityRepository.findByName(name);
    if (authority == null) {
      authority = new AuthorityEntity(name);
      authorityRepository.save(authority);
    }
    return authority;
  }

  @Transactional
  private RoleEntity createRole(
    String name,
    List<AuthorityEntity> authorities
  ) {
    RoleEntity role = roleRepository.findByName(name);
    if (role == null) {
      role = new RoleEntity(name);
      role.setAuthorities(authorities);
      roleRepository.save(role);
    }
    return role;
  }
}
