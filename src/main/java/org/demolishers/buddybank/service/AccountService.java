package org.demolishers.buddybank.service;

import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.User;
import org.demolishers.buddybank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findByUser(User user) {
        return accountRepository.findByUser(user);
    }

    public Account createAccount(User user, Account account) {
        account.setUser(user);
        return accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account updateCreditLimit(Long accountId, BigDecimal newCreditLimit) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setCreditLimit(newCreditLimit);
        return accountRepository.save(account);
    }

    // Other account-related methods
}



