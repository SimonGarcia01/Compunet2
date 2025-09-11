package org.example.introspringboot.unit.service;

import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.services.implementations.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void findStudentByCode_WhenStudentExist_ShouldReturnOptionalStudent(){
        //Arrange
        //make the student
        Student mockStudent = new Student();
        //When the method is called then the repository with return that specific value
        when(studentRepository.findByCode("12345")).thenReturn(Optional.of(mockStudent));

        //Act
        //This method calls the repository which is replaced with the when() line
        Optional<Student> student = studentService.findStudentByCode("12345");

        //assert
        assertTrue(student.isPresent());
    }
}
