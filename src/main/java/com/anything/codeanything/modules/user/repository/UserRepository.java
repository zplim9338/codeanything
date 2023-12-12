package com.anything.codeanything.modules.user.repository;

import com.anything.codeanything.modules.user.model.TUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface  UserRepository extends JpaRepository<TUserAccount, Long> {
}
