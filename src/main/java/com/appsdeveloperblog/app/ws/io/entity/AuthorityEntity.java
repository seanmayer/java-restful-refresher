package com.appsdeveloperblog.app.ws.io.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class AuthorityEntity implements Serializable {

  private static final long serialVersionUID = -89898989898989898L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20)
  private String name;

  @ManyToMany(mappedBy = "authorities")
  private Collection<RoleEntity> roles;

  public AuthorityEntity() {}

  public AuthorityEntity(String name) {
    this.name = name;
  }
}
