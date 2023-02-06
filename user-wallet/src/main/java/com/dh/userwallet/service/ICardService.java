package com.dh.userwallet.service;

import com.dh.userwallet.model.Card;

import java.util.List;
import java.util.Optional;

public interface ICardService {

    List<Card> getAllCards(Long id);
    Card getByID(Long accountId, Long id);
    void createCard(Card card, Long accountId);
    void deleteCard(Long accountId, Long id);
}
