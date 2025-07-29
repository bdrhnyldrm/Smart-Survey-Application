package com.example.surveyapp.repository;

import com.example.surveyapp.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Choice entity'si için repository arayüzü.
 * Her bir şık veritabanında yönetilir.
 */
@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    // List<Choice> findByQuestionId(Long questionId);
}
