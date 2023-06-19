package com.springboot.springbootassignment.service;
import com.springboot.springbootassignment.dao.CourseRepository;
import com.springboot.springbootassignment.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(int id) {
        Optional<Course> result = courseRepository.findById(id);
        Course s = null;
        if (result.isPresent()) {
            s = result.get();
        }
        else {
            // we didn't find the Course
            throw new RuntimeException("Did not find Course id - " + id);
        }
        return s;
    }
}
