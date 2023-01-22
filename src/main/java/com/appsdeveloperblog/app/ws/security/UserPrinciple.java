package com.appsdeveloperblog.app.ws.security;

import com.appsdeveloperblog.app.ws.io.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.io.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails {

  private static final long serialVersionUID = -89898989898989898L;

  private UserEntity userEntity;
  private String userId;

  public UserPrinciple(UserEntity userEntity) {
    this.userEntity = userEntity;
    this.userId = userEntity.getUserId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    List<AuthorityEntity> authorityEntities = new ArrayList<>();

    Collection<RoleEntity> roles = this.userEntity.getRoles();

    if (roles == null) {
      return authorities;
    }

    roles.forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
      authorityEntities.addAll(role.getAuthorities());
    });

    authorityEntities.forEach(authorityEntity -> {
      authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
    });

    return authorities;
  }

  @Override
  public String getPassword() {
    return this.userEntity.getEncryptPassword();
  }

  @Override
  public String getUsername() {
    return this.userEntity.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.userEntity.getEmailVerificationStatus();
  }

  public String getUserId() {
    return this.userId;
  }

  public UserEntity getUserEntity() {
    return this.userEntity;
  }
}
