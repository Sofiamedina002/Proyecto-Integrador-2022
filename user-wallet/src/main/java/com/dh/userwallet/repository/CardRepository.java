package com.dh.userwallet.repository;


import com.dh.userwallet.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
    @Query(value = "select card_number from cards where card_number = ?1", nativeQuery = true)
    String findCardNumber(String cardNumber);
}
