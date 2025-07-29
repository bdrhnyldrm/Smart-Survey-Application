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
    private Choice choice;

    private String textResponse;

    // Kullanıcı ilişkisi - foreign key için JoinColumn ekledik!
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // burada eklemeyi yaptık ✅
    private User user;

    // Boş constructor (JPA için zorunlu)
    public Response() {}

    // Tüm alanlarla constructor
    public Response(Survey survey, Question question, Choice choice, String textResponse, User user) {
        this.survey = survey;
        this.question = question;
        this.choice = choice;
        this.textResponse = textResponse;
        this.user = user;
    }

    // Getter & Setter'lar

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
