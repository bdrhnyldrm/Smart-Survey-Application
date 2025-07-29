package com.example.surveyapp.controller;

import com.example.surveyapp.model.User;
import com.example.surveyapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // Giriş formunu gösteren endpoint
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html render edilir
    }

    // Giriş formu gönderildiğinde çalışır
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        // Kullanıcıyı veritabanından kullanıcı adına göre bul
        User user = userRepository.findByUsername(username).orElse(null);

        // Şifre eşleşirse giriş başarılı
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user); // Oturuma kullanıcıyı ekle

            if (user.getRole().name().equals("ADMIN")) {
                return "redirect:/admin/panel"; // admin paneline yönlendir
            } else {
                return "redirect:/"; // müşteri anasayfaya gider
            }

        } else {
            // Giriş başarısızsa hata mesajı gönder
            model.addAttribute("error", "Geçersiz kullanıcı adı veya şifre");
            return "login"; // tekrar login.html göster
        }
    }

    // Oturum sonlandırma
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // tüm oturumu temizle
        return "redirect:/login";
    }
}
