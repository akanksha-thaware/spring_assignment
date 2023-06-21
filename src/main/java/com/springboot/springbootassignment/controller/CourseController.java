package com.springboot.springbootassignment.controller;
import org.springframework.http.*;
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
    public ResponseEntity<?> getCourseById(@PathVariable int id)
    {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

}


