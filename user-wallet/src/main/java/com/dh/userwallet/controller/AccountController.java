package com.dh.userwallet.controller;

import com.dh.userwallet.model.*;
import com.dh.userwallet.service.impl.AccountServiceImpl;
import com.dh.userwallet.service.impl.CardServiceImpl;
import com.dh.userwallet.service.impl.TransactionServiceImpl;
import com.dh.userwallet.service.impl.TransferencesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private CardServiceImpl cardService;

    @Autowired
    private TransferencesServiceImpl transferencesService;

    //Account Controller
    @PostMapping("{id}")
    public ResponseEntity<Account> createAccount(@RequestBody Account account, @PathVariable Long id) {

        accountService.createAccount(account, id);
        return ResponseEntity.ok().body(account);
    }

    @PatchMapping("/{id}")
    public Optional<Account> updateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields){
        return accountService.patchAccount(id,fields);
    }

    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }

    @GetMapping("")
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
    }

/*
    @GetMapping("/{id}/alltransactions")
    public ResponseEntity<List<Transaction>> getTransactionByID(@PathVariable Long id){

        return ResponseEntity.ok().body(transactionService.getTransactionByAccountID(id));
    }*/

    @GetMapping("/{accountId}/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long accountId, @PathVariable Long id){
        return  ResponseEntity.ok().body(transactionService.getTransactionById(accountId,id));
    }
    @GetMapping("/{id}/alltransactions")
    public ResponseEntity<List<Transaction>> getAllSortedByDate(@PathVariable Long id){
        return ResponseEntity.ok().body(transactionService.getAllSortedByDate(id));
    }

    @GetMapping("/{accountId}/transactions/limit/10")
    public ResponseEntity<List<Transaction>> getTransactionsByDateLimitTen(@PathVariable Long accountId){
        return ResponseEntity.ok().body(transactionService.getTransactionsByDateLimitTen(accountId));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionByIDLimitFive(@PathVariable Long id){
        return ResponseEntity.ok().body(transactionService.findTransactionByIdLimitFive(id));
    }

   @PostMapping("/{id}/transactions")
   public void generateTransaction(@PathVariable Long id, @RequestBody Transaction transaction){
        transactionService.generateTransaction(transaction,id);
   }

   @GetMapping("/{id}/transactions/type/{type}")
   public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable Long id, @PathVariable String type){
        return ResponseEntity.ok().body(transactionService.getTransactionsByType(id, type));
   }

    @GetMapping("/{id}/transactions/amount/{minVal}/{maxVal}")
    public ResponseEntity<List<Transaction>> getTransactionsByAmount(@PathVariable Long id, @PathVariable Long minVal, @PathVariable Long maxVal){
        return ResponseEntity.ok().body(transactionService.getTransactionsByAmount(id, minVal, maxVal));
    }

    //Card Controller
    @GetMapping("/{accountId}/cards")
    public ResponseEntity<List<Card>> getAllCards(@PathVariable Long accountId){
        return ResponseEntity.ok().body(cardService.getAllCards(accountId));
    }

    @GetMapping("{accountId}/cards/{id}")
    public ResponseEntity<Card> getCardByID(@PathVariable Long accountId, @PathVariable Long id){
        return ResponseEntity.ok().body(cardService.getByID(accountId,id));
    }

    @PostMapping("{accountId}/cards")
    public void createCard(@RequestBody Card card, @PathVariable Long accountId){
        cardService.createCard(card,accountId);
    }

    @DeleteMapping("/{accountId}/cards/{id}")
    public void deleteCard(@PathVariable Long accountId, @PathVariable Long id){
        cardService.deleteCard(accountId,id);
    }


    //Transference

    @PostMapping("/{accountId}/transferences")
    public ResponseEntity<Transference> generateTransference(@RequestBody Transference transference, @PathVariable Long accountId){
       return ResponseEntity.ok().body(transferencesService.generateTransference(transference, accountId));
    }


}

