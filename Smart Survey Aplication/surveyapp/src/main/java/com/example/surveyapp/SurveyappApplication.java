package com.example.surveyapp;

import com.example.surveyapp.model.Role;
import com.example.surveyapp.model.User;
import com.example.surveyapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SurveyappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyappApplication.class, args);
    }

    // Sadece ilk çalıştırmada admin kullanıcıyı ekler ve bilgi verir
    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepo) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", "1234", Role.ADMIN);
                userRepo.save(admin);

                // Konsola bilgi yaz
                System.out.println("✅ Admin kullanıcısı oluşturuldu: admin / 1234");

            }
            System.out.println("🌐 Localhost: http://localhost:8080");
            System.out.println("🔐 Admin Paneli: http://localhost:8080/admin/panel");
        };
    }
}
