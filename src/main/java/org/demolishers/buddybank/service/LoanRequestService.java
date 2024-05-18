package org.demolishers.buddybank.service;

import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.LoanRequest;
import org.demolishers.buddybank.model.LoanStatus;
import org.demolishers.buddybank.model.User;
import org.demolishers.buddybank.repository.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public LoanRequest createLoanRequest(User user, LoanRequest loanRequest) {
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

        Account account = loanRequest.getUser().getAccounts().get(0);
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
