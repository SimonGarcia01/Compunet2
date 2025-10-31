package org.example.taller2springboot.entity;

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
    @JoinColumn(name = "progress_id", nullable = false)
    private GeneralProgress generalProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "trainer_id", referencedColumnName = "trainer_id", nullable = false),
            @JoinColumn(name = "trainee_id", referencedColumnName = "trainee_id", nullable = false)
    })
    private TrainerTrainee trainerTrainee;

    // Attributes
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private LocalDate commentDate;

    public Recommendation() {
        //Default constructor
    }

    public Recommendation(Integer recommendationId, String content, LocalDate commentDate) {
        this.recommendationId = recommendationId;
        this.content = content;
        this.commentDate = commentDate;
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

    public TrainerTrainee getTrainerTrainee() {
        return trainerTrainee;
    }

    public void setTrainerTrainee(TrainerTrainee trainerTrainee) {
        this.trainerTrainee = trainerTrainee;
    }

}
