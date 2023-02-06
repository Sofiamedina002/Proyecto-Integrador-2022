package com.dh.userwallet.service.impl;

import com.dh.userwallet.exception.custom.BadRequestException;
import com.dh.userwallet.model.Account;
import com.dh.userwallet.model.Transaction;
import com.dh.userwallet.repository.AccountRepository;
import com.dh.userwallet.repository.TransactionRepository;
import com.dh.userwallet.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionByAccountID(Long id) {

        accountRepository.findById(id);
        List<Transaction> transactions = transactionRepository.findAll();
        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : transactions
        ) {
            if (transaction.getIdAccount().equals(id)) {
                results.add(transaction);
            }
        }
        return results;
    }


    @Override
    public List<Transaction> getAllSortedByDate(Long id){

        return transactionRepository.getAllSortedByDate(id);
    }

    @Override
    public Transaction getTransactionById(Long accountId, Long id) {
        List<Transaction> transactions = transactionRepository.findAll();


        for (Transaction transaction : transactions) {
            if( transaction.getIdAccount().equals(accountId) && transaction.getId().equals(id)){
                return transaction;
            }
            /*else{
                throw new BadRequestException("card not found");
            }*/
        }
        return null;
    }
    @Override
    public List<Transaction> getTransactionsByType(Long id, String type){
        List<Transaction> transactions = transactionRepository.searchByType(id, type);
        for (Transaction transaction : transactions) {
            if( transaction.getIdAccount().equals(id)){
                return transactions;
            }
            /*else{
                throw new BadRequestException("card not found");
            }*/
        }
        return null;
    }

    public List<Transaction> getTransactionsByAmount(Long id, Long minVal, Long maxVal){
        return transactionRepository.searchByAmount(id, minVal, maxVal);
    }

    @Override
    public void generateTransaction(Transaction transaction, Long id) {

        Optional<Account> originAccount = accountRepository.findById(id);
        List<Account> accounts = accountRepository.findAll();
        Account destinatedAccount = null;

        for (Account account : accounts
        ) {
            if (account.getCvu().equals(transaction.getDestinatedCvu())) {
                destinatedAccount = account;
            }
        }

        if (destinatedAccount != null) {
            if (originAccount.get().getAmount() >= transaction.getAmount()) {
                destinatedAccount.setAmount(destinatedAccount.getAmount() + transaction.getAmount());
                originAccount.get().setAmount(originAccount.get().getAmount() - transaction.getAmount());
                transaction.setIdAccount(id);
                transactionRepository.save(transaction);
            } else {
                throw new BadRequestException("the amount of money is not available");
            }
        } else {
            throw new BadRequestException("the destinated account does not exist");
        }


    }

    @Override
    public List<Transaction> findTransactionByIdLimitFive(Long id_account) {
        accountRepository.findById(id_account);
        List<Transaction> transactions = transactionRepository.findTransactionByIdLimitFive(id_account);
        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : transactions
        ) {
            if (transaction.getIdAccount().equals(id_account)) {
                results.add(transaction);
            }
        }
        return results;
    }

    @Override
    public List<Transaction> getTransactionsByDateLimitTen(Long idAccount) {
        accountRepository.findById(idAccount);
        List<Transaction> transactions = transactionRepository.findTransactionByDateLimitTen(idAccount);
        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : transactions
        ) {
            if (transaction.getIdAccount().equals(idAccount)) {
                results.add(transaction);
            }
        }
        return results;
    }
}
