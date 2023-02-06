package com.dh.userwallet.service.impl;

import com.dh.userwallet.exception.custom.BadRequestException;
import com.dh.userwallet.exception.custom.NotFoundException;
import com.dh.userwallet.model.Account;
import com.dh.userwallet.model.Card;
import com.dh.userwallet.model.Transference;
import com.dh.userwallet.repository.TransferenceRepository;
import com.dh.userwallet.service.IAccountService;
import com.dh.userwallet.service.ICardService;
import com.dh.userwallet.service.ITransferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransferencesServiceImpl implements ITransferenceService {

    @Autowired
    private TransferenceRepository transferenceRepository;

    @Autowired
    private ICardService cardService;

    @Autowired
    private IAccountService accountService;

    @Override
    public Transference generateTransference(Transference transference, Long idAccount) {

        Optional<Account> account = accountService.getAccountById(idAccount);
        transference.setIdAccount(account.get().getId());
        List<Card> cards = cardService.getAllCards(idAccount);
        Card cardSelected = null;

        for (Card card: cards
             ) {
            if(card.getCardNumber().equals(transference.getCardNumber())){
                cardSelected = card;
            }
        }

        if(Objects.isNull(cardSelected)){
            throw new NotFoundException("Card not found");
        }else{
            if(cardSelected.getAmount()<transference.getAmount()){
                throw new BadRequestException("Amount not available");
            }else{
                account.get().setAmount(account.get().getAmount() + transference.getAmount());
                return transferenceRepository.save(transference);
            }
        }
    }


}
