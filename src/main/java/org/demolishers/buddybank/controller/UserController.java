package org.demolishers.buddybank.controller;

import org.demolishers.buddybank.model.Account;
import org.demolishers.buddybank.model.LoanRequest;
import org.demolishers.buddybank.model.User;
import org.demolishers.buddybank.repository.UserRepository;
import org.demolishers.buddybank.service.AccountService;
import org.demolishers.buddybank.service.LoanRequestService;
import org.demolishers.buddybank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final AccountService accountService;
    private final LoanRequestService loanRequestService;
    private final UserService userService;
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




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

        List<LoanRequest> loanRequests = loanRequestService.findByUser(user); // New method to fetch user's loan requests
        model.addAttribute("loanRequests", loanRequests);

        return "user/dashboard";
    }
    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
        return "redirect:/user/dashboard"; // Redirect to user dashboard after deleting account
    }
    @GetMapping("/edit/{id}")
    public String editAccountForm(@PathVariable("id") Long id, Model model) {
        Account account = accountService.findById(id);
        model.addAttribute("account", account);
        return "user/editAccount";
    }

    @GetMapping("/accounts/new")
    public String newAccountForm(Model model) {
        model.addAttribute("account", new Account());
        return "user/createAccount";
    }

    @GetMapping("/loan-requests/new")
    public String newLoanRequestForm(Model model) {
        model.addAttribute("loanRequest", new LoanRequest());
        return "user/createLoanRequest";
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
