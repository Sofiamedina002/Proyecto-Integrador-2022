package com.dh.userwallet.service;

import com.dh.userwallet.model.Transaction;

import java.util.List;

public interface ITransactionService {
    Transaction getTransactionById(Long accountId, Long transactionId);
    List<Transaction> getAllSortedByDate(Long id);
    List<Transaction> getTransactionsByDateLimitTen(Long idAccount);
    List<Transaction> getTransactionByAccountID(Long id);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByType(Long id, String type);
    List<Transaction> getTransactionsByAmount(Long id, Long minVal, Long maxVal);
    void generateTransaction(Transaction transaction, Long id);
    List<Transaction> findTransactionByIdLimitFive(Long id_account);
}
