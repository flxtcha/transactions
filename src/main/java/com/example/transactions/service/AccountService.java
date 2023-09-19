package com.example.transactions.service;

import com.example.transactions.model.Account;
import com.example.transactions.respository.AccountRepository;
import com.example.transactions.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {


    private AccountRepository accountRepository;

    public Account getAccountById(String id){
        return accountRepository.findById(id).orElse(null);
    }
    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    @Transactional
    public Account balanceTransfer(Account fromAccount, Account toAccount, BigDecimal amount) throws InsufficientBalanceException {
        validatePositiveAmount(amount);
        validateSufficientBalance(fromAccount, amount);

        fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(amount));
        toAccount.setAccountBalance(toAccount.getAccountBalance().add(amount));

        accountRepository.saveAll(List.of(fromAccount, toAccount));

        return fromAccount;
    }
    private void validatePositiveAmount(BigDecimal amount) throws IllegalArgumentException{
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer must be a positive value.");
        }
    }
    private void validateSufficientBalance(Account fromAccount, BigDecimal amount) throws InsufficientBalanceException {
        BigDecimal fromBalance = fromAccount.getAccountBalance();
        if (fromBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }
}
