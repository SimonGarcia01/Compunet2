package org.example.introspringboot.service.impl;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public long getCount() {
        return studentRepository.count();
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findStudentByCode(String code) {
            Optional<Student> student = studentRepository.findByCode(code);
            if(student.isPresent()){
                return student;
            }else throw new RuntimeException("Student with code " + code + " not found");
    }

    @Override
    public List<Student> getStudentsByCourseName(String name) {
        return studentRepository.findByStudentCourses_Course_Name(name);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Integer id) {
        studentRepository.deleteById(id);
    }
}

