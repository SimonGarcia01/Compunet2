package org.example.service;

import org.example.model.Student;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;

public class StudentService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public void addStudent(Student student){
        //Check for duplicity using the equals property
        if(studentRepository.getStudents().contains(student)){
            System.out.println("The student is duplicated. It will not be registered.");
        } else {
            studentRepository.addStudent(student);
        }

    }

}
