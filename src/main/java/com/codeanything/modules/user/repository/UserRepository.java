package com.codeanything.modules.user.repository;

import com.codeanything.modules.user.model.TUserAccount;

import java.util.List;

//public interface  UserRepository extends JpaRepository<TUserAccount, Long> {
public interface UserRepository {
    TUserAccount save(TUserAccount product);

    List<TUserAccount> findAll();
}
