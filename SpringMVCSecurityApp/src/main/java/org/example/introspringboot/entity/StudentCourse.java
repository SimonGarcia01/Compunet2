package org.example.introspringboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name="students_courses")
public class StudentCourse {
    @EmbeddedId
    private StudentCourseId studentCourseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    public StudentCourse() {
        //Default constructor
    }

    public StudentCourse(StudentCourseId studentCourseId, Course course, Student student) {
        this.studentCourseId = studentCourseId;
        this.course = course;
        this.student = student;
    }

    public StudentCourseId getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(StudentCourseId studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
