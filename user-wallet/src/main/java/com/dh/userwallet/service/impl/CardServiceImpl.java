package com.dh.userwallet.service.impl;

import com.dh.userwallet.exception.custom.BadRequestException;
import com.dh.userwallet.exception.custom.ConflictException;
import com.dh.userwallet.exception.custom.NotFoundException;
import com.dh.userwallet.model.Account;
import com.dh.userwallet.model.Card;
import com.dh.userwallet.repository.AccountRepository;
import com.dh.userwallet.repository.CardRepository;
import com.dh.userwallet.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardServiceImpl implements ICardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;



    @Override
    public List<Card> getAllCards(Long id) {

        List<Card> cards = cardRepository.findAll();
        List<Account> accounts = accountRepository.findAll();
        List<Card> results = new ArrayList<>();
        Account accountSelected = null;

        for (Account account: accounts
             ) {
            if(account.getId().equals(id)){
                accountSelected = account;
            }
        }

        if(Objects.isNull(accountSelected)){

            throw new NotFoundException("Account id not found");

        }else {

            for (Card card : cards) {

                if (card.getAccountId().equals(id)) {
                    results.add(card);
                }
            }

            return results;
        }
    }

    @Override
    public Card getByID(Long accountId, Long id) {
        List<Card> cards = cardRepository.findAll();
        Card c = null;

        for (Card card : cards) {
            if(card.getAccountId().equals(accountId) && card.getId().equals(id)){
                c = card;
            }
            /*else{
                throw new BadRequestException("card not found");
            }*/
        }
        if (Objects.isNull(c)){
            throw new NotFoundException("Card not found");
        } else {
        return c;
    }
    }

    @Override
    public void createCard(Card card, Long accountId) {
        List<Account> accounts = accountRepository.findAll();
        String cardNumber = cardRepository.findCardNumber(card.getCardNumber());
        if(Objects.isNull(cardNumber)) {
            for (Account acc : accounts) {
                if(accountId.equals(acc.getId())){
                    card.setAccountId(accountId);
                    cardRepository.save(card);
                }
            }
        }else{
            throw new ConflictException("card number already set to another account");
        }
    }

    @Override
    public void deleteCard(Long accountId, Long id) {
        cardRepository.deleteById(id);
    }
}
