package com.example.surveyapp.controller;

import com.example.surveyapp.model.*;
import com.example.surveyapp.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class SurveyAnswerController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private ResponseRepository responseRepository;

    /**
     * Anket cevaplama formunu gösterir.
     */
    @GetMapping("/survey/{id}/answer")
    public String showAnswerForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null) {
            model.addAttribute("error", "Anket bulunamadı.");
            return "error";
        }

        if (!survey.isActive()) {
            model.addAttribute("error", "Bu anket şu anda yayında değil.");
            return "error";
        }

        // Daha önce katılım kontrolü
        if (responseRepository.existsByUserAndSurvey(user, survey)) {
            model.addAttribute("error", "Bu ankete zaten katıldınız.");
            return "error";
        }

        model.addAttribute("survey", survey);
        return "survey_answer";
    }

    /**
     * Anket cevaplarını kaydeder.
     */
    @PostMapping("/survey/{id}/answer")
    public String submitAnswers(@PathVariable Long id,
                                @RequestParam Map<String, String> allParams,
                                HttpSession session) {

        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null || !survey.isActive()) return "error";

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        int i = 0;
        while (allParams.containsKey("questionId[" + i + "]")) {
            try {
                Long questionId = Long.parseLong(allParams.get("questionId[" + i + "]"));
                Long choiceId = Long.parseLong(allParams.get("choiceId[" + i + "]"));

                Question question = questionRepository.findById(questionId).orElse(null);
                Choice choice = choiceRepository.findById(choiceId).orElse(null);

                if (question != null && choice != null) {
                    Response response = new Response();
                    response.setSurvey(survey);
                    response.setQuestion(question);
                    response.setChoice(choice);
                    response.setUser(user);
                    response.setTextResponse(null); // opsiyonel alan

                    responseRepository.save(response);
                }

            } catch (NumberFormatException ignored) {}

            i++;
        }

        return "thank_you";
    }
}
