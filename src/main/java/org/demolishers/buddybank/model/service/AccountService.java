package org.demolishers.buddybank.service;

import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.User;
import org.demolishers.buddybank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    // Method to find an account by its ID
    public Account findById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.orElse(null); // Return the account if found, otherwise return null
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



