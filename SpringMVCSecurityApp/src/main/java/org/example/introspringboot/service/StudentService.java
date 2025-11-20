package org.example.introspringboot.service;

import org.example.introspringboot.api.v1.dto.StudentCompleteResponse;
import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.api.v1.dto.StudentOnlyCoursesResponse;
import org.example.introspringboot.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    long getCount();

    List<Student> getStudents();

    Optional<Student> findStudentByCode(String code);

    List<Student> getStudentsByCourseName(String name);

    Optional<Student> findById(Integer id);

    Student save(Student student);

    void deleteById(Integer id);

    StudentOnlyCoursesResponse getCoursesStudentId(Integer id);

    Page<StudentDTO> getStudentsByProgram(String program, Pageable pageable);

    void createStudent(StudentDTO studentDTO);

    void updateStudent(Integer id, StudentDTO request);

    List<StudentDTO> getAllStudents();

    StudentCompleteResponse findDetailsByCode(String code);

    StudentOnlyCoursesResponse getCoursesStudentCode(String code);
}
