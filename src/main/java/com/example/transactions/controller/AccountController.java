package com.example.transactions.controller;

import com.example.transactions.model.Account;
import com.example.transactions.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){

        Account createdAccount = accountService.saveAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.OK);
    }
    @PostMapping("/transfer")
    public ResponseEntity<Account> balanceTransfer(
            @RequestParam("fromAccount") String fromAccount,
            @RequestParam("toAccount") String toAccount,
            @RequestParam("amount") BigDecimal amount) {

        Account account1 = accountService.getAccountById(fromAccount);
        Account account2 = accountService.getAccountById(toAccount);
        Account remainingBalance = accountService.balanceTransfer(account1, account2, amount);

        return new ResponseEntity<>(remainingBalance, HttpStatus.OK);
    }
    @GetMapping("/find")
    public ResponseEntity<Account> findAccount(@RequestParam("accountId") String accountId) {
        Account response = accountService.getAccountById(accountId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
