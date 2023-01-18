package com.appsdeveloperblog.app.ws.io.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

  private static final long serialVersionUID = -89898989898989898L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private Collection<UserEntity> users;

  @ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
  @JoinTable(
    name = "roles_authorities",
    joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
      name = "authorities_id",
      referencedColumnName = "id"
    )
  )
  private Collection<AuthorityEntity> authorities;

  public RoleEntity() {}

  public RoleEntity(String name) {
    this.name = name;
  }
}
