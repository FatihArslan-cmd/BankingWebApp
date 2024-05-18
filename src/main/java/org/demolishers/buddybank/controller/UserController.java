package org.demolishers.buddybank.controller;

import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.LoanRequest;
import org.demolishers.buddybank.model.User;
import org.demolishers.buddybank.service.AccountService;
import org.demolishers.buddybank.service.LoanRequestService;
import org.demolishers.buddybank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final AccountService accountService;
    private final LoanRequestService loanRequestService;
    private final UserService userService;

    @Autowired
    public UserController(AccountService accountService, LoanRequestService loanRequestService, UserService userService) {
        this.accountService = accountService;
        this.loanRequestService = loanRequestService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = getCurrentUser();
        List<Account> accounts = accountService.findByUser(user);
        model.addAttribute("accounts", accounts);
        return "user/dashboard";
    }

    @PostMapping("/accounts")
    public String createAccount(@ModelAttribute Account account) {
        User user = getCurrentUser();
        accountService.createAccount(user, account);
        return "redirect:/user/dashboard";
    }

    @PostMapping("/loan-requests")
    public String createLoanRequest(@ModelAttribute LoanRequest loanRequest) {
        User user = getCurrentUser();
        loanRequestService.createLoanRequest(user, loanRequest);
        return "redirect:/user/dashboard";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }

    // Other user-related controller methods
}
