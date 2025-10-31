package org.example.taller3mvc.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "recommendations")
public class Recommendation {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recommendationId;

    // Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_progress_id", nullable = false)
    private GeneralProgress generalProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    // Attributes
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private LocalDate commentDate;

    public Recommendation() {
        //Default constructor
    }

    public Recommendation(Integer recommendationId, String content, LocalDate commentDate, GeneralProgress generalProgress, User trainer) {
        this.recommendationId = recommendationId;
        this.content = content;
        this.commentDate = commentDate;
        this.generalProgress = generalProgress;
        this.trainer = trainer;
    }

    // Getters and Setters
    public Integer getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Integer recommendationId) {
        this.recommendationId = recommendationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }

    public GeneralProgress getGeneralProgress() {
        return generalProgress;
    }

    public void setGeneralProgress(GeneralProgress generalProgress) {
        this.generalProgress = generalProgress;
    }

}
