package com.example.surveyapp.model;

// JPA anotasyonlarını kullanmak için gerekli paket
import jakarta.persistence.*;

// Bu sınıf bir veritabanı tablosunu temsil eder
@Entity
public class Choice {

    // Bu alan Choice tablosunun birincil anahtarıdır.
    // Her yeni kayıt için veritabanı otomatik olarak ID atar (AUTO_INCREMENT gibi çalışır).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bu alan çoktan seçmeli sorunun bir seçeneğinin metinsel içeriğidir.
    // Örneğin: "Evet", "Hayır", "Bilmiyorum", "Günde 6-8 saat uyurum" gibi.
    private String text;

    // Bu alan bu seçeneğin hangi soruya ait olduğunu belirtir.
    // Yani her seçenek bir soruya bağlıdır (ManyToOne ilişkisi).
    // Bir soru birden fazla seçenek içerebilir, ancak bir seçenek sadece bir soruya aittir.
    @ManyToOne
    @JoinColumn(name = "question_id") // Veritabanında bu ilişkiyi sağlayan sütun adı
    private Question question;

    // JPA tarafından zorunlu olan parametresiz (boş) constructor.
    // ORM araçları bu constructor'ı kullanarak nesneleri oluşturur.
    public Choice() {}

    // Bu constructor, Choice nesnesi oluştururken text ve question bilgilerini doğrudan set etmeye yarar.
    public Choice(String text, Question question) {
        this.text = text;
        this.question = question;
    }

    // Aşağıdaki metotlar "getter" ve "setter"lardır.
    // Java Beans standardına göre yazılırlar ve nesne verilerine erişmek veya değiştirmek için kullanılırlar.

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
