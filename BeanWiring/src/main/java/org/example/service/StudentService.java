package org.example.service;

import org.example.model.Student;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void addStudent(Student student){
        //Check for duplicity using the equals property
        if(studentRepository.getStudents().contains(student)){
            System.out.println("The student is duplicated. It will not be registered.");
        } else {
            studentRepository.addStudent(student);
        }
    }

    public ArrayList<Student> getStudents(){
        return studentRepository.getStudents();
    }

}
