package com.example.surveyapp.repository;

import com.example.surveyapp.model.Response;
import com.example.surveyapp.model.User;
import com.example.surveyapp.model.Survey;
import com.example.surveyapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    long countByChoiceId(Long choiceId);
    List<Response> findByUser(User user);
    boolean existsByUserAndQuestion(User user, Question question);
    boolean existsByUserAndSurvey(User user, Survey survey); // 🔸 YENİ
}
