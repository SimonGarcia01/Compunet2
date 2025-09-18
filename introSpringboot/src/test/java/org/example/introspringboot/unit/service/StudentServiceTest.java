package org.example.introspringboot.unit.service;

import org.example.introspringboot.entity.Professor;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.ProfessorRepository;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.services.implementations.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProfessorRepository professorRepository;

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

    @Test
    public void findStudentByCode_WhenStudentDoesNotExist_ShouldThrowRuntimeException(){
        //Arrange
        //Simulate the repository again
        //since that method throws a runtimeException when the optional is empty then it can be used like this
        when(studentRepository.findByCode("12345")).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(RuntimeException.class, () -> studentService.findStudentByCode("12345"));
    }

    @Test
    public void getStudentsByCourseName_WhenCalled_ShouldReturnStudents(){

        //Arrange
        //First return an existing professor so it can actually enter the conditional
        when(professorRepository.findByCourses_Name("Aplicadas")).
                thenReturn(Optional.of(new Professor()));
        //Now actually try to get the students from the course
        when(studentRepository.findByStudentCourses_Course_Name("Aplicadas")).
                thenReturn(List.of(new Student(), new Student()));

        //Act
        List<Student> list = studentService.getStudentsByCourseName("Aplicadas");

        //Assert
        assertEquals(2, list.size());
    }
}
