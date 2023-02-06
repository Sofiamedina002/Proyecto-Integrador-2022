package com.dh.userwallet.service;

import com.dh.userwallet.model.Account;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAccountService {
    Optional<Account> getAccountById(Long id);
    Account createAccount(Account account,Long id);
    Optional<Account> patchAccount(Long id, Map<Object, Object> fields);
    List<Account> getAllAccounts();
    void deleteAccount(Long id);


}
