package com.example.surveyapp.controller;

import com.example.surveyapp.model.User;
import com.example.surveyapp.model.Role;
import com.example.surveyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user, Model model) {
        // Optional kullanıldığı için isPresent() ile kontrol edilir
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Kullanıcı adı zaten alınmış!");
            return "register";
        }

        // Yeni kullanıcıya otomatik olarak CUSTOMER rolü atanır
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);

        return "redirect:/login";
    }
}
