package com.dh.userwallet.service.impl;

import com.dh.userwallet.client.UserFeign;
import com.dh.userwallet.exception.custom.BadRequestException;
import com.dh.userwallet.exception.custom.ConflictException;
import com.dh.userwallet.model.Account;
import com.dh.userwallet.model.User;
import com.dh.userwallet.repository.AccountRepository;
import com.dh.userwallet.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserFeign userFeign;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);

    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account createAccount(Account account, Long id) {

        User accountUser = userFeign.getUserById(id);
        account.setUserId(id);
        account.setCvu(accountUser.getCvu());
        account.setAlias(accountUser.getAlias());
//        account.setUser(accountUser);
        return accountRepository.save(account);

    }

    public User getUserById(Long id){
        User newUser = userFeign.getUserById(id);
        return newUser;
    }

    @Override
    public Optional<Account> patchAccount(Long id, Map<Object, Object> fields){
        Optional<Account> account = accountRepository.findById(id);


        if(account.isPresent() && fields.containsKey("alias") && fields.size()==1){
            fields.forEach((key, value) ->{
                        String alias = accountRepository.findAllAlias(value.toString());
                        if(Objects.isNull(alias)) {
                            Field field = ReflectionUtils.findField(Account.class, (String) key);
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, account.get(), value);

                            accountRepository.save(account.get());
                        }else{
                           throw  new ConflictException("alias is already in use");
                        }
                });

            } else {
            throw new BadRequestException("update not available");
        }

        return account;
    }




}
