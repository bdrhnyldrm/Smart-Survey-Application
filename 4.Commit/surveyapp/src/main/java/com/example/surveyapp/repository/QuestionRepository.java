package com.example.surveyapp.repository;

import com.example.surveyapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Soru (Question) entity'si için repository arayüzü.
 * JpaRepository ile tüm temel veritabanı işlemleri (findAll, findById, save, delete) hazır gelir.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Ek sorgular tanımlanabilir, örneğin:
    // List<Question> findBySurveyId(Long surveyId);
}
