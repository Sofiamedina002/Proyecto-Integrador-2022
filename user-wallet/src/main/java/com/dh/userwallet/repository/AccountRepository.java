package com.dh.userwallet.repository;

import com.dh.userwallet.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "select alias from accounts where alias = ?1", nativeQuery = true)
    String findAllAlias(String alias);
}
