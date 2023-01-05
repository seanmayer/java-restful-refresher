package com.appsdeveloperblog.app.ws.io.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity implements Serializable {

  private static final long serialVersionUID = -89898989898989898L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 120)
  private String email;

  @Column(nullable = false)
  private String encryptPassword;

  private String emailVerificationToken;

  @Column(nullable = false)
  private Boolean emailVerificationStatus = false;

  @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
  private List<AddressEntity> addresses;

 }
