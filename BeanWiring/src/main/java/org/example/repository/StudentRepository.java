package org.example.repository;

import jakarta.annotation.PostConstruct;
import org.example.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class StudentRepository {

    public ArrayList<Student> students = new ArrayList<>();

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    @PostConstruct
    public void initRepo(){
        students.add(new Student("A001", "Student1", ""));
        students.add(new Student("A002", "Student2", ""));
    }
}
