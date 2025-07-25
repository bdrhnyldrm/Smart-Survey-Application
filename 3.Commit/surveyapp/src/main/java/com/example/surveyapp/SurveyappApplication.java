package com.example.surveyapp;

import com.example.surveyapp.model.*;
import com.example.surveyapp.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SurveyappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyappApplication.class, args);
    }

    // 1️⃣ Örnek anket, soru ve şık ekler
    @Bean
    public CommandLineRunner dataLoader(SurveyRepository surveyRepo,
                                        QuestionRepository questionRepo,
                                        ChoiceRepository choiceRepo) {
        return args -> {
            // Anket oluştur
            Survey survey = new Survey("Kahve Tüketim Alışkanlıkları");
            surveyRepo.save(survey);

            // Soru oluştur
            Question question = new Question("Günde kaç fincan kahve içersiniz?", true, survey);
            questionRepo.save(question);

            // Seçenekleri oluştur
            Choice c1 = new Choice("0-1", question);
            Choice c2 = new Choice("2-3", question);
            Choice c3 = new Choice("4 ve üzeri", question);
            choiceRepo.saveAll(Arrays.asList(c1, c2, c3));

            System.out.println("📋 Örnek anket ve soru eklendi.");
            System.out.println("🌐 Localhost: http://localhost:8080");
            System.out.println("🔐 Admin Paneli: http://localhost:8080/admin/panel");
        };
    }

    // 2️⃣ Giriş yapabilmek için varsayılan admin kullanıcısını ekler
    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepo) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", "1234", Role.ADMIN);
                userRepo.save(admin);
                System.out.println("✅ Admin kullanıcısı oluşturuldu: admin / 1234");
            }
        };
    }
}
