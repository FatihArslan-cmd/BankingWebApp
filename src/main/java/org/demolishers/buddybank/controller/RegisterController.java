package org.demolishers.buddybank.controller;

import org.demolishers.buddybank.model.Role;
import org.demolishers.buddybank.model.User;
import org.demolishers.buddybank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.html şablonunu kullanarak register sayfasını döndürür
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Kullanıcıya şifreleme uygulanması
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Kullanıcı adı kontrolü ve rol ataması
        if ("admin".equalsIgnoreCase(user.getUsername())) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
        return "redirect:/login";
    }
}
