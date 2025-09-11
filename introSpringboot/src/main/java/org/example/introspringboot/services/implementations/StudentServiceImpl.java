package org.example.introspringboot.services.implementations;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    //Give access to the repository of students
    @Autowired
    private StudentRepository studentRepository;

    //Override the method so it actually knows what to do
    @Override
    public long getCount() {
        return studentRepository.count();
    }

    //Extract all the students within the repository
    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    //Implementation of the method declared in the interface to find a student by code
    @Override
    public Optional<Student> findStudentByCode(String code) {

        Optional<Student> student = studentRepository.findByCode(code);

        if(student.isPresent()){
            return student;
        } else throw new RuntimeException("Student with code " + code + "not found");
    }

    @Override
    public List<Student> getStudentsByCourseName(String name) {
        return studentRepository.findByStudentCourses_Course_Name(name);
    }
}
