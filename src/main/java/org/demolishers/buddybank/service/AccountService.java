package org.demolishers.buddybank.service;

import org.demolishers.buddybank.dto.LoanRequest;
import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.Loan;
import org.demolishers.buddybank.repository.AccountRepository;
import org.demolishers.buddybank.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, LoanRepository loanRepository) {
        this.accountRepository = accountRepository;
        this.loanRepository = loanRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountType(accountDetails.getAccountType());
        account.setBalance(accountDetails.getBalance());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Loan createLoanForAccount(Long accountId, LoanRequest loanRequest) {
        // Find the account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Create a new loan
        Loan loan = Loan.builder()
                .expiryDate(LocalDate.now())
                .loanType(loanRequest.getLoanType())
                .balance(loanRequest.getAmount())
                .description(loanRequest.getDescription())
                .account(account)
                .response("")
                .isItaboveLimit((byte) 2)
                .build();

        // Save the loan
        return loanRepository.save(loan);
    }
}
