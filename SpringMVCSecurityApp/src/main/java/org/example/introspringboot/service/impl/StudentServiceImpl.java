package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.v1.dto.CourseResponse;
import org.example.introspringboot.api.v1.dto.StudentDTO;
import org.example.introspringboot.api.v1.dto.StudentOnlyCoursesResponse;
import org.example.introspringboot.api.v1.mappers.CourseMapper;
import org.example.introspringboot.api.v1.mappers.StudentMapper;
import org.example.introspringboot.entity.Student;
import org.example.introspringboot.repository.StudentRepository;
import org.example.introspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

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

    @Override
    public StudentOnlyCoursesResponse getCoursesStudentId(Integer id) {
        Student student = studentRepository.findById(id).orElse(null);

        List<CourseResponse> courses = student.getStudentCourses().stream().map(
                studentCourse -> courseMapper.toBasicCourse(studentCourse.getCourse())
        ).toList();

        StudentOnlyCoursesResponse response = new StudentOnlyCoursesResponse();

        response.setCourses(courses);

        return response;
    }

    @Override
    public Page<StudentDTO> getStudentsByProgram(String program, Pageable pageable) {
        return studentRepository.findByProgramOrderByIdAsc(program, pageable).map(
                studentMapper::toDto
        );
    }

    @Override
    public void createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        studentRepository.save(student);
    }

    @Override
    public void updateStudent(Integer id, StudentDTO request) {

        Student student = studentRepository.findById(id).orElse(null);

        // Update only non-null fields
        if (request.getName() != null)
            student.setName(request.getName());

        if (request.getCode() != null)
            student.setCode(request.getCode());

        if (request.getProgram() != null)
            student.setProgram(request.getProgram());

        studentRepository.save(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(
                studentMapper::toDto
        ).toList();
    }

}

