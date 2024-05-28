package org.demolishers.buddybank.service;

import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.LoanRequest;
import org.demolishers.buddybank.model.LoanStatus;
import org.demolishers.buddybank.model.User;
import org.demolishers.buddybank.repository.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LoanRequestService {
    private final LoanRequestRepository loanRequestRepository;
    private final AccountService accountService;

    @Autowired
    public LoanRequestService(LoanRequestRepository loanRequestRepository, AccountService accountService) {
        this.loanRequestRepository = loanRequestRepository;
        this.accountService = accountService;
    }

    public List<LoanRequest> findByUser(User user) {
        return loanRequestRepository.findByUser(user);
    }

    public LoanRequest createLoanRequest(User user, LoanRequest loanRequest) {
        // Loan request amount
        BigDecimal loanAmount = loanRequest.getAmount();

        // User's first account
        Account account = user.getAccounts().get(0);

        // Account's credit limit
        BigDecimal accountCreditLimit = account.getCreditLimit();

        // Account's current balance
        BigDecimal accountBalance = account.getBalance();

        // Ensure the loan amount does not exceed the credit limit minus the balance
        if (loanAmount.compareTo(accountCreditLimit.subtract(accountBalance)) > 0) {
            throw new RuntimeException("Loan amount exceeds credit limit");
        }

        // Assign the loan request to the user and save
        loanRequest.setUser(user);
        loanRequest.setStatus(LoanStatus.PENDING);
        return loanRequestRepository.save(loanRequest);
    }

    public List<LoanRequest> findPendingLoanRequests() {
        return loanRequestRepository.findByStatus(LoanStatus.PENDING);
    }

    public void approveLoanRequest(Long loanRequestId, String description) {
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));

        loanRequest.setStatus(LoanStatus.APPROVED);
        loanRequest.setDescription(description);
        loanRequestRepository.save(loanRequest);

        User user = loanRequest.getUser();
        Account account = user.getAccounts().get(0);
        account.setBalance(account.getBalance().add(loanRequest.getAmount()));
        accountService.save(account);
    }

    public void rejectLoanRequest(Long loanRequestId, String description) {
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));

        loanRequest.setStatus(LoanStatus.REJECTED);
        loanRequest.setDescription(description);
        loanRequestRepository.save(loanRequest);
    }
}
