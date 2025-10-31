package org.example.taller2springboot.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


//Id for the intermediate table between two users
@Embeddable
public class TrainerTraineeId implements Serializable {
    @Column(name = "trainer_id")
    private Integer trainerId;

    @Column(name = "trainee_id")
    private Integer traineeId;

    public TrainerTraineeId(Integer trainerId, Integer traineeId) {
        this.trainerId = trainerId;
        this.traineeId = traineeId;
    }

    public TrainerTraineeId() {
        //default constructor
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainerId, traineeId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TrainerTraineeId that){
            return Objects.equals(this.trainerId, that.trainerId) && Objects.equals(this.traineeId, that.traineeId);
        }

        return false;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }
}
