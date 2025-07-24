package com.example.surveyapp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity // Bu sınıfın bir JPA entity (veritabanı tablosu) olduğunu belirtir
public class Question {

    @Id // Bu alanın birincil anahtar (primary key) olduğunu belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik artan ID değeri
    private Long id; // Sorunun benzersiz kimliği

    private String text; // Sorunun metni (örneğin: "Hangi renkleri seversiniz?")

    private boolean multipleChoice; // Soru çoktan seçmeli mi? true = evet, false = hayır

    @ManyToOne // Her soru bir ankete (Survey) aittir (çoktan bire ilişki)
    @JoinColumn(name = "survey_id") // Veritabanında survey_id adlı sütun ile ilişkilendirilir
    private Survey survey; // Bu soru hangi ankete ait

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    // Bu soruya ait cevap seçenekleri listesi
    // mappedBy = "question" -> Choice sınıfında question alanı bu ilişkiyi yönetir
    // cascade = CascadeType.ALL -> Soru silinirse ilişkili tüm seçenekler de silinir
    private List<Choice> choices;

    public Question() {}
    // Boş kurucu metot (JPA için gereklidir)

    public Question(String text, boolean multipleChoice, Survey survey) {
        // Parametreli kurucu metot
        this.text = text;
        this.multipleChoice = multipleChoice;
        this.survey = survey;
    }

    // Getter ve Setter metotları

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
