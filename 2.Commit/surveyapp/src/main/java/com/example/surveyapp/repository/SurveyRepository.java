package com.example.surveyapp.repository;

import com.example.surveyapp.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Survey (anket) nesnesi için veritabanı işlemlerini gerçekleştiren repository arayüzüdür.
 * JpaRepository sayesinde temel CRUD işlemleri otomatik gelir.
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // Örnek: aktif anketleri getirmek için özelleştirilmiş bir metod ekleyebilirsin
    // List<Survey> findByActiveTrue();
}
