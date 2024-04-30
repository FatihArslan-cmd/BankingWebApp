package org.demolishers.buddybank.service;

import org.demolishers.buddybank.model.Loan;
import org.demolishers.buddybank.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long id, Loan newLoanData) {
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setExpiryDate(newLoanData.getExpiryDate());
                    loan.setLoanType(newLoanData.getLoanType());
                    loan.setBalance(newLoanData.getBalance());
                    loan.setDescription(newLoanData.getDescription());
                    loan.setIsItaboveLimit(newLoanData.getIsItaboveLimit());
                    loan.setAccount(newLoanData.getAccount());
                    return loanRepository.save(loan);
                })
                .orElse(null);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public Loan updateIsItAboveLimitAndResponse(Long loanId, boolean newValue, String responseValue) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Set the new value for isItaboveLimit
        loan.setIsItaboveLimit((byte) (newValue ? 1 : 0));

        // Set response value
        loan.setResponse(responseValue);

        // Save the updated loan
        return loanRepository.save(loan);
    }

}
