package org.example.introspringboot.services.implementations;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
