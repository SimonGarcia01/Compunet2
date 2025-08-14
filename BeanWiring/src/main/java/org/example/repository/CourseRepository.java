package org.example.repository;

import org.example.model.Course;

import java.util.ArrayList;

public class CourseRepository {
    //Repository to hold the information of courses
    private ArrayList<Course> courses;

    public void addCourse(Course course){
        courses.add(course)
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
