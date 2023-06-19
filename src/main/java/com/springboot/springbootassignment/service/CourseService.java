package com.springboot.springbootassignment.service;
import com.springboot.springbootassignment.entity.Course;
import java.util.List;

public interface CourseService {

    public Course createCourse(Course course);
    public List<Course> getAllCourses();
    public Course getCourseById(int id);
}
