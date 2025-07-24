package com.example.surveyapp.controller;

import com.example.surveyapp.model.Choice;
import com.example.surveyapp.model.Question;
import com.example.surveyapp.model.Survey;
import com.example.surveyapp.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SurveyRepository surveyRepository;

    @Autowired
    public AdminController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/panel")
    public String showAdminPanel(Model model) {
        model.addAttribute("surveys", surveyRepository.findAll());
        return "admin_panel";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "admin_create";
    }

    @PostMapping("/create")
    public String createSurvey(@ModelAttribute Survey survey,
                               @RequestParam("questionTexts") List<String> questionTexts,
                               @RequestParam("choiceTexts") List<String> choiceTexts) {

        // Soru listesi boşsa başlat
        if (survey.getQuestions() == null) {
            survey.setQuestions(new ArrayList<>());
        }

        int index = 0;
        for (String qText : questionTexts) {
            Question question = new Question();
            question.setText(qText);
            question.setMultipleChoice(true);
            question.setSurvey(survey);

            List<Choice> choices = new ArrayList<>();
            // 3 şık ekle (her soru için 3 adet varsayıyoruz)
            for (int j = 0; j < 3; j++) {
                String cText = choiceTexts.get(index++);
                Choice choice = new Choice();
                choice.setText(cText);
                choice.setQuestion(question);
                choices.add(choice);
            }

            question.setChoices(choices);
            survey.getQuestions().add(question);
        }

        surveyRepository.save(survey);
        return "redirect:/admin/panel";
    }

    @GetMapping("/delete/{id}")
    public String deleteSurvey(@PathVariable Long id) {
        surveyRepository.deleteById(id);
        return "redirect:/admin/panel";
    }
}
