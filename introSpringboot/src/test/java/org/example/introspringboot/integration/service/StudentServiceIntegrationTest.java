package org.example.introspringboot.integration.service;

import org.example.introspringboot.entity.*;
import org.example.introspringboot.repository.CourseRepository;
import org.example.introspringboot.repository.ProfessorRepository;
import org.example.introspringboot.repository.StudentCourseRepository;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.services.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceIntegrationTest {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    //Variables for tests
    private Student student1;
    private Student student2;
    private Professor professor;
    private Course course;
    private StudentCourse studentCourse1;
    private StudentCourse studentCourse2;


    //Scenario to load in a test
    public void scenario1(){
        //Create a Professor
        professor = new Professor();
        professor.setName("Professor1");
        professorRepository.save(professor);

        //Create a Course
        course = new Course();
        course.setName("Course1");
        course.setCredits(3);
        course.setProfessor(professor);
        courseRepository.save(course);

        //Create a Student
        student1 = new Student();
        student1.setName("Student1");
        student1.setProgram("Program1");
        student1.setCode("1");
        studentRepository.save(student1);

        //Create another Student
        student2 = new Student();
        student2.setName("Student2");
        student2.setProgram("Program2");
        student2.setCode("2");
        studentRepository.save(student1);

        //Add the students to a course
        //Make the embedded id
        StudentCourseId studentCourseId1 = new StudentCourseId();
        studentCourseId1.setStudentId(student1.getId());
        studentCourseId1.setCourseId(course.getId());
        //add the student and course to the key
        studentCourse1 = new StudentCourse();
        //Add the id to the studentcourse
        studentCourse1.setStudentCourseId(studentCourseId1);
        //Add the student and the course
        studentCourse1.setStudent(student1);
        studentCourse1.setCourse(course);
        studentCourseRepository.save(studentCourse1);

        //Add the other student to the course
        StudentCourseId studentCourseId2 = new StudentCourseId();
        studentCourseId2.setStudentId(student2.getId());
        studentCourseId2.setCourseId(course.getId());
        studentCourse2 = new StudentCourse();
        studentCourse2.setStudentCourseId(studentCourseId2);
        studentCourse2.setStudent(student2);
        studentCourse2.setCourse(course);
        studentCourseRepository.save(studentCourse2);
    }

    @BeforeEach
    public void setup(){

    }

    @AfterEach
    public void cleanup(){
        //Cleans the entire DB
        studentRepository.deleteAll();
    }

    @Test
    public void findStudentByCode_WhenStudentExist_ShouldReturnOptionalStudent(){
        //Arrange
        Student student1 = new Student();
        student1.setName("John");
        student1.setCode("123");
        student1.setProgram("SIS");

        //This adds the student in the table and gives it an ID
        studentRepository.save(student1);

        //Act
        Optional<Student> returnedStudent = studentService.findStudentByCode("123");

        //Assert
        assertTrue(returnedStudent.isPresent());
        //The same is assertFalse(retunedStudent.isEmpty())

        //get the info straight from the repository
        Optional<Student> studentInDB = studentRepository.findById(returnedStudent.get().getId());
        assertTrue(studentInDB.isPresent());
    }

    @Test
    public void findStudentByCode_WhenStudentDoesNotExist_ShouldThrowRuntimeException(){
        //Arrange
        //The student doesn't exist so nothing is done

        //Act and Assert
        assertThrows(RuntimeException.class, () -> {
           studentService.findStudentByCode("123");
        });
    }

    @Test
    public void getStudentsByCourseName_WhenCalled_ShouldReturnStudentList(){
        //Arrange
        //This tests nees a class, a professor and students
        scenario1();

        //Act
        List<Student> studentList = studentService.getStudentsByCourseName(course.getName());

        //Assert
        assertEquals(2, studentList.size());
    }
}
