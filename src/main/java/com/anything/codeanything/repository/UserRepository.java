package com.anything.codeanything.repository;

import com.anything.codeanything.model.TUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface  UserRepository extends JpaRepository<TUserAccount, Long> {
    //Method 1
   @Query(value = "SELECT u FROM TUserAccount u WHERE u.email = :email")
   Optional<TUserAccount> findUserByEmail(@Param("email") String email);

    //Method 2
    Optional<TUserAccount> findByEmailEqualsIgnoreCase(String email);
    Optional<TUserAccount> findByUsernameEquals(String username);

}
