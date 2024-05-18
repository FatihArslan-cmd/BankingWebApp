package org.demolishers.buddybank.repository;

import org.demolishers.buddybank.model.LoanRequest;
import org.demolishers.buddybank.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    List<LoanRequest> findByStatus(LoanStatus status);
}
