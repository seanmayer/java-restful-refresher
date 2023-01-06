package com.appsdeveloperblog.app.ws.io.repositories;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
  extends PagingAndSortingRepository<UserEntity, Long> {
  UserEntity findByEmail(String email);
  UserEntity findByUserId(String userId);
  UserEntity findUserByEmailVerificationToken(String token);

  @Query(
    value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = true",
    countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = true",
    nativeQuery = true
  )
  Page<UserEntity> findAllUsersWithConfirmedEmailAddress(
    Pageable pageableRequest
  );

  @Query(
    value = "select * from Users u where u.FIRST_NAME = ?1",
    nativeQuery = true
  )
  List<UserEntity> findUserByFirstName(String firstName);

  @Query(
    value = "select * from Users u where u.LAST_NAME = :lastName",
    nativeQuery = true
  )
  List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

  @Query(
    value = "select * from Users u where u.FIRST_NAME LIKE %:keyword% or u.LAST_NAME LIKE %:keyword%",
    nativeQuery = true
  )
  List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);
}
