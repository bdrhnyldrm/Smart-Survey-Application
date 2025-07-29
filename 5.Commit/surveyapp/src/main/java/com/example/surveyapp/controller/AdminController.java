package com.example.surveyapp.controller;

import com.example.surveyapp.model.*;
import com.example.surveyapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    /**
     * Admin panelini gösterir.
     */
    @GetMapping("/admin/panel")
    public String adminPanel(Model model) {
        List<Survey> surveys = surveyRepository.findAll();
        model.addAttribute("surveys", surveys);
        return "admin_panel";
    }

    /**
     * Yeni anket oluşturma formunu gösterir.
     */
    @GetMapping("/admin/create")
    public String showCreateForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "admin_create";
    }

    /**
     * Yeni anketi kaydeder.
     */
    @PostMapping("/admin/create")
    public String createSurvey(@ModelAttribute Survey survey,
                               @RequestParam("questionTexts") List<String> questionTexts,
                               @RequestParam("choiceTexts") List<String> choiceTexts) {

        survey.setActive(true);
        surveyRepository.save(survey);

        int choiceIndex = 0;

        for (String questionText : questionTexts) {
            Question question = new Question();
            question.setSurvey(survey);
            question.setText(questionText);
            question.setMultipleChoice(false); // örnek
            questionRepository.save(question);

            List<Choice> choices = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                if (choiceIndex < choiceTexts.size()) {
                    Choice choice = new Choice();
                    choice.setQuestion(question);
                    choice.setText(choiceTexts.get(choiceIndex++));
                    choices.add(choice);
                }
            }
            choiceRepository.saveAll(choices);
        }

        return "redirect:/admin/panel";
    }

    /**
     * Anketi siler.
     */
    @GetMapping("/admin/delete/{id}")
    public String deleteSurvey(@PathVariable Long id) {
        surveyRepository.deleteById(id);
        return "redirect:/admin/panel";
    }

    /**
     * Yayın durumu (aktif/pasif) değiştirir.
     */
    @PostMapping("/admin/toggle/{id}")
    public String toggleSurveyStatus(@PathVariable Long id) {
        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey != null) {
            survey.setActive(!survey.isActive());
            surveyRepository.save(survey);
        }
        return "redirect:/admin/panel";
    }
}
