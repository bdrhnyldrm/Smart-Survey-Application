package com.example.surveyapp.model;

import jakarta.persistence.*;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Survey survey;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Choice choice; // Çoktan seçmeli yanıt

    private String textResponse; // Açık uçlu yanıt (isteğe bağlı)

    public Response() {}

    public Response(Survey survey, Question question, Choice choice, String textResponse) {
        this.survey = survey;
        this.question = question;
        this.choice = choice;
        this.textResponse = textResponse;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public String getTextResponse() {
        return textResponse;
    }

    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }
}
