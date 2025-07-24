package com.example.surveyapp.repository;

import com.example.surveyapp.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Kullanıcıların verdiği cevaplar için repository.
 * CRUD işlemleri ve istatistiksel veriler bu repository üzerinden çekilebilir.
 */
@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    // List<Response> findBySurveyId(Long surveyId);
    // Long countByChoiceId(Long choiceId); // sonuç yüzdesi hesaplamak için
}
