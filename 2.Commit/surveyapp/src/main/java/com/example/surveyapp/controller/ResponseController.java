package com.example.surveyapp.controller;

import com.example.surveyapp.model.*;
import com.example.surveyapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.*;

@Controller
public class ResponseController {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseController(SurveyRepository surveyRepository,
                              QuestionRepository questionRepository,
                              ChoiceRepository choiceRepository,
                              ResponseRepository responseRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.responseRepository = responseRepository;
    }

    // Formu gösteren sayfa: /survey/{id}/answer
    @GetMapping("/survey/{id}/answer")
    public String showAnswerForm(@PathVariable Long id, Model model) {
        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null) return "error";

        model.addAttribute("survey", survey);
        return "survey_answer";
    }

    // Form submit edildiğinde gelen cevapları kaydeden endpoint
    @PostMapping("/survey/{id}/answer")
    public String submitAnswers(@PathVariable Long id, @RequestParam Map<String, String> allParams) {
        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null) return "error";

        for (Question question : survey.getQuestions()) {
            String choiceIdStr = allParams.get("question_" + question.getId());
            if (choiceIdStr != null) {
                try {
                    Long choiceId = Long.parseLong(choiceIdStr);
                    Choice selectedChoice = choiceRepository.findById(choiceId).orElse(null);
                    if (selectedChoice != null) {
                        Response response = new Response(survey, question, selectedChoice, null);
                        responseRepository.save(response);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        return "thank_you"; // Form sonrası teşekkür sayfası
    }
}
