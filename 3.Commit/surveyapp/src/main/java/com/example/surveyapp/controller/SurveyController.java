package com.example.surveyapp.controller;

import com.example.surveyapp.model.*;
import com.example.surveyapp.repository.ResponseRepository;
import com.example.surveyapp.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @GetMapping("/")
    public String showSurveys(Model model) {
        List<Survey> surveys = surveyRepository.findAll();
        model.addAttribute("surveys", surveys);
        return "survey_list";
    }

    @GetMapping("/survey/{id}")
    public String showSurveyDetail(@PathVariable Long id, Model model) {
        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null) return "error";

        model.addAttribute("survey", survey);
        return "survey_detail";
    }

    @GetMapping("/survey/{id}/results")
    public String showSurveyResults(@PathVariable Long id, Model model) {
        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null) return "error";

        model.addAttribute("survey", survey);

        Map<Long, Long> choiceVoteCounts = new HashMap<>();
        Map<Long, Long> questionTotalVotes = new HashMap<>();

        for (Question q : survey.getQuestions()) {
            long total = 0;
            for (Choice c : q.getChoices()) {
                long count = responseRepository.countByChoiceId(c.getId());
                choiceVoteCounts.put(c.getId(), count);
                total += count;
            }
            questionTotalVotes.put(q.getId(), total);
        }

        model.addAttribute("choiceVoteCounts", choiceVoteCounts);
        model.addAttribute("questionTotalVotes", questionTotalVotes);

        return "survey_results";
    }
}
