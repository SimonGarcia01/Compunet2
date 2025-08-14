package org.example.repository;

import org.example.model.Student;

import java.util.ArrayList;

public class StudentRepository {

    public ArrayList<Student> students = new ArrayList<>();

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }
}
