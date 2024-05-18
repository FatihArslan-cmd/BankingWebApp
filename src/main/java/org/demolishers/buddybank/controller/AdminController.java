package org.demolishers.buddybank.controller;

import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.LoanRequest;
import org.demolishers.buddybank.service.AccountService;
import org.demolishers.buddybank.service.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final LoanRequestService loanRequestService;
    private final AccountService accountService;

    @Autowired
    public AdminController(LoanRequestService loanRequestService, AccountService accountService) {
        this.loanRequestService = loanRequestService;
        this.accountService = accountService;
    }

    @GetMapping("/loan-requests")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String loanRequests(Model model) {
        List<LoanRequest> loanRequests = loanRequestService.findPendingLoanRequests();
        model.addAttribute("loanRequests", loanRequests);
        return "admin/loan-requests";
    }

    @PostMapping("/loan-requests/{id}/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String approveLoanRequest(@PathVariable Long id, @RequestParam String description) {
        loanRequestService.approveLoanRequest(id, description);
        return "redirect:/admin/loan-requests";
    }

    @PostMapping("/loan-requests/{id}/reject")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String rejectLoanRequest(@PathVariable Long id, @RequestParam String description) {
        loanRequestService.rejectLoanRequest(id, description);
        return "redirect:/admin/loan-requests";
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String accounts(Model model) {
        List<Account> accounts = accountService.findAll();
        model.addAttribute("accounts", accounts);
        return "admin/accounts";
    }

    @PostMapping("/accounts/{id}/credit-limit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateCreditLimit(@PathVariable Long id, @RequestParam BigDecimal creditLimit) {
        accountService.updateCreditLimit(id, creditLimit);
        return "redirect:/admin/accounts";
    }
}

