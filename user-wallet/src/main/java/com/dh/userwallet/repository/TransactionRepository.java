package com.dh.userwallet.repository;

import com.dh.userwallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value="select * from transactions where id_account = ?1 limit 5", nativeQuery = true)
    List<Transaction> findTransactionByIdLimitFive(Long id_account);

    @Query(value="select * from transactions t where t.id_account = ?1 order by t.date DESC limit 10", nativeQuery = true)
    List<Transaction> findTransactionByDateLimitTen(Long id_account);

    @Query(value = "SELECT * FROM transactions t WHERE t.id_account = ?1 ORDER BY t.date DESC", nativeQuery = true)
    List<Transaction> getAllSortedByDate(Long id);

    @Query(value = "Select * from transactions t where t.type = ?2 and t.id_account = ?1", nativeQuery = true)
    List<Transaction> searchByType(Long id, String type);

    @Query(value = "SELECT * FROM transactions t WHERE t.amount >= ?2 AND t.amount <= ?3 AND t.id_account = ?1 ORDER BY t.amount ASC", nativeQuery = true)
    List<Transaction> searchByAmount(Long id, Long minVal, Long maxVal);
}
