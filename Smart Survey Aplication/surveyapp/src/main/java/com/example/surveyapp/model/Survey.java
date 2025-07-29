package com.example.surveyapp.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean active = true;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    // Constructor — MUTLAKA listeyi başlat
    public Survey() {
        this.questions = new ArrayList<>();
    }

    public Survey(String title) {
        this.title = title;
        this.active = true;
        this.questions = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
