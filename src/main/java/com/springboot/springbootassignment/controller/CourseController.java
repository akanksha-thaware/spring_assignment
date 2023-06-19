package com.springboot.springbootassignment.controller;
import com.springboot.springbootassignment.entity.Course;
import com.springboot.springbootassignment.service.CourseService;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import java.util.List;

@RestController
@Data
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

}


