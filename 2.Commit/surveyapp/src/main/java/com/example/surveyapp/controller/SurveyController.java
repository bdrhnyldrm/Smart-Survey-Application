package com.example.surveyapp.controller;

import com.example.surveyapp.model.Survey;
import com.example.surveyapp.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Bu controller sınıfı, anketlerin kullanıcıya listelenmesi ve
 * her bir ankete ait detayların (sorular/seçenekler) gösterilmesini sağlar.
 */
@Controller
public class SurveyController {

    private final SurveyRepository surveyRepository;

    // SurveyRepository bağımlılığı constructor üzerinden alınır (Dependency Injection)
    @Autowired
    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Ana sayfa: Tüm anketleri listeler
     * URL: http://localhost:8080/
     * @@param model - Thymeleaf'e verileri taşımak için kullanılan model nesnesi
     * @return survey_list.html dosyasını render eder
     */
    @GetMapping("/")
    public String showSurveys(Model model) {
        List<Survey> surveys = surveyRepository.findAll(); // Tüm anketleri veritabanından al
        model.addAttribute("surveys", surveys); // "surveys" adında view'a gönder
        return "survey_list"; // resources/templates/survey_list.html dosyasını döndür
    }

    /**
     * Belirli bir anketin detay sayfası
     * URL: /survey/{id}
     * Örn: /survey/1 → 1 numaralı anketin başlığı, soruları ve şıkları gösterilir
     * .@param id - URL'den gelen anket ID'si
     * .@param model - Thymeleaf'e verileri taşımak için
     * @return survey_detail.html dosyasını döndürür
     */
    @GetMapping("/survey/{id}")
    public String showSurveyDetail(@PathVariable Long id, Model model) {
        Survey survey = surveyRepository.findById(id).orElse(null); // ID ile anketi bul

        if (survey == null) {
            return "error"; // Anket bulunamazsa hata sayfasına yönlendir
        }

        model.addAttribute("survey", survey); // Anketi template'e gönder
        return "survey_detail"; // resources/templates/survey_detail.html dosyasını render et
    }
}

