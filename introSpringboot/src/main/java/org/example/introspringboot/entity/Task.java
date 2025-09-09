package org.example.introspringboot.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_code", unique = true, nullable = false)
    private String taskCode;

    // Se mapea a un tipo TIMESTAMP en la BD
    @Column(name = "execution_time")
    private LocalDateTime executionTime;

    // Se mapea a un tipo DATE en la BD
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(nullable = true, length = 500)
    private String description;

    public Task(Long id, String taskCode, LocalDateTime executionTime, LocalDate creationDate, String description) {
        this.id = id;
        this.taskCode = taskCode;
        this.executionTime = executionTime;
        this.creationDate = creationDate;
        this.description = description;
    }

    public Task() {
        //default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public LocalDateTime getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getters, setters y constructores
}