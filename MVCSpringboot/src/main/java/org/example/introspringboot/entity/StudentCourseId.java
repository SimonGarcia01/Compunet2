package org.example.introspringboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentCourseId implements Serializable {
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "course_id")
    private Integer courseId;

    public StudentCourseId() {
    }

    public StudentCourseId(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof StudentCourseId){
            StudentCourseId that = (StudentCourseId) o;
            return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
        }else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
