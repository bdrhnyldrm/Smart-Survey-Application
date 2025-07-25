package com.example.surveyapp.repository;

import com.example.surveyapp.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    long countByChoiceId(Long choiceId);
}
