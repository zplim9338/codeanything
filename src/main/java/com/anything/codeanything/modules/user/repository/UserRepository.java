package com.anything.codeanything.modules.user.repository;

import com.anything.codeanything.modules.user.model.TUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface  UserRepository extends JpaRepository<TUserAccount, Long> {
    //Method 1
//    @Query(value = "SELECT * FROM T_USER_ACCOUNT WHERE `email` = :email")
//    TUserAccount findUserByEmail(@Param("email") String email);

    //Method 2
    TUserAccount findByEmailEqualsIgnoreCase(String email);
    TUserAccount findByUsernameEquals(String username);

}
